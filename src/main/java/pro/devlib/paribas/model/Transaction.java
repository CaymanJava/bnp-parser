package pro.devlib.paribas.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDate;

@Builder
@EqualsAndHashCode
public class Transaction {

  @JsonSerialize(using = ToStringSerializer.class)
  @Getter
  private final LocalDate date;
  @Getter
  private final String description;
  @Getter
  private final Amount amount;
  @Getter
  private final Amount balanceAfterOperation;

}
