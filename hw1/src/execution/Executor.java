package execution;

import java.util.List;
import jdk.jshell.spi.ExecutionControl.NotImplementedException;

public class Executor {
    private final String buffer = ""; // TODO: Executables должны иметь доступ к buffer. Либо execute() возвращать String

    ResultCode execute(List<Executable> inStmts) throws NotImplementedException {
        throw new NotImplementedException("Not yet");
    }

    String getBuffer() {
        return buffer;
    }
}
