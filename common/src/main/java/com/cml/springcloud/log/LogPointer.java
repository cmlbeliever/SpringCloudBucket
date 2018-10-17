package com.cml.springcloud.log;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class LogPointer {
    private static ThreadLocal<String> traceId = new ThreadLocal<>();
    private static ThreadLocal<Map<String, Object>> points = new ThreadLocal<>();

    public static void init() {
        traceId.set(UUID.randomUUID().toString());
        points.set(new HashMap<>());
    }

    public static String getTraceId() {
        return traceId.get();
    }

    public static void addPoint(String key, Object value) {
        if (points.get() != null) {
            points.get().put(key, value);
        }
    }

    public static Map<String, Object> getPoints() {
        return points.get() == null ? new HashMap<>() : points.get();
    }

    public static void clear() {
        traceId.remove();
        points.remove();
    }
}
