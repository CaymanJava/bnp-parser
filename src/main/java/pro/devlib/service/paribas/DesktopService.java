package pro.devlib.service.paribas;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import pro.devlib.dto.DesktopInfoDto;
import pro.devlib.parser.DesktopInformationParser;
import pro.devlib.http.RequestExecutor;

import static pro.devlib.util.Constants.EMPTY;

@Service
@Scope("session")
@Slf4j
public class DesktopService {
    private final String desktopUrl;
    private final RequestExecutor requestExecutor;
    private final DesktopInformationParser desktopInformationParser;

    @Autowired
    public DesktopService(@Value("${desktop.url}") String desktopUrl,
                          RequestExecutor requestExecutor, DesktopInformationParser desktopInformationParser) {
        this.desktopUrl = desktopUrl;
        this.requestExecutor = requestExecutor;
        this.desktopInformationParser = desktopInformationParser;
    }

    public DesktopInfoDto getDesktopInformation() {
        String desktopHtml = requestExecutor.executeGetRequest(desktopUrl, EMPTY);
        return desktopInformationParser.parseDesktopHtml(desktopHtml);
    }
}
