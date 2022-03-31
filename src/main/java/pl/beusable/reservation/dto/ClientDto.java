package pl.beusable.reservation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class ClientDto {
  private final String name;
}
