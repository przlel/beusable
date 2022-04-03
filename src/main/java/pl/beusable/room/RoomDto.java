package pl.beusable.room;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class RoomDto {
  private RoomType roomType;
  private boolean roomAvailable;
}
