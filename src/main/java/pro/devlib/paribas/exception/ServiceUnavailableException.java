package pro.devlib.paribas.exception;

public class ServiceUnavailableException extends RuntimeException {

  private final static String MESSAGE = "Service unavailable.";

  public ServiceUnavailableException(Exception e) {
    super(MESSAGE, e);
  }

}
