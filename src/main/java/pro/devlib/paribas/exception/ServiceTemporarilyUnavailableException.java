package pro.devlib.paribas.exception;


public class ServiceTemporarilyUnavailableException extends RuntimeException {

  private final static String MESSAGE = "Sorry, service temporarily unavailable.";

  public ServiceTemporarilyUnavailableException() {
    super(MESSAGE);
  }

}
