package com.n26.util;

/**
 * Logging helper class
 */
public class LoggingUtil {

    /**
     * Method to construct entering log message
     *
     * @param methodName name of the method
     * @param args       method arguments
     * @return log message
     */
    public static String getEnteringMethodMessage(String methodName, Object... args) {
        StringBuilder builder = new StringBuilder(String.format("Entering Method: %s with args: ", methodName));
        addArgs(args, builder);
        return builder.toString();
    }

    /**
     * Method to construct exiting log message
     *
     * @param methodName name of the method
     * @param args       method arguments
     * @return log message
     */
    public static String getExitingMethodMessage(String methodName, Object... args) {
        StringBuilder builder = new StringBuilder(String.format("Exiting Method: %s with args: ", methodName));
        addArgs(args, builder);
        return builder.toString();
    }

    /**
     * Method to add arguments to log message
     *
     * @param args    method arguments
     * @param builder log message StringBuilder
     */
    private static void addArgs(Object[] args, StringBuilder builder) {

        if (args != null) {
            for (Object arg : args) {
                if (arg != null) {
                    builder.append(arg.toString());
                }
                builder.append(" ");
            }
        }
    }
}
