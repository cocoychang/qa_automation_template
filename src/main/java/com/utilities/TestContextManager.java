package com.utilities;

import org.testng.annotations.Parameters;

import java.lang.reflect.Method;

public class TestContextManager {
    private static final ThreadLocal<String> testName = new ThreadLocal<>();
    private static final ThreadLocal<String> xmlTestName = new ThreadLocal<>();


    public static void setTestName(String name) {
        testName.set(name);
    }

    public static String getTestName() {
        return testName.get();
    }

    public static void unload() {
        testName.remove();
    }

    public static String getXmlTestName() {
        return xmlTestName.get();
    }
}
