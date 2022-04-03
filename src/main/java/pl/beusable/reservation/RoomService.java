package pl.beusable.reservation;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.beusable.reservation.storage.RoomRepository;
import pl.beusable.room.RoomDto;
import pl.beusable.room.RoomType;

import java.util.List;

@Service
@AllArgsConstructor
public class RoomService {

  private final RoomRepository roomRepository;

  public List<RoomDto> findAvailableRooms() {
    return roomRepository.getAvailableRooms();
  }

  public List<RoomDto> findAvailableRoomsOfType(final RoomType roomType) {
    return roomRepository.getAvailableRoomsOfType(roomType);
  }

}
