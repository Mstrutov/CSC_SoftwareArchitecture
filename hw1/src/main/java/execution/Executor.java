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
     * @return {@code ResultCode} of the programm
     */
    public ResultCode execute(List<Executable> inStmts) {
        buffer = new StringBuilder();
        if (inStmts == null || inStmts.size() == 0) {
            return ResultCode.okCode();
        }

        if (inStmts.size() > 1) {
            throw new UnsupportedOperationException();
        }
        ResultCode resultCode = null;
        for (Executable inStmt : inStmts) {
            resultCode = inStmt.execute(buffer);
            if (resultCode.getReturnCode() != 0) {
                return resultCode;
            }
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
