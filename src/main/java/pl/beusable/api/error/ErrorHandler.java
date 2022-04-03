package pl.beusable.api.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@ControllerAdvice(annotations = RestController.class)
public class ErrorHandler {

  @ExceptionHandler
  //should be more specific
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ResponseBody
  public Map<String, String> onApiError(final ApiException apiException) {
    return Map.of("message", apiException.getError());
  }
}
