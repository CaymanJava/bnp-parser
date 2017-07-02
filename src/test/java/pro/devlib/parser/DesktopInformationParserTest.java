package pro.devlib.parser;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pro.devlib.config.SpringApplicationConfig;
import pro.devlib.dto.DesktopInfoDto;
import pro.devlib.model.Account;
import pro.devlib.model.Amount;
import pro.devlib.model.Card;

import java.nio.file.Files;
import java.nio.file.Paths;

import static junit.framework.TestCase.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SpringApplicationConfig.class})
public class DesktopInformationParserTest {

    @Autowired
    private DesktopInformationParser desktopInformationParser;
    private String html;
    private Account expectedPlnAccount;
    private Account expectedEurAccount;
    private Card expectedCard;
    private String expectedRndParam;
    private Amount expectedSumAmount;

    @Before
    public void init() throws Exception{
        html = new String(Files.readAllBytes(Paths.get("src/test/resources/desktop.html")));
        expectedPlnAccount = buildPlnAccount();
        expectedEurAccount = buildEurAccount();
        expectedCard = buildCard();
        expectedRndParam = "69109028193279258387208980";
        expectedSumAmount = new Amount(1252263.54, "PLN");
    }

    @Test
    public void parseDesktopHtml() throws Exception {
        DesktopInfoDto desktopInfoDto = desktopInformationParser.parseDesktopHtml(html);

        assertTrue(desktopInfoDto.getAccounts().size() == 2);
        assertTrue(desktopInfoDto.getAccounts().get(0).equals(expectedPlnAccount));
        assertTrue(desktopInfoDto.getAccounts().get(1).equals(expectedEurAccount));
        assertTrue(desktopInfoDto.getCards().size() == 1);
        assertTrue(desktopInfoDto.getCards().get(0).equals(expectedCard));
        assertTrue(desktopInfoDto.getRndParameter().equals(expectedRndParam));
        assertTrue(desktopInfoDto.getSumAmount().equals(expectedSumAmount));
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