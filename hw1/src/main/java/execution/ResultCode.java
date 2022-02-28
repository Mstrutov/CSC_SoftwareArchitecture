package execution;

/**
 * Class that contains result code of the command and exit signal if needed.
 */
public class ResultCode {
    private int returnCode;
    private boolean exitSignal;

    private static final ResultCode okCode = new ResultCode(0, false);
    private static final ResultCode exitCode = new ResultCode(0, true);

    /**
     * Default successful result code of the program
     *
     * @return {@code ResultCode}
     */
    public static ResultCode okCode() {
        return okCode;
    }

    /**
     * Default successful exit code of the program
     *
     * @return {@code ResultCode}
     */
    public static ResultCode exitCode() {
        return exitCode;
    }

    /**
     * Result code constructor
     *
     * @param returnCode Return code of the program
     * @param exitSignal Exit signal if passed
     */
    public ResultCode(int returnCode, boolean exitSignal) {
        this.returnCode = returnCode;
        this.exitSignal = exitSignal;
    }

    /**
     * Set {@code returnCode}
     *
     * @param returnCode Return code of the program
     */
    //TODO: remove if not needed
    public void setReturnCode(int returnCode) {
        this.returnCode = returnCode;
    }


    /**
     * Get return code
     *
     * @return Return code of the program
     */
    public int getReturnCode() {
        return returnCode;
    }

    /**
     * Set exit signal of the program
     *
     * @param exitSignal ExitSignal flag
     */
    //TODO: remove if not needed
    public void setExitSignal(boolean exitSignal) {
        this.exitSignal = exitSignal;
    }

    /**
     * Get exit signal
     *
     * @return True if program passed exit signal, false otherwise
     */
    public boolean isExitSignal() {
        return exitSignal;
    }
}
