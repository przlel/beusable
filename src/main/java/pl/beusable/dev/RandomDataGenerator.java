package pl.beusable.dev;

import static pl.beusable.room.RoomType.PREMIUM;
import static pl.beusable.room.RoomType.STANDARD;

import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import pl.beusable.reservation.dto.ClientDto;
import pl.beusable.reservation.dto.ReservationDto;
import pl.beusable.reservation.storage.ReservationRepository;
import pl.beusable.room.Room;

import javax.annotation.PostConstruct;

@Component
@Profile("dev")
@AllArgsConstructor
@Slf4j
public class RandomDataGenerator {

  private final ReservationRepository reservationRepository;

  @PostConstruct
  public void generateTestDataForDevelopment() {
    for (int x = 0; x < 20; ++x) {
      reservationRepository.save(generateReservation(35,145));
    }
    log.info("Working in dev mode");
    log.info("Test data generated {}", reservationRepository.getAllReservations());
  }

  public static ReservationDto generateReservation(final int minPrice, final int maxPrice) {
    final Faker faker = new Faker();
    final Integer price = faker.random().nextInt(minPrice, maxPrice);
    final ReservationDto reservation = ReservationDto.builder()
        .price(price)
        .client(ClientDto.builder().name(faker.name().firstName()).build())
        .assignedRoom(price < 100 ? new Room(STANDARD) : new Room(PREMIUM))
        .build();
    return reservation;
  }
}
