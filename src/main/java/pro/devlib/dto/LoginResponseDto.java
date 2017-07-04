package pro.devlib.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class LoginResponseDto {

  private List<String> passwordSymbols;
  private String flowId;
  private String stateId;
  private String actionToken;
  private String action;
  private String sid;
  private String loginMask;

}
