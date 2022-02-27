package execution;

import java.util.List;

public class Executor {
    private final StringBuilder buffer = new StringBuilder();

    ResultCode execute(List<Executable> inStmts) {
        if (inStmts == null || inStmts.size() == 0) {
            return ResultCode.okCode();
        }

        if (inStmts.size() > 1) {
            throw new UnsupportedOperationException();
        }

        for (Executable inStmt : inStmts) {
            inStmt.execute(buffer);
        }

        System.out.println(buffer);

        return ResultCode.okCode();
    }

    String getBuffer() {
        return buffer.toString();
    }
}
