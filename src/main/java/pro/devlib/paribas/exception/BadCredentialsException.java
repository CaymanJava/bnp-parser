package pro.devlib.paribas.exception;


public class BadCredentialsException extends RuntimeException {

  private final static String MESSAGE = "Please, provide valid credentials";

  public BadCredentialsException() {
    super(MESSAGE);
  }

}
