package pro.devlib.paribas.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class StatementDto {

  @Getter
  private final String systemDate;
  @Getter
  private final int templatesIdSize;

}
