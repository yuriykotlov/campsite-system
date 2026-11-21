package org.example.util;

import org.example.data_structures.LinkedList;

public class ListHelper {
    private static void addIfNotBlank(LinkedList<String> list, String item){
        String trimmed = item.trim();

        if(!trimmed.isEmpty()){
            list.add(trimmed);
        }
    }

    public static LinkedList<String> parseList(String stringList){
        if(stringList == null) return new LinkedList<>();

        LinkedList<String> list = new LinkedList<>();

        String currentObject = "";

        for(int i = 0; i < stringList.length(); i++){
            char c = stringList.charAt(i);

            if(c == ','){
                addIfNotBlank(list, currentObject);
                currentObject = "";

            } else {
                currentObject += c;
            }
        }

        addIfNotBlank(list, currentObject);

        return list;
    }

    public static String joinList(LinkedList<String> list){
        if(list == null) return "";

        String stringList = "";

        for(String item : list){
            if(!stringList.isEmpty()) stringList += ",";
            stringList += item;
        }

        return stringList;
    }
}
