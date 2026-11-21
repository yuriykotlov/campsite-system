package org.example.util;

import org.example.data_structures.LinkedList;

public class StringChecker {
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

    // Concatenate a LinkedList of type String into one String variable
    public static String LinkedListToString(LinkedList<String> linkedList){
        String finalString = "";
        for(String string : linkedList){
            finalString += string;
        }
        return finalString;
    }
}
