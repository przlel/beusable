package pl.beusable.reservation.storage;

import org.springframework.stereotype.Component;
import pl.beusable.reservation.dto.ReservationDto;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class ReservationRepository {
  private final Set<ReservationDto> reservationStorage = new HashSet<>();

  public void save(ReservationDto reservation) {
    reservationStorage.add(reservation);
  }

  public List<ReservationDto> getAllReservations() {
    return List.copyOf(reservationStorage);
  }
}
