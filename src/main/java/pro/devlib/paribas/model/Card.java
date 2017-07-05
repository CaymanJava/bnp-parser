package pro.devlib.paribas.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Builder
@EqualsAndHashCode
public class Card {
  @Getter
  private final String name;
  @Getter
  private final String holderName;
  @Getter
  private final String number;

}
