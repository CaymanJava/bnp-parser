package pro.devlib.parser;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pro.devlib.config.SpringApplicationConfig;
import pro.devlib.exception.ParsePageException;

import java.nio.file.Files;
import java.nio.file.Paths;

import static junit.framework.TestCase.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SpringApplicationConfig.class})
public class PasswordPageParserTest {

  @Autowired
  private PasswordPageParser passwordPageParser;

  @Test
  public void extractSamlUrlFromPasswordPage() throws Exception {
    String html = new String(Files.readAllBytes(Paths.get("src/test/resources/passwordPage.html")));
    String expectedSamlUrl = "https://planet.bgzbnpparibas.pl/hades/RedirectSaml?SAMLart=AAQAAC4H9dHvCEfgFKKhkn6P2cSY7OyEAAACAQAjg7DGwvlQTkXP9dmbwQE%3D";
    String samlUrl = passwordPageParser.extractSamlUrlFromPasswordPage(html);
    assertEquals(expectedSamlUrl, samlUrl);
  }

  @Test(expected = ParsePageException.class)
  public void extractSamlUrlExceptionExpect() throws Exception {
    passwordPageParser.extractSamlUrlFromPasswordPage("");
  }
}
