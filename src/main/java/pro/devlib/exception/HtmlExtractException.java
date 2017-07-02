package pro.devlib.exception;


import static pro.devlib.util.Constants.HTML_EXTRACT_EXCEPTION_MESSAGE;

public class HtmlExtractException extends RuntimeException {

    public HtmlExtractException(Throwable cause) {
        this(HTML_EXTRACT_EXCEPTION_MESSAGE, cause);
    }

    private HtmlExtractException(String message, Throwable cause) {
        super(message, cause);
    }
}
