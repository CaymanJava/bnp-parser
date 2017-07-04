package pro.devlib.parser;


import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import pro.devlib.dto.DesktopInfoDto;
import pro.devlib.exception.ParsePageException;
import pro.devlib.model.Account;
import pro.devlib.model.Amount;
import pro.devlib.model.Card;
import pro.devlib.model.ZeroAmount;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class DesktopInformationParser {

  private final static String DESKTOP_PAGE_PARSE_EXCEPTION_MESSAGE = "Couldn't parse desktop page.";

  public DesktopInfoDto parseDesktopHtml(String desktopHtml) {
    try {
      Document desktopDocument = Jsoup.parse(desktopHtml);
      String rndParameter = extractRndParameter(desktopDocument);
      Amount sumAmount = extractSum(desktopDocument);
      List<Account> accounts = parseAccounts(desktopDocument);
      List<Card> cards = parseCards(desktopDocument);
      return DesktopInfoDto
              .builder()
              .rndParameter(rndParameter)
              .sumAmount(sumAmount)
              .accounts(accounts)
              .cards(cards)
              .build();

    } catch (Exception e) {
      throw new ParsePageException(DESKTOP_PAGE_PARSE_EXCEPTION_MESSAGE, e);
    }
  }

  private String extractRndParameter(Document desktopDocument) {
    return desktopDocument.select("div[id=smartInfoContainer] input").attr("value");
  }

  private Amount extractSum(Document desktopDocument) {
    try {
      String headerSelectInfo = desktopDocument.select("div[id=wypRach] span[class=headerSelectInfo]").html();
      if (headerSelectInfo != null) {
        return parseTotalAmount(headerSelectInfo.split("<span")[0]);
      } else {
        return new ZeroAmount();
      }
    } catch (Exception e) {
      return new ZeroAmount();
    }
  }

  private Amount parseTotalAmount(String value) {
    String[] values = value.split("&nbsp;");
    if (values.length > 0) {
      double number = Double.parseDouble(values[0]
              .replaceAll(" ", "")
              .replaceAll(",", ".")
              .trim());
      String currency = values[1].trim();
      return new Amount(number, currency);
    } else {
      return new ZeroAmount();
    }
  }

  private List<Account> parseAccounts(Document desktopDocument) {
    List<Account> accounts = new ArrayList<>();
    Elements htmlAccounts = desktopDocument.select("div[id=tabRachunki] tr[class^=trbg]");
    for (Element htmlAccount : htmlAccounts) {
      try {
        accounts.add(parseAccount(htmlAccount));
      } catch (Exception e) {
        log.warn("This is not an account!");
      }
    }
    return accounts;
  }

  private List<Card> parseCards(Document desktopDocument) {
    List<Card> cards = new ArrayList<>();
    Elements htmlCards = desktopDocument.select("div[id=tabCards] tr[class^=trbg]");
    for (Element htmlCard : htmlCards) {
      try {
        cards.add(parseCard(htmlCard));
      } catch (Exception e) {
        log.warn("This is not a card!");
      }
    }
    return cards;
  }

  private Card parseCard(Element htmlCard) {
    Elements cardInfo = htmlCard.select("td");
    String name = cardInfo.select("span").text();
    String holderName = cardInfo.get(1).text();
    String number = cardInfo.get(2).text();
    return Card
            .builder()
            .name(name)
            .holderName(holderName)
            .number(number)
            .build();
  }

  private Account parseAccount(Element htmlAccount) {
    Elements accountInfo = htmlAccount.select("td");
    String name = accountInfo.select("span").text();
    String number = accountInfo.get(1).text();
    Amount balance = parseAmount(accountInfo.get(2).text());
    return Account
            .builder()
            .name(name)
            .number(number)
            .balance(balance)
            .build();
  }

  Amount parseAmount(String value) {
    try {
      String[] splitAmount = value.split(" ");
      String currency = splitAmount[splitAmount.length - 1].trim();
      double number = Double.parseDouble(value
              .replaceAll(currency, "")
              .replaceAll(" ", "")
              .replaceAll(",", ".")
              .trim());
      return new Amount(number, currency);
    } catch (Exception e) {
      return new ZeroAmount();
    }
  }

}
