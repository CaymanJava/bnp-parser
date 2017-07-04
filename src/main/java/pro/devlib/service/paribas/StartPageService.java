package pro.devlib.service.paribas;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import pro.devlib.parser.StartPageParser;
import pro.devlib.http.RequestExecutor;

import java.util.Map;

@Service
@Scope("session")
@Slf4j
public class StartPageService {

  private final String startPageUrl;
  private final String startPageReferer;
  private final RequestExecutor requestExecutor;
  private final StartPageParser startPageParser;

  public StartPageService(@Value("${start.page.url}") String startPageUrl,
                          @Value("${start.page.referer}") String startPageReferer,
                          RequestExecutor requestExecutor,
                          StartPageParser startPageParser) {
    this.startPageUrl = startPageUrl;
    this.startPageReferer = startPageReferer;
    this.requestExecutor = requestExecutor;
    this.startPageParser = startPageParser;
  }

  public Map<String, String> getParametersFromStartPage() {
    log.info("Get parameters from start page.");
    String htmlFromStartPage = requestExecutor.executeGetRequest(startPageUrl, startPageReferer);
    return startPageParser.parseStartPage(htmlFromStartPage);
  }

}
