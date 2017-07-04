package pro.devlib.model;


import lombok.Getter;

public class ZeroAmount extends Amount {

  @Getter
  private final double value = 0;
  @Getter
  private final String currency = "PLN";

  public ZeroAmount() {
    super();
  }

}
