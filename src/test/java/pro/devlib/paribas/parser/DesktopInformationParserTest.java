package pro.devlib.paribas.parser;

import org.junit.Test;
import pro.devlib.paribas.dto.DesktopInfoDto;
import pro.devlib.paribas.model.Account;
import pro.devlib.paribas.model.Amount;
import pro.devlib.paribas.model.Card;

import java.nio.file.Files;
import java.nio.file.Paths;

import static junit.framework.TestCase.assertEquals;

public class DesktopInformationParserTest {

  @Test
  public void parseDesktopHtml() throws Exception {
    DesktopInformationParser desktopInformationParser = new DesktopInformationParser();
    String html = new String(Files.readAllBytes(Paths.get("src/test/resources/desktop.html")));
    Account expectedPlnAccount = buildPlnAccount();
    Account expectedEurAccount = buildEurAccount();
    Card expectedCard = buildCard();
    String expectedRndParam = "69109028193279258387208980";
    Amount expectedSumAmount = new Amount(1252263.54, "PLN");

    DesktopInfoDto desktopInfoDto = desktopInformationParser.parseDesktopHtml(html);

    assertEquals(2, desktopInfoDto.getAccounts().size());
    assertEquals(expectedPlnAccount, desktopInfoDto.getAccounts().get(0));
    assertEquals(expectedEurAccount, desktopInfoDto.getAccounts().get(1));
    assertEquals(1, desktopInfoDto.getCards().size());
    assertEquals(expectedCard, desktopInfoDto.getCards().get(0));
    assertEquals(expectedRndParam, desktopInfoDto.getRndParameter());
    assertEquals(expectedSumAmount, desktopInfoDto.getSumAmount());
  }

  private Card buildCard() {
    return Card
            .builder()
            .name("MasterCard Debit Standard")
            .holderName("Gates Bill")
            .number("1111 22** **** 3333")
            .build();
  }

  private Account buildEurAccount() {
    return Account
            .builder()
            .name("RACHUNEK WALUTOWY")
            .balance(new Amount(112041.93, "EUR"))
            .number("88 8888 1462 5454 5252 62623 0042")
            .build();
  }

  private Account buildPlnAccount() {
    return Account
            .builder()
            .name("RACH OSZCZ?DN-ROZLICZEN")
            .balance(new Amount(332635.36, "PLN"))
            .number("11 2222 3333 4444 5555 6666 7777")
            .build();
  }

}
