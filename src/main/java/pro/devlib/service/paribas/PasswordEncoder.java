package pro.devlib.service.paribas;

import org.springframework.stereotype.Component;

import java.util.List;

import static org.apache.commons.codec.digest.DigestUtils.*;

@Component
class PasswordEncoder {

  private final static String HEX_CHARS = "0123456789abcdef";

  String encodePassword(String login, String password, List<String> passwordSymbols, String loginMask) {
    String passwordMask = getPasswordMask(passwordSymbols, password);
    int[] bytesOfLoginMask = hexString2ArrayOfBytes(loginMask);
    return createPassMaskedBis(login, passwordMask, bytesOfLoginMask);
  }

  private String getPasswordMask(List<String> passwordSymbols, String password) {
    StringBuilder result = new StringBuilder();
    for (int i = 0; i < passwordSymbols.size(); i++) {
      if (passwordSymbols.get(i).equals("*")) {
        result.append(passwordSymbols.get(i));
      } else {
        result.append(password.charAt(i));
      }
    }
    return result.toString();
  }

  private int[] hexString2ArrayOfBytes(String loginMask) {
    int size = loginMask.length() / 2;
    int[] result = new int[size];
    for (int i = 0; i < loginMask.length(); i += 2) {
      result[i / 2] = hex2Byte(loginMask.substring(i, i + 2));
    }
    return result;
  }

  private int hex2Byte(String hex) {
    hex = hex.toLowerCase();
    return (HEX_CHARS.indexOf(hex.charAt(0)) << 4) | HEX_CHARS.indexOf(hex.charAt(1));
  }

  private String createPassMaskedBis(String login, String passwordMask, int[] bytesOfLoginMask) {
    int[] passLoginMask = textToUnicodeValues(login);
    int[] passMasked = createPassMasked(passwordMask, bytesOfLoginMask);
    return sha1Hex(concatArrays(passLoginMask, passMasked));
  }

  private int[] createPassMasked(String passwordMask, int[] bytesOfLoginMask) {
    int[] passArray = textToUnicodeValues(passwordMask);
    return mergeArrays(passArray, bytesOfLoginMask);
  }

  private int[] mergeArrays(int[] first, int[] second) {
    int size = first.length <= second.length ? first.length : second.length;
    int[] result = new int[size];

    for (int i = 0; i < size; i++) {
      if (second[i] == 255) {
        result[i] = first[i] & second[i];
      } else {
        result[i] = second[i];
      }
    }
    return result;
  }

  private int[] textToUnicodeValues(String text) {
    int[] result = new int[text.length()];
    for (int i = 0; i < text.length(); i++) {
      result[i] = Character.codePointAt(text, i);
    }
    return result;
  }

  private byte[] concatArrays(int[] first, int[] second) {
    int size = first.length + second.length;
    byte[] result = new byte[size];
    for (int i = 0; i < size; ) {
      for (int aFirst : first) {
        result[i] = (byte) aFirst;
        i++;
      }
      for (int aSecond : second) {
        result[i] = (byte) aSecond;
        i++;
      }
    }
    return result;
  }

}
