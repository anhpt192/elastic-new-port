package nxt.http;

import nxt.Account;
import nxt.crypto.Crypto;
import nxt.util.Convert;
import org.json.simple.JSONObject;
import org.json.simple.JSONStreamAware;

import javax.servlet.http.HttpServletRequest;

import static nxt.http.JSONResponses.MISSING_SECRET_PHRASE;

public final class GetAccountId extends HttpRequestHandler {

    static final GetAccountId instance = new GetAccountId();

    private GetAccountId() {}

    @Override
    JSONStreamAware processRequest(HttpServletRequest req) {

        String secretPhrase = req.getParameter("secretPhrase");
        if (secretPhrase == null) {
            return MISSING_SECRET_PHRASE;
        }

        byte[] publicKey = Crypto.getPublicKey(secretPhrase);

        JSONObject response = new JSONObject();
        response.put("accountId", Convert.convert(Account.getId(publicKey)));

        return response;
    }

}
