package pro.devlib.parser;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import pro.devlib.dto.StatementDto;
import pro.devlib.exception.ParsePageException;
import pro.devlib.model.Amount;
import pro.devlib.model.Transaction;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static pro.devlib.util.Constants.FORMATTER;
import static pro.devlib.util.Constants.STATEMENT_PAGE_PARSE_EXCEPTION_MESSAGE;

@Component
@Slf4j
public class TransactionParser extends DesktopInformationParser {

    public StatementDto parseSysDateAndTemplatesIdSize(String html) {
        try {
            Document parseStatement = Jsoup.parse(html);
            int templatesIdSize = parseStatement.select("select[name=p_template_id] option").size();
            String systemDate = parseStatement.select("input[name=p_sys_date]").attr("value");
            return new StatementDto(systemDate, templatesIdSize);
        } catch (Exception e) {
            throw new ParsePageException(STATEMENT_PAGE_PARSE_EXCEPTION_MESSAGE, e);
        }
    }

    public List<Transaction> parseTransactions(String html) {
        List<Transaction> operationList = new ArrayList<>();
        Document parsedHtml = Jsoup.parse(html);
        Elements operationElements = parsedHtml.select("div[class=print-content] div[class=list] tr[class^=trbg]");
        for (Element operationElement : operationElements) {
            try {
                operationList.add(parseOperation(operationElement));
            } catch (Exception e) {
                log.warn("Can't parse transaction.");
            }
        }
        return operationList;
    }

    private Transaction parseOperation(Element operationElement) {
        Elements operationInfo = operationElement.select("td");
        LocalDate date = LocalDate.parse(operationInfo.get(0).text(), FORMATTER);
        String description = operationInfo.get(1).text();
        Amount amount = parseAmount(operationInfo.get(2).text());
        Amount balanceAfterOperation = parseAmount(operationInfo.get(3).text());
        return Transaction
                .builder()
                .date(date)
                .description(description)
                .amount(amount)
                .balanceAfterOperation(balanceAfterOperation)
                .build();
    }
}
