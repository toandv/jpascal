package io.github.toandv.wci.frontend.pascal.tokens;

import io.github.toandv.wci.frontend.Token;

public class NumberTokenTest extends TokenBaseTest {

    @Override
    public void extractToken() throws Exception {
        init("/tokens/number_tokens.pas");
        Token token = nextToken();
    }

}
