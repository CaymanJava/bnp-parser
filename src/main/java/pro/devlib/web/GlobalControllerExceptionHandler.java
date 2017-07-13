package pro.devlib.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import pro.devlib.paribas.dto.ExceptionDto;
import pro.devlib.paribas.exception.BadCredentialsException;
import pro.devlib.paribas.exception.IncorrectParameterException;
import pro.devlib.paribas.exception.ServiceTemporarilyUnavailableException;

@ControllerAdvice
@Slf4j
public class GlobalControllerExceptionHandler {

  private final static String MESSAGE = "Service unavailable.";

  @ExceptionHandler(Exception.class)
  @ResponseBody
  ExceptionDto defaultErrorHandler(Exception e) throws Exception {
    log.warn("Exception: ", e);
    if (e instanceof BadCredentialsException || e instanceof ServiceTemporarilyUnavailableException || e instanceof IncorrectParameterException) {
      return new ExceptionDto(e.getMessage());
    } else {
      return new ExceptionDto(MESSAGE);
    }
  }

}
