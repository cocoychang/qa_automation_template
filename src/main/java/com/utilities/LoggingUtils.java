package com.utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class LoggingUtils {

    public static final Logger LOGGER = LogManager.getLogger(LoggingUtils.class);
    public void info(String message) {
        String xmlTestName = TestContextManager.getTestName();
        System.out.println("[LOG] [" + Thread.currentThread().getId() + "] "
                + "[" + xmlTestName + "] "+ message);
        LOGGER.info(message);
    }

    public void info(int message) {
        LOGGER.info(message);
    }

    public void info(ArrayList<String> message) {
        LOGGER.info(message);
    }

    public void info(List<String> message) {
        LOGGER.info(message);
    }

    public void error(String message) {
        LOGGER.error(message);
    }

    public void error(Exception message) {
        LOGGER.error(message);
    }
    /**
     * Log a message with WARN severity.
     *
     * <p>ExtentReporter relies on this method when the reporting step is not
     * properly initialised. Without it the project fails to compile because
     * {@code LoggingUtils} did not previously expose a matching signature.</p>
     *
     * @param message text to log at the warn level
     */
    public void warn(String message) {
        LOGGER.warn(message);
    }

    public void log(byte[] bytes, String message) {
    }

    public void logBase64(String base64, String message) {
        LOGGER.info(base64, message);
    }

    public void log(File file, String message) {
        LOGGER.info("RP_MESSAGE#FILE#{}#{}", file.getAbsolutePath(), message);
    }
}

