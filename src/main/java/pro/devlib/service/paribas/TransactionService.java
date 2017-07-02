package pro.devlib.service.paribas;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import pro.devlib.dto.DesktopInfoDto;
import pro.devlib.dto.StatementDto;
import pro.devlib.http.RequestExecutor;
import pro.devlib.model.Account;
import pro.devlib.model.Transaction;
import pro.devlib.parser.TransactionParser;

import java.util.*;

import static pro.devlib.util.Constants.*;

@Service
@Scope("session")
@Slf4j
public class TransactionService {
    private final String desktopUrl;
    private final String desktopReferer;
    private final String statementUrlWithParams;
    private final String statementUrl;
    private final RequestExecutor requestExecutor;
    private final TransactionParser transactionParser;

    @Autowired
    public TransactionService(@Value("${desktop.url}") String desktopUrl,
                              @Value("${desktop.referer}") String desktopReferer,
                              @Value("${statement.url.with.params}") String statementUrlWithParams,
                              @Value("${statement.url}") String statementUrl,
                              RequestExecutor requestExecutor, TransactionParser transactionParser) {
        this.desktopUrl = desktopUrl;
        this.desktopReferer = desktopReferer;
        this.statementUrlWithParams = statementUrlWithParams;
        this.statementUrl = statementUrl;
        this.requestExecutor = requestExecutor;
        this.transactionParser = transactionParser;
    }

    public void setTransactionsToAccounts(DesktopInfoDto desktopInformation) {
        List<Account> accounts = desktopInformation.getAccounts();
        for (int accountNumber = 0; accountNumber < accounts.size(); accountNumber++) {
            List<Transaction> accountTransactions = getAccountTransactions(accountNumber, desktopInformation.getRndParameter());
            accounts.get(accountNumber).setTransactions(accountTransactions);
        }
    }

    private List<Transaction> getAccountTransactions(int accountNumber, String rndParameter) {
        Map<String, String> paramsForDesktopRequest = createParamsForDesktopRequest(accountNumber, rndParameter);
        requestExecutor.executePostRequest(desktopUrl, desktopReferer, paramsForDesktopRequest);
        String statementWithParamsHtml = requestExecutor.executeGetRequest(statementUrlWithParams, desktopUrl);
        StatementDto statementDto = transactionParser.parseSysDateAndTemplatesIdSize(statementWithParamsHtml);

        List<Transaction> transactions = new ArrayList<>();
        for (int templateId = 0; templateId < statementDto.getTemplatesIdSize(); templateId++) {
            transactions.addAll(getTransactionsFromTemplate(statementDto, templateId));
        }
        return transactions;
    }

    private List<Transaction> getTransactionsFromTemplate(StatementDto statementDto, int templateId) {
        try {
            Map<String, String> paramsForStatementRequest = createParamsForStatementRequest(statementDto.getSystemDate(), templateId);
            String statementHtml = requestExecutor.executePostRequest(statementUrl, statementUrl, paramsForStatementRequest);
            return transactionParser.parseTransactions(statementHtml);
        } catch (Exception e) {
            System.out.println("Can't execute request to statement");
            return Collections.emptyList();
        }
    }

    private Map<String, String> createParamsForDesktopRequest(int accountNumber, String rndParameter) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put(RND, rndParameter);
        parameters.put(TASK, ACC_DETAILS + accountNumber);
        parameters.put(WHICH_ACCOUNTS_LIST, ACCOUNTS);
        return parameters;
    }

    private Map<String, String> createParamsForStatementRequest(String systemDate, int templateId) {
        String actualTemplateId = String.valueOf(templateId <= 0 ? 0 : templateId - 1);
        Map<String, String> parameters = new HashMap<>();
        parameters.put(TASK, EXECUTE);
        parameters.put(ASCENDING, TRUE);
        parameters.put(METHOD_NAME, EMPTY);
        parameters.put(CURRENT_POSITION, ZERO);
        parameters.put(P_ACTUAL_TEMPLATE_ID, actualTemplateId);
        parameters.put(P_SYS_DATE, systemDate);
        parameters.put(P_TEMPLATE_ID, String.valueOf(templateId));
        parameters.put(M_HIDE_OVERNIGHT, FALSE);
        parameters.put(SIZE_PER_WINDOWS, THOUSAND);
        return parameters;
    }
}
