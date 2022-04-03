package pl.beusable;

import static lombok.AccessLevel.PRIVATE;

import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor(access = PRIVATE)
public class Const {
  public static BigDecimal HIGHEST_ECONOMY_ROOM_PRICE = new BigDecimal(100);
}
