package execution.commands;

import execution.ResultCode;

/**
 * Command class interface
 */
public interface Binary {
    /**
     * Execute the command with given arguments and buffer
     *
     * @param args   Arguments of the command
     * @param buffer Buffer for the command
     * @return {@code ResultCode} of the command
     */
    ResultCode execute(String[] args, StringBuilder buffer);
}
