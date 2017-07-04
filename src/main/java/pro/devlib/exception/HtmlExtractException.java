package pro.devlib.exception;


public class HtmlExtractException extends RuntimeException {

  private final static String HTML_EXTRACT_EXCEPTION_MESSAGE = "Exception during extract HTML from response.";

  public HtmlExtractException(Throwable cause) {
    this(HTML_EXTRACT_EXCEPTION_MESSAGE, cause);
  }

  private HtmlExtractException(String message, Throwable cause) {
    super(message, cause);
  }

}
