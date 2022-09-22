package me.iksadnorth.insta.exception;

public class InstaApplicationException extends RuntimeException {
    private ErrorCode errorCode;
    private String message;

    public InstaApplicationException(ErrorCode errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public InstaApplicationException(ErrorCode errorCode) {
        new InstaApplicationException(errorCode, null);
    }

    @Override
    public String getMessage() {
        return (this.message != null)
                ? String.format("%s. %s", errorCode.getMessage(), message)
                : errorCode.getMessage();
    }

    @Override
    public String toString() {
        return message != null
                ? String.format("\n%s \n%s \n%s", super.toString(), errorCode.getMessage(), message)
                : String.format("\n%s \n%s", super.toString(), errorCode.getMessage());
    }
}
