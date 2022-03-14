package execution;

import java.util.List;

/**
 * Last part of the REPL, class that executes parsed command.
 */
public class Executor {
    private StringBuilder buffer;

    /**
     * Execute pipe of small commands as a big command.
     *
     * @param inStmts List of small commands
     * @return {@code ResultCode} of the program
     */
    public ResultCode execute(List<Executable> inStmts) {
        buffer = new StringBuilder();
        if (inStmts == null || inStmts.size() == 0) {
            return ResultCode.okCode();
        }

        ResultCode resultCode = null;
        int commandNumber = 0;
        for (Executable inStmt : inStmts) {
            resultCode = inStmt.execute(buffer);
            if (resultCode.isExitSignal()) {
                System.out.println("Execution was abandoned due to exit command, passed # " + commandNumber);
                return resultCode;
            }
            if (resultCode.getReturnCode() != 0) {
                System.out.println("Execution was abandoned due to non-zero return code in command # " + commandNumber);
                return resultCode;
            }
            commandNumber++;
        }

        System.out.println(buffer);

        return resultCode;
    }

    /**
     * Get command's buffer
     *
     * @return Buffer
     */
    public String getBuffer() {
        return buffer.toString();
    }
}
