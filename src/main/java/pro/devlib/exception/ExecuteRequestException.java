package pro.devlib.exception;

import static pro.devlib.util.Constants.EXECUTE_REQUEST_EXCEPTION_MESSAGE;

public class ExecuteRequestException extends RuntimeException {
    public ExecuteRequestException(Throwable cause) {
        super(EXECUTE_REQUEST_EXCEPTION_MESSAGE, cause);
    }
}
