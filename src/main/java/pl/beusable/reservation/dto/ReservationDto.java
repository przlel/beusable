package pl.beusable.reservation.dto;

import lombok.Builder;
import lombok.Data;
import pl.beusable.room.Room;

@Data
@Builder
public class ReservationDto {
  private final ClientDto client;
  //FIXME
  private final int price;
  private boolean roomUpgradeAvailable;
  private final Room assignedRoom;
}
