package main.java.environment;

import java.util.HashMap;
import java.util.Map;

public class Environment {
    private Map<String, String> variables = new HashMap<>();

    public String getValue(String variableName) {
        return variables.get(variableName);
    }

    public String setValue(String variableName, String variableValue) {
        return variables.put(variableName, variableValue);
    }
}
