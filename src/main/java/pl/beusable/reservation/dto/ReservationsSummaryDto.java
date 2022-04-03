package pl.beusable.reservation.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class ReservationsSummaryDto {
  private BigDecimal dailyIncome;
  private List<ReservationDto> reservations;
}
