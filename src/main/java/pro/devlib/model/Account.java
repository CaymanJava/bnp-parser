package pro.devlib.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class Account {

  private String name;
  private String number;
  private Amount balance;
  private List<Transaction> transactions;

}
