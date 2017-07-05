package pro.devlib.paribas.dto;

import lombok.Builder;
import lombok.Getter;
import pro.devlib.paribas.model.Account;
import pro.devlib.paribas.model.Amount;
import pro.devlib.paribas.model.Card;

import java.util.List;

@Builder
public class DesktopInfoDto {

  @Getter
  private final String rndParameter;
  @Getter
  private final Amount sumAmount;
  @Getter
  private final List<Account> accounts;
  @Getter
  private final List<Card> cards;

}
