package pro.devlib.exception;


import static pro.devlib.util.Constants.UNSUPPORTED_REQUEST_PARAM_EXCEPTION_MESSAGE;

public class UnsupportedRequestParameterException extends RuntimeException {


    public UnsupportedRequestParameterException(Throwable cause) {
        this(UNSUPPORTED_REQUEST_PARAM_EXCEPTION_MESSAGE, cause);
    }

    private UnsupportedRequestParameterException(String message, Throwable cause) {
        super(message, cause);
    }
}
