package pro.devlib.util;

import java.util.List;

import static pro.devlib.util.Constants.*;

public class PasswordEncoder {

    public static String encodePassword(String login, String password, List<String> passwordSymbols, String loginMask) {
        String passwordMask = getPasswordMask(passwordSymbols, password);
        int[] bytesOfLoginMask = hexString2ArrayOfBytes(loginMask);
        return createPassMaskedBis(login, passwordMask, bytesOfLoginMask);
    }

    private static String getPasswordMask(List<String> passwordSymbols, String password) {
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

    private static int[] hexString2ArrayOfBytes(String loginMask) {
        int size = loginMask.length() / 2;
        int[] result = new int[size];
        for (int i = 0; i < loginMask.length(); i+=2) {
            result[i / 2] = hex2Byte(loginMask.substring(i, i + 2));
        }
        return result;
    }

    private static int hex2Byte(String hex) {
        hex = hex.toLowerCase();
        return (HEX_CHARS.indexOf(hex.charAt(0)) << 4) | HEX_CHARS.indexOf(hex.charAt(1));
    }

    private static String createPassMaskedBis(String login, String passwordMask, int[] bytesOfLoginMask) {
        int[] passLoginMask = textToUnicodeValues(login);
        int[] passMasked = createPassMasked(passwordMask, bytesOfLoginMask);
        return sha1(concatArrays(passLoginMask, passMasked), "H");
    }

    private static String sha1(int[] array, String codeSymbol) {
        int[] m = str2blksSHA1AOB(array);
        int[] n = new int[80];
        int L = 1732584193;
        int k = -271733879;
        int j = -1732584194;
        int i = 271733878;
        int h = -1009589776;
        for (int e = 0; e < m.length; e += 16) {
            int g = L;
            int f = k;
            int d = j;
            int c = i;
            int a = h;
            for (int b = 0; b < 80; b++) {
                if (b < 16) {
                    n[b] = m[e + b];
                } else {
                    n[b] = rol(n[b - 3] ^ n[b - 8] ^ n[b - 14] ^ n[b - 16], 1);
                }
                int o = safeAdd(safeAdd(rol(L, 5), ft(b, k, j, i)), safeAdd(safeAdd(h, n[b]), kt(b)));
                h = i;
                i = j;
                j = rol(k, 30);
                k = L;
                L = o;
            }
            L = safeAdd(L, g);
            k = safeAdd(k, f);
            j = safeAdd(j, d);
            i = safeAdd(i, c);
            h = safeAdd(h, a);
        }
        if (codeSymbol.equals("H")) {
            return hex(L) + hex(k) + hex(j) + hex(i) + hex(h);
        } else {
            int[] resArr = new int[20];
            resArr[0] = (L >> 24) & 255;
            resArr[1] = (L >> 16) & 255;
            resArr[2] = (L >> 8) & 255;
            resArr[3] = L & 255;
            resArr[4] = (k >> 24) & 255;
            resArr[5] = (k >> 16) & 255;
            resArr[6] = (k >> 8) & 255;
            resArr[7] = k & 255;
            resArr[8] = (j >> 24) & 255;
            resArr[9] = (j >> 16) & 255;
            resArr[10] = (j >> 8) & 255;
            resArr[11] = j & 255;
            resArr[12] = (i >> 24) & 255;
            resArr[13] = (i >> 16) & 255;
            resArr[14] = (i >> 8) & 255;
            resArr[15] = i & 255;
            resArr[16] = (h >> 24) & 255;
            resArr[17] = (h >> 16) & 255;
            resArr[18] = (h >> 8) & 255;
            resArr[19] = h & 255;
            return arrayToString(resArr);
        }
    }

    private static int rol(int a, int b) {
        return (a << b) | (a >>> (32 - b));
    }

    private static int kt(int a) {
        return (a < 20) ? 1518500249 : (a < 40) ? 1859775393 : (a < 60) ? -1894007588 : -899497514;
    }

    private static int safeAdd(int a, int d) {
        int c = (a & 65535) + (d & 65535);
        int b = (a >> 16) + (d >> 16) + (c >> 16);
        return (b << 16) | (c & 65535);
    }

    private static int ft(int b, int a, int d, int c) {
        if (b < 20) {
            return (a & d) | ((~a) & c);
        }
        if (b < 40) {
            return a ^ d ^ c;
        }
        if (b < 60) {
            return (a & d) | (a & c) | (d & c);
        }
        return a ^ d ^ c;
    }

    private static String hex(int b) {
        String c = EMPTY;
        for (int i = 7; i >= 0; i--) {
            c += HEX_CHARS.charAt((b >> (i * 4)) & 15);
        }
        return c;
    }

    private static String arrayToString(int[] array) {
        StringBuilder result = new StringBuilder();
        for (int i : array) {
            result.append(i);
        }
        return result.toString();
    }

    private static int[] str2blksSHA1AOB(int[] array) {
        int size = (((array.length + 8) >> 6) + 1) * 16;
        int[] result = new int[size];
        for (int i = 0; i < array.length; i++) {
            result[i >> 2] |= array[i] << (24 - (i % 4) * 8);
        }
        result[array.length >> 2] |= 128 << (24 - (array.length % 4) * 8);
        result[size - 1] = array.length * 8;
        return result;
    }

    private static int[] createPassMasked(String passwordMask, int[] bytesOfLoginMask) {
        int[] passArray = textToUnicodeValues(passwordMask);
        return mergeArrays(passArray, bytesOfLoginMask);
    }

    private static int[] mergeArrays(int[] first, int[] second) {
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

    private static int[] textToUnicodeValues(String text) {
        int[] result = new int[text.length()];
        for (int i = 0; i < text.length(); i++) {
            result[i] = Character.codePointAt(text, i);
        }
        return result;
    }

    private static int[] concatArrays(int[] first, int[] second) {
        int size = first.length + second.length;
        int[] result = new int[size];
        for (int i = 0; i < size;) {
            for (int aFirst : first) {
                result[i] = aFirst;
                i++;
            }
            for (int aSecond : second) {
                result[i] = aSecond;
                i++;
            }
        }
        return result;
    }
}
