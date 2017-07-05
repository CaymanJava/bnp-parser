package pro.devlib.paribas.model;


import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.math.BigDecimal;

@EqualsAndHashCode
@AllArgsConstructor
public class Amount {

  @Getter
  private final BigDecimal value;
  @Getter
  private final String currency;

  public Amount(double value) {
    this(value, "PLN");
  }

  public Amount(double value, String currency) {
    this.value = new BigDecimal(value).setScale(2, BigDecimal.ROUND_HALF_UP);
    this.currency = currency;
  }

}
