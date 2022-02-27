package main.java.execution;

import java.util.List;

public class Executor {
    private StringBuilder buffer;

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

    public String getBuffer() {
        return buffer.toString();
    }
}
