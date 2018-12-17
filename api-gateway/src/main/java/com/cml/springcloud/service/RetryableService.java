package com.cml.springcloud.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Service
public class RetryableService {
	private Logger log = LoggerFactory.getLogger(RetryableService.class);

	@Retryable(include = Exception.class, maxAttempts = 3)
	public String testRetry(int type) {
		if (type < 0) {
			log.warn("do smthing fail!!!! retry !!!!!!!!");
			throw new IllegalArgumentException("invalid type [" + type + "]");
		}
		return "valid type :" + type;
	}

	@Recover
	public String recover() {
		return "default Value!!!";
	}

}
