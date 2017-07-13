package pro.devlib.paribas.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class UserCredentialsDto {

  @Getter
  private final String login;
  @Getter
  private final String password;
  @Getter
  private final int monthsToParse;
  @Getter
  private final boolean useHttpUnit;

}
