package pl.beusable;

import static pl.beusable.room.RoomType.PREMIUM;
import static pl.beusable.room.RoomType.STANDARD;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.beusable.room.Room;

import java.util.List;

@SpringBootApplication
public class RoomManagerApplication {
  
  public static void main(String[] args) {
    SpringApplication.run(RoomManagerApplication.class, args);
  }

}
