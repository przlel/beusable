package pl.beusable.reservation;

import static pl.beusable.Const.HIGHEST_ECONOMY_ROOM_PRICE;
import static pl.beusable.room.RoomType.PREMIUM;
import static pl.beusable.room.RoomType.STANDARD;

import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.beusable.api.error.ApiException;
import pl.beusable.reservation.dto.ClientDto;
import pl.beusable.reservation.dto.ReservationDto;
import pl.beusable.reservation.dto.ReservationsSummaryDto;
import pl.beusable.reservation.storage.ReservationRepository;
import pl.beusable.reservation.storage.RoomRepository;
import pl.beusable.room.RoomDto;
import pl.beusable.room.RoomType;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@Service
@AllArgsConstructor
@Slf4j
public class ReservationService {
  private final ReservationRepository reservationRepository;
  private final RoomRepository roomRepository;

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

  public ReservationDto createReservation(final String clientName, final BigDecimal price) {
    final boolean isPremiumRoom = HIGHEST_ECONOMY_ROOM_PRICE.compareTo(price) <= 0;
    final RoomType roomType = isPremiumRoom ? PREMIUM : STANDARD ;
    Optional<RoomDto> selectedRoom = findAvailableRoom(roomType);
    if (selectedRoom.isPresent()) {
      return prepareReservation(clientName, price, selectedRoom.get());
    }
    throw ApiException.builder().error("No room available").build();
  }

  public ReservationsSummaryDto createReservations(final List<String> batchReservationsData) {
    addNewReservationsInBatch(batchReservationsData);
    return buildDailyReservationsSummary();
  }

  private ReservationsSummaryDto buildDailyReservationsSummary() {
    final List<ReservationDto> allReservations = reservationRepository.getAllReservations();
    final BigDecimal dailyIncome = allReservations.stream()
        .map(ReservationDto::getPrice)
        .reduce(BigDecimal.ZERO, BigDecimal::add);
    return ReservationsSummaryDto.builder()
        .reservations(allReservations)
        .dailyIncome(dailyIncome)
        .build();
  }

  private void addNewReservationsInBatch(List<String> batchReservationsData) {
    final Faker faker = new Faker();
    batchReservationsData
        .forEach(price -> createReservation(faker.name().firstName(), new BigDecimal(price)));
  }

  private ReservationDto prepareReservation(String clientName, BigDecimal price, RoomDto roomDto) {
    final ClientDto client = new ClientDto(clientName);
    final ReservationDto reservationDto = new ReservationDto(client, price, roomDto);
    roomDto.setRoomAvailable(false);
    reservationRepository.save(reservationDto);
    return reservationDto;
  }

  private Optional<RoomDto> findAvailableRoom(RoomType chosenRoom) {
    final List<RoomDto> availableRoomsOfType = roomRepository.getAvailableRoomsOfType(chosenRoom);
    if (!availableRoomsOfType.isEmpty()) {
      return availableRoomsOfType.stream().findFirst();
    }
    if (chosenRoom == STANDARD) {
      log.debug("Check if premium room available for standard room request", chosenRoom);
      final List<RoomDto> availableRooms = roomRepository.getAvailableRooms();
      return availableRooms.stream().findFirst();
    }
    log.debug("Selected room type {} not found", chosenRoom);
    return Optional.empty();
  }
}
