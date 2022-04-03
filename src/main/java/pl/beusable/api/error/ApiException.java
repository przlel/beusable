package pl.beusable.api.error;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ApiException extends RuntimeException {
  private String error;
}
