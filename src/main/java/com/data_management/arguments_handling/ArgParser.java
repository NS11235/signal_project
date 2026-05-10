package com.data_management.arguments_handling;

import java.util.HashMap;
import java.util.Map;

public class ArgParser implements ArgReader {

    String[] args;
    Map<String, String> map;

    public ArgParser(String[] args){
        this.args = args;
    }

    @Override
    public Map<String, String> readArguments(){
        map = new HashMap<>();
        for(int i = 0; i < args.length; i++){
            if(args[i].equals("--output") && args[i+1].startsWith("file:")) {
                map.put("path", args[i+1].substring("file:".length()));
            }
        }
        return map;
    }

    public String getPath(){
        readArguments();
        String path = "path";
        return map.get(path).toString();
    }

}
