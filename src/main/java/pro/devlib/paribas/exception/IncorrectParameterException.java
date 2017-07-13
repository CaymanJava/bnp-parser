package pro.devlib.paribas.exception;


public class IncorrectParameterException extends RuntimeException {

  private final static String MESSAGE = "Number of months should be more (or equal) than one.";

  public IncorrectParameterException() {
    super(MESSAGE);
  }
}
