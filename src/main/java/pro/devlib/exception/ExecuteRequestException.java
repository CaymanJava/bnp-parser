package pro.devlib.exception;


public class ExecuteRequestException extends RuntimeException {

  private final static String EXECUTE_REQUEST_EXCEPTION_MESSAGE = "Exception during executing request.";

  public ExecuteRequestException(Throwable cause) {
        super(EXECUTE_REQUEST_EXCEPTION_MESSAGE, cause);
    }

}
