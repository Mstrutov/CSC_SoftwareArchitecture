package environment;

import java.util.HashMap;
import java.util.Map;

/**
 * Storage for environment variables of the REPL
 */
public class Environment {
    private final Map<String, String> variables = new HashMap<>();

    /**
     * Get the value of an environment variable
     *
     * @param variableName Environment variable name
     * @return Value of environment variable, if it is set, otherwise empty string.
     */
    public String getValue(String variableName) {
        return variables.getOrDefault(variableName, "");
    }

    /**
     * Set the value of an environment variable
     *
     * @param variableName  Environment variable name
     * @param variableValue Value of environment variable
     */
    public void setValue(String variableName, String variableValue) {
        variables.put(variableName, variableValue);
    }
}
