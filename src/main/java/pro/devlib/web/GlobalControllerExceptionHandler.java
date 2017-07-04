package pro.devlib.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import pro.devlib.dto.ExceptionDto;

@ControllerAdvice
@Slf4j
public class GlobalControllerExceptionHandler {

  @ExceptionHandler(Exception.class)
  @Order(Ordered.LOWEST_PRECEDENCE)
  @ResponseBody
  ExceptionDto defaultErrorHandler(Exception e) throws Exception {
    log.warn("Exception: ", e);
    return new ExceptionDto(e.getMessage());
  }

}
