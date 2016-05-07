// CHECKSTYLE:OFF
package com.evokly.kafka.connect.mqtt.util;

/**
 * Copyright 2016 Evokly S.A.
 *
 * <p>See LICENSE file for License
 **/
public class Utils {
    public static <T> T getConfiguredInstance(String key, Class<T> t) {
        Class<?> c = null;
        try {
            c = Class.forName(key);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (c == null)
            return null;
        Object o = Utils.newInstance(c);
        if (!t.isInstance(o))
            throw new RuntimeException(c.getName() + " is not an instance of " + t.getName());
        return t.cast(o);
    }

    /**
     * Instantiate the class
     */
    public static <T> T newInstance(Class<T> c) {
        try {
            return c.newInstance();
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Could not instantiate class " + c.getName(), e);
        } catch (InstantiationException e) {
            throw new RuntimeException("Could not instantiate class " + c.getName() + " Does it have a public no-argument constructor?", e);
        } catch (NullPointerException e) {
            throw new RuntimeException("Requested class was null", e);
        }
    }
}
