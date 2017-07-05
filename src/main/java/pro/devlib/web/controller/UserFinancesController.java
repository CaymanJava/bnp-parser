package pro.devlib.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pro.devlib.paribas.dto.UserCredentialsDto;
import pro.devlib.paribas.model.UserFinances;
import pro.devlib.paribas.service.BnpParibasService;

@RestController
@Slf4j
public class UserFinancesController {

  @RequestMapping(value = "finances", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
  public UserFinances getUserFinances(@RequestBody UserCredentialsDto userCredentials) {
    log.info("Receive request with user credentials.");
    BnpParibasService bnpParibasService = new BnpParibasService(userCredentials.getLogin(), userCredentials.getPassword());
    return bnpParibasService.execute();
  }
  
}
