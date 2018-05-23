package com.cml.springcloud.auth;

import java.security.Key;
import java.text.ParseException;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@ConfigurationProperties(prefix = "system.auth")
@Component
public class AccessTokenAuthManager {
	private String jwtKey;
	private long validTime;
	/**
	 * 加密key，jws使用时会复制一份，不会发生并发问题
	 */
	private Key key;

	@PostConstruct
	public void init() {
		key = generateKey(jwtKey);
	}

	/**
	 * 生成token
	 * 
	 * @param target
	 *            需要保存到token的对象信息，一般为User对象或map对象
	 * @return token
	 * @throws ParseException
	 */
	public String generateToken(Object target) {
		return Jwts.builder().setSubject(new Gson().toJson(target)).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + validTime)).signWith(SignatureAlgorithm.HS512, key).compact();
	}

	public String getJwtKey() {
		return jwtKey;
	}

	public void setJwtKey(String jwtKey) {
		this.jwtKey = jwtKey;
	}

	public long getValidTime() {
		return validTime;
	}

	public void setValidTime(long validTime) {
		this.validTime = validTime;
	}

	/**
	 * 获取token上记录的信息
	 * 
	 * @param token
	 * @return token上记录的信息
	 */
	public String parseToken(String token) {
		Claims claim = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
		return claim.getSubject();
	}

	/**
	 * 随机生成字符串key
	 * 
	 * @return 生成的字符串key
	 */
	public String generateKeyValue() {
		return new String(Base64.getEncoder().encode(UUID.randomUUID().toString().getBytes()));
	}

	/**
	 * 根据字符串key获取Key对象
	 * 
	 * @param key
	 * @return
	 */
	public Key generateKey(String key) {
		Key signingKey = new SecretKeySpec(key.getBytes(), SignatureAlgorithm.HS512.getJcaName());
		return signingKey;
	}
}
