package pro.devlib.dto;

import lombok.Builder;
import lombok.Data;
import pro.devlib.model.Account;
import pro.devlib.model.Amount;
import pro.devlib.model.Card;

import java.util.List;

@Builder
@Data
public class DesktopInfoDto {
    private String rndParameter;
    private Amount sumAmount;
    private List<Account> accounts;
    private List<Card> cards;
}
