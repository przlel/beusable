package pl.beusable.reservation.storage;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import org.junit.jupiter.api.Test;
import pl.beusable.reservation.dto.ReservationDto;

import java.util.List;
import java.util.Random;

class ReservationRepositoryTest {

  final ReservationRepository reservationRepository = new ReservationRepository();

  @Test
  void should_persist_reservation_in_storage() {
    final int firstRoomPrice = new Random().nextInt();
    final int secondRoomPrice = new Random().nextInt();
    final ReservationDto firstReservationDto = ReservationDto.builder().price(firstRoomPrice).build();
    final ReservationDto secondReservationDto = ReservationDto.builder().price(secondRoomPrice).build();

    reservationRepository.save(firstReservationDto);
    reservationRepository.save(secondReservationDto);

    assertThat(reservationRepository.getAllReservations())
        .isNotEmpty()
        .containsExactlyInAnyOrder(firstReservationDto, secondReservationDto);
  }

  @Test
  void should_return_all_reservations_as_unmodifiable_collection() {
    final ReservationDto firstReservation = ReservationDto.builder().price(new Random().nextInt()).build();
    reservationRepository.save(firstReservation);

    final List<ReservationDto> allReservations = reservationRepository.getAllReservations();

    final Throwable modificationException =
        catchThrowable(() -> allReservations.add(ReservationDto.builder().price(new Random().nextInt()).build()));

    assertThat(modificationException).isInstanceOf(UnsupportedOperationException.class);
    assertThat(reservationRepository.getAllReservations()).containsExactly(firstReservation);
  }


}