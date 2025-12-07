package mapping;

import java.util.*;

public class ExecutionTracker {
    private static final ThreadLocal<String> currentTest = new ThreadLocal<>();
    private static final Map<String, Set<String>> testToMethods = new HashMap<>();
    private static final Map<String, List<Long>> methodDurations = new HashMap<>();

    public static void setCurrentTest(String testName) {
        currentTest.set(testName);
        testToMethods.putIfAbsent(testName, new HashSet<>());
    }

    public static void logMethod(String className, String methodName) {
        String test = currentTest.get();
        if (test != null) {
            testToMethods.get(test).add(className + "." + methodName);
        }
    }

    public static void logDuration(String method, long nanos) {
        methodDurations.computeIfAbsent(method, k -> new ArrayList<>()).add(nanos);
    }

    public static void writeReport(String path) throws Exception {
        Map<String, Object> out = new HashMap<>();
        out.put("tests", testToMethods);
        //out.put("durations_nanos", methodDurations);
        new com.fasterxml.jackson.databind.ObjectMapper().writerWithDefaultPrettyPrinter()
            .writeValue(new java.io.File(path), out);
    }
}
