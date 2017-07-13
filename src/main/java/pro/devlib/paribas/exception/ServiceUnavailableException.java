package pro.devlib.paribas.exception;

public class ServiceUnavailableException extends RuntimeException {

  public ServiceUnavailableException(Exception e) {
    super(e);
  }

}
