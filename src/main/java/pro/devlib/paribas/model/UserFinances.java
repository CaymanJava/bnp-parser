package pro.devlib.paribas.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;

@Builder
@EqualsAndHashCode
public class UserFinances {

  @Getter
  private final Amount sumAmount;
  @Getter
  private final List<Account> accounts;
  @Getter
  private final List<Card> cards;

}
