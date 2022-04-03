package pl.beusable.api;


import static java.util.Optional.ofNullable;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.beusable.api.error.ApiException;
import pl.beusable.reservation.ReservationService;
import pl.beusable.reservation.dto.ReservationDto;
import pl.beusable.reservation.dto.ReservationsSummaryDto;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/reservation")
@AllArgsConstructor
public class ReservationController {

  final ReservationService reservationService;

  @GetMapping("all")
  public List<ReservationDto> getAllReservations() {
    return List.of(ReservationDto.builder().build());
  }

  @PostMapping
  public ReservationDto createReservation(final String clientName, final String price) {
    return reservationService.createReservation(clientName, new BigDecimal(price));
  }

  @PostMapping("batch")
  public ReservationsSummaryDto createReservation(@RequestBody final List<String> reservationPrice) {
    final List<String> batchData = ofNullable(reservationPrice).orElseGet(List::of);
    if (!batchData.isEmpty()) {
      return reservationService.createReservations(batchData);
    }
    throw ApiException.builder().error("Empty input data").build();
  }
}
