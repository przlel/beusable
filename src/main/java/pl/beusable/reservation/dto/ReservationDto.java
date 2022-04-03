package pl.beusable.reservation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import pl.beusable.room.RoomDto;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
public class ReservationDto {
  private final ClientDto client;
  private final BigDecimal price;
  private final RoomDto assignedRoom;
}
