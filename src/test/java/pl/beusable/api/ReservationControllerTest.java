package pl.beusable.api;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
class ReservationControllerTest {

  @LocalServerPort
  private int serverPort;

  @Test
  public void should_test_happy_flow_in_application() {
    final String s = "http://localhost:" + serverPort + "/room?premiumRoomCount=10&economyRoomCount=2";
    RestAssured.given()
        .post("http://localhost:" + serverPort + "/room?premiumRoomCount=10&economyRoomCount=2")
        .then()
        .statusCode(HttpStatus.OK.value());

    RestAssured.given()
        .body("[\"23\", \"45\", \"155\", \"374\", \"22\", \"99.99\", \"100\", \"101\", \"115\", \"209\"]")
        .contentType(ContentType.JSON)
        .post("http://localhost:" + serverPort + "/reservation/batch")
        .then()
        .statusCode(HttpStatus.OK.value())
        .body("dailyIncome", equalTo(1243.99F))
        .body("reservations.size()", is(10));
  }

  @Test
  public void should_return_error_when_no_room_available() {
    final String s = "http://localhost:" + serverPort + "/room?premiumRoomCount=10&economyRoomCount=2";

    RestAssured.given()
        .body("[\"23\", \"45\", \"155\", \"374\", \"22\", \"99.99\", \"100\", \"101\", \"115\", \"209\"]")
        .contentType(ContentType.JSON)
        .post("http://localhost:" + serverPort + "/reservation/batch")
        .then()
        .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
        .body("message", equalTo("No room available"));
  }
}