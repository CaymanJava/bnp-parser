package pro.devlib.service.paribas;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import pro.devlib.dto.LoginResponseDto;
import pro.devlib.parser.LoginPageParser;
import pro.devlib.http.RequestExecutor;

import java.util.Map;

@Service
@Scope("session")
@Slf4j
public class LoginService {

    private final String loginUrl;
    private final String loginReferer;
    private final String ssoUrl;
    private final RequestExecutor requestExecutor;
    private final LoginPageParser loginPageParser;

    @Autowired
    public LoginService(@Value("${login.url}") String loginUrl,
                        @Value("${login.referer}") String loginReferer,
                        @Value("${sso.url}") String ssoUrl,
                        RequestExecutor requestExecutor,
                        LoginPageParser loginPageParser) {
        this.loginUrl = loginUrl;
        this.loginReferer = loginReferer;
        this.ssoUrl = ssoUrl;
        this.requestExecutor = requestExecutor;
        this.loginPageParser = loginPageParser;
    }

    public LoginResponseDto login(Map<String, String> parameters, String login) {
        log.info("Try to login on website with login: " + login);
        parameters.put("p_alias", login);
        requestExecutor.executePostRequest(loginUrl, loginReferer, parameters);
        String htmlFromSsoRequest = requestExecutor.executeGetRequest(ssoUrl, loginUrl);
        return loginPageParser.parseLoginPage(htmlFromSsoRequest);
    }
}
