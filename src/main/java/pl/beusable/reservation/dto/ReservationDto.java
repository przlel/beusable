package pl.beusable.reservation.dto;

import lombok.Builder;
import lombok.Data;
import pl.beusable.room.Room;

@Data
@Builder
public class ReservationDto {
  private final ClientDto client;
  private final Integer price;
  private final Room assignedRoom;
}
