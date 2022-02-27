package execution;

public class ResultCode {
    private int returnCode;
    private boolean exitSignal;

    private static ResultCode okCode = new ResultCode(0, false);

    public static ResultCode okCode() {
        return okCode;
    }

    public ResultCode(int returnCode, boolean exitSignal) {
        this.returnCode = returnCode;
        this.exitSignal = exitSignal;
    }

    //TODO: remove if not needed
    public void setReturnCode(int returnCode) {
        this.returnCode = returnCode;
    }

    public int getReturnCode() {
        return returnCode;
    }

    //TODO: remove if not needed
    public void setExitSignal(boolean exitSignal) {
        this.exitSignal = exitSignal;
    }

    public boolean isExitSignal() {
        return exitSignal;
    }
}
