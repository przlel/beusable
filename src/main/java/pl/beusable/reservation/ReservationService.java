package pl.beusable.reservation;

import static pl.beusable.room.RoomType.STANDARD;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.beusable.reservation.dto.ReservationDto;
import pl.beusable.reservation.storage.ReservationRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@Service
@AllArgsConstructor
public class ReservationService {

  private final ReservationRepository reservationRepository;

  public List<ReservationDto> getAllReservations() {
    return reservationRepository.getAllReservations();
  }

  public Optional<ReservationDto> findReservationWithUpgradeOption() {
    final List<ReservationDto> allReservations = reservationRepository.getAllReservations();
    final Predicate<ReservationDto> standardRoomFilter = reservation -> reservation.getAssignedRoom().getRoomType() == STANDARD;
    return allReservations.stream()
        .filter(standardRoomFilter)
        .max(Comparator.comparing(ReservationDto::getPrice));
  }

}
