package hu.fitforfun.exception;

public class FitforfunException extends Exception {
    private ErrorCode errorCode;

    public FitforfunException(String message) {
        super(message);
    }

    public FitforfunException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public FitforfunException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
