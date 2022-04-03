package pl.beusable.api;


import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.beusable.reservation.storage.RoomRepository;
import pl.beusable.room.RoomDto;
import pl.beusable.room.RoomType;

import java.util.List;

@RestController
@RequestMapping("/room")
@AllArgsConstructor
public class RoomController {

  private RoomRepository roomRepository;

  @PostMapping
  public List<RoomDto> configureRooms(@RequestParam("economyRoomCount") final int economyRoomCount,
                                      @RequestParam("premiumRoomCount") final int premiumRoomCount) {
    for (int x = 0; x < economyRoomCount; ++x) {
      roomRepository.save(new RoomDto(RoomType.STANDARD, true));
    }
    for (int x = 0; x < premiumRoomCount; ++x) {
      roomRepository.save(new RoomDto(RoomType.PREMIUM, true));
    }
    return roomRepository.getAvailableRooms();
  }

}
