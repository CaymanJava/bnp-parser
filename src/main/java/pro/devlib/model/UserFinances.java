package pro.devlib.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class UserFinances {
    private Amount sumAmount;
    private List<Account> accounts;
    private List<Card> cards;
}
