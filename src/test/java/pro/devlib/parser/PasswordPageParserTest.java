package pro.devlib.parser;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pro.devlib.config.SpringApplicationConfig;
import pro.devlib.exception.ParsePageException;

import java.nio.file.Files;
import java.nio.file.Paths;

import static junit.framework.TestCase.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SpringApplicationConfig.class})
public class PasswordPageParserTest {

    @Autowired
    private PasswordPageParser passwordPageParser;
    private String html;
    private String expectedSamlUrl;

    @Before
    public void init() throws Exception {
        html = new String(Files.readAllBytes(Paths.get("src/test/resources/passwordPage.html")));
        expectedSamlUrl = "https://planet.bgzbnpparibas.pl/hades/RedirectSaml?SAMLart=AAQAAC4H9dHvCEfgFKKhkn6P2cSY7OyEAAACAQAjg7DGwvlQTkXP9dmbwQE%3D";
    }

    @Test
    public void extractSamlUrlFromPasswordPage() throws Exception {
        String samlUrl = passwordPageParser.extractSamlUrlFromPasswordPage(html);
        assertTrue(samlUrl.equals(expectedSamlUrl));
    }

    @Test(expected = ParsePageException.class)
    public void extractSamlUrlExceptionExpect() throws Exception {
        passwordPageParser.extractSamlUrlFromPasswordPage("");
    }

}