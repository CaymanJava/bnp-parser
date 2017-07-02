package pro.devlib.http;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolException;
import org.apache.http.impl.client.DefaultRedirectStrategy;
import org.apache.http.protocol.HttpContext;

@Slf4j
public class CustomRedirectStrategy extends DefaultRedirectStrategy {
    public boolean isRedirected(HttpRequest request, HttpResponse response, HttpContext context) {
        boolean isRedirect = false;
        try {
            isRedirect = super.isRedirected(request, response, context);
        } catch (ProtocolException e) {
            log.warn("Exception during checking redirection! ", e);
        }
        if (!isRedirect) {
            int responseCode = response.getStatusLine().getStatusCode();
            if (responseCode == 301 || responseCode == 302) {
                return true;
            }
        }
        return isRedirect;
    }
}
