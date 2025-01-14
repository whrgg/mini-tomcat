package com.traveller.utils;

import java.util.*;

public class InitParameters extends LazyMap{

    public InitParameters(){
        super(false);
    }

    public boolean setInitParameter(String name, String value) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name is null or empty.");
        }
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException("Value is null or empty.");
        }
        if (super.containsKey(name)) {
            return false;
        }
        super.put(name, value);
        return true;
    }

    public String getInitParameter(String name) {
        return super.get(name).toString();
    }


    public Set<String> setInitParameters(Map<String, String> initParameters) {
        if (initParameters == null) {
            throw new IllegalArgumentException("initParameters is null.");
        }
        if (initParameters.isEmpty()) {
            return Set.of();
        }
        Set<String> conflicts = new HashSet<>();
        for (String name : initParameters.keySet()) {
            String value = initParameters.get(name);
            if (value == null) {
                throw new IllegalArgumentException("initParameters contains null value by name: " + name);
            }
            if (super.containsKey(name)) {
                conflicts.add(name);
            } else {
                super.put(name, value);
            }
        }
        return conflicts;
    }

    public Map<String, String> getInitParameters() {
        return super.map();
    }

    public Enumeration<String> getInitParameterNames() {
        return Collections.enumeration(super.map().keySet());
    }

}
