package pl.beusable.reservation.storage;

import org.springframework.stereotype.Component;
import pl.beusable.room.RoomDto;
import pl.beusable.room.RoomType;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Component
public class RoomRepository {
  private final List<RoomDto> roomStorage = new ArrayList<>();

  public void save(final RoomDto room) {
    roomStorage.add(room);
  }

  public List<RoomDto> getAllRooms() {
    return List.copyOf(roomStorage);
  }

  public List<RoomDto> getAvailableRoomsOfType(final RoomType roomType) {
    final Predicate<RoomDto> isRoomAvailable = RoomDto::isRoomAvailable;
    final Predicate<RoomDto> roomTypePredicate = room -> room.getRoomType() == roomType;
    return getRooms(isRoomAvailable.and(roomTypePredicate));
  }

  public List<RoomDto> getAvailableRooms() {
    return getRooms(RoomDto::isRoomAvailable);
  }

  private List<RoomDto> getRooms(final Predicate<RoomDto> filter) {
    return roomStorage.stream()
        .filter(filter)
        .collect(Collectors.toList());
  }
}
