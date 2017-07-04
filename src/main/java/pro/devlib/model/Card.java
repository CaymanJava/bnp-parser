package pro.devlib.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Card {

  private String name;
  private String holderName;
  private String number;

}
