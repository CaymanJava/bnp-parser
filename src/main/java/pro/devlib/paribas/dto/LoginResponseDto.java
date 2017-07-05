package pro.devlib.paribas.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
public class LoginResponseDto {

  @Getter
  private final List<String> passwordSymbols;
  @Getter
  private final String flowId;
  @Getter
  private final String stateId;
  @Getter
  private final String actionToken;
  @Getter
  private final String action;
  @Getter
  private final String sid;
  @Getter
  private final String loginMask;

}
