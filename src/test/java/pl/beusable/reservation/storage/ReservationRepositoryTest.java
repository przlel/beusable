package pl.beusable.reservation.storage;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static pl.beusable.TestConst.FIRST_RESERVATION_PREMIUM_ROOM;
import static pl.beusable.TestConst.FIRST_RESERVATION_STD_ROOM;
import static pl.beusable.TestConst.SECOND_RESERVATION_STD_ROOM;

import org.junit.jupiter.api.Test;
import pl.beusable.reservation.dto.ReservationDto;

import java.util.List;

class ReservationRepositoryTest {

  final ReservationRepository reservationRepository = new ReservationRepository();

  @Test
  void should_persist_reservation_in_storage() {
    reservationRepository.save(FIRST_RESERVATION_STD_ROOM);
    reservationRepository.save(SECOND_RESERVATION_STD_ROOM);

    assertThat(reservationRepository.getAllReservations())
        .isNotEmpty()
        .containsExactlyInAnyOrder(FIRST_RESERVATION_STD_ROOM, SECOND_RESERVATION_STD_ROOM);
  }

  @Test
  void should_return_all_reservations_as_unmodifiable_collection() {
    reservationRepository.save(FIRST_RESERVATION_STD_ROOM);

    final List<ReservationDto> allReservations = reservationRepository.getAllReservations();

    final Throwable modificationException =
        catchThrowable(() -> allReservations.add(FIRST_RESERVATION_PREMIUM_ROOM));

    assertThat(modificationException).isInstanceOf(UnsupportedOperationException.class);
    assertThat(reservationRepository.getAllReservations()).containsExactly(FIRST_RESERVATION_STD_ROOM);
  }


}