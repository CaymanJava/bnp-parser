package pro.devlib.paribas.model;

import lombok.*;

import java.util.List;

@Builder
@EqualsAndHashCode
public class Account {

  @Getter
  private final String name;
  @Getter
  private final String number;
  @Getter
  private final Amount balance;
  @Getter @Setter
  private List<Transaction> transactions;

}
