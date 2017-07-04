package pro.devlib.exception;


public class UnsupportedRequestParameterException extends RuntimeException {

  private final static String UNSUPPORTED_REQUEST_PARAM_EXCEPTION_MESSAGE = "Exception during adding parameters to request.";

  public UnsupportedRequestParameterException(Throwable cause) {
    this(UNSUPPORTED_REQUEST_PARAM_EXCEPTION_MESSAGE, cause);
  }

  private UnsupportedRequestParameterException(String message, Throwable cause) {
    super(message, cause);
  }

}
