package execution;

import java.util.List;

public class Executor {
    private final String buffer = ""; // TODO: Executables должны иметь доступ к buffer. Либо execute() возвращать String

    ResultCode execute(List<Executable> inStmts) {
        throw new UnsupportedOperationException();
    }

    String getBuffer() {
        return buffer;
    }
}
