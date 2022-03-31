package pl.beusable;


import static org.mockito.Mockito.spy;

import pl.beusable.dev.RandomDataGenerator;
import pl.beusable.reservation.dto.ReservationDto;

public class TestConst {
  public static ReservationDto FIRST_RESERVATION_STD_ROOM = spy(RandomDataGenerator.generateReservation(35,95));
  public static ReservationDto SECOND_RESERVATION_STD_ROOM = spy(RandomDataGenerator.generateReservation(35,95));
  public static ReservationDto FIRST_RESERVATION_PREMIUM_ROOM = spy(RandomDataGenerator.generateReservation(101,1234));
  public static ReservationDto UPGRADABLE_RESERVATION_STD_ROOM = spy(RandomDataGenerator.generateReservation(96,99));
}
