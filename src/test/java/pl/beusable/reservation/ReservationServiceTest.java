package pl.beusable.reservation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static pl.beusable.TestConst.FIRST_RESERVATION_PREMIUM_ROOM;
import static pl.beusable.TestConst.FIRST_RESERVATION_STD_ROOM;
import static pl.beusable.TestConst.SECOND_RESERVATION_STD_ROOM;
import static pl.beusable.TestConst.UPGRADABLE_RESERVATION_STD_ROOM;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.beusable.reservation.dto.ReservationDto;
import pl.beusable.reservation.storage.ReservationRepository;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

  @Mock
  private ReservationRepository reservationRepository;

  @InjectMocks
  private ReservationService reservationService;

  @Test
  void should_return_all_reservations_without_modification() {
    final List<ReservationDto> allReservations = List.of(FIRST_RESERVATION_PREMIUM_ROOM, SECOND_RESERVATION_STD_ROOM);
    when(reservationRepository.getAllReservations()).thenReturn(allReservations);

    final List<ReservationDto> reservations = reservationService.getAllReservations();

    assertThat(reservations).containsExactlyElementsOf(allReservations);
    verifyNoInteractions(FIRST_RESERVATION_PREMIUM_ROOM, SECOND_RESERVATION_STD_ROOM);
  }

  @Test
  void should_find_reservation_with_room_upgrade_possibility() {
    final List<ReservationDto> allReservations =
        List.of(FIRST_RESERVATION_PREMIUM_ROOM, UPGRADABLE_RESERVATION_STD_ROOM, SECOND_RESERVATION_STD_ROOM, FIRST_RESERVATION_STD_ROOM);
    when(reservationRepository.getAllReservations()).thenReturn(allReservations);

    final Optional<ReservationDto> reservationWithUpgradeOption = reservationService.findReservationWithUpgradeOption();
    assertThat(reservationWithUpgradeOption).isNotEmpty().contains(UPGRADABLE_RESERVATION_STD_ROOM);
  }
}