package org.example.util;

public final class StringChecker {
    private StringChecker(){
        throw new UnsupportedOperationException("Cannot construct a utility class (StringChecker).");
    }

    // Check if the `source` string is not NULL or EMPTY. If so, return the `resolution` string.
    public static String resolveIfNullOrEmpty(String source, String resolution){
        if(source == null || source.isEmpty()){
            return resolution;
        }
        return source;
    }

    public static boolean notNullOrEmpty(String source){
        return source != null && !source.isEmpty();
    }
}
