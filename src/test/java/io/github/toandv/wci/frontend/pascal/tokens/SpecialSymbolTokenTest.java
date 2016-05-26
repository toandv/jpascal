package io.github.toandv.wci.frontend.pascal.tokens;

import io.github.toandv.wci.frontend.Token;
import io.github.toandv.wci.frontend.pascal.PascalTokenType;
import org.junit.Assert;

public class SpecialSymbolTokenTest extends BaseTokenTest {

    @Override
    public void extractToken() throws Exception {
        // + - * / : := > >= ...
        init("/tokens/special_symbol_tokens.pas");
        Token token0 = nextToken();
        Token token1 = nextToken();
        Token token2 = nextToken();
        Token token3 = nextToken();
        Token token4 = nextToken();
        Token token5 = nextToken();
        Token token6 = nextToken();
        Token token7 = nextToken();
        Token token8 = nextToken();
        Token token9 = nextToken();
        Token token10 = nextToken();

        Assert.assertEquals(PascalTokenType.IDENTIFIER, token0.getType());
        Assert.assertEquals(PascalTokenType.PLUS, token1.getType());
        Assert.assertEquals(PascalTokenType.MINUS, token2.getType());
        Assert.assertEquals(PascalTokenType.STAR, token3.getType());
        Assert.assertEquals(PascalTokenType.SLASH, token4.getType());
        Assert.assertEquals(PascalTokenType.COLON, token5.getType());
        Assert.assertEquals(PascalTokenType.COLON_EQUALS, token6.getType());
        Assert.assertEquals(PascalTokenType.GREATER_THAN, token7.getType());
        Assert.assertEquals(PascalTokenType.GREATER_EQUALS, token8.getType());
        Assert.assertEquals(PascalTokenType.DOT_DOT, token9.getType());
        Assert.assertEquals(PascalTokenType.DOT, token10.getType());
    }

}
