package pro.devlib.exception;


public class ParsePageException extends RuntimeException {

  public ParsePageException(String message) {
    super(message);
  }

  public ParsePageException(String message, Throwable cause) {
    super(message, cause);
  }

}
