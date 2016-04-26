package io.github.toandv.wci.frontend.pascal.tokens;

import org.junit.Assert;

import io.github.toandv.wci.frontend.Token;
import io.github.toandv.wci.frontend.pascal.PascalTokenType;

public class NumberTokenTest extends TokenBaseTest {

    @Override
    public void extractToken() throws Exception {
        init("/tokens/number_tokens.pas");
        Token token = nextToken();
        Token token1 = nextToken();
        Token token2 = nextToken();
        Token token3 = nextToken();
        // 1.2E+5 
        // 123E-6
        // 10E308
        Assert.assertEquals(PascalTokenType.REAL, token.getType());
        Assert.assertEquals(1.2e+5, token.getValue());
        Assert.assertEquals("1.2E+5", token.getText());

        Assert.assertEquals(PascalTokenType.REAL, token1.getType());
        Assert.assertEquals(123E-6, (double) token1.getValue(), 0.001);
        Assert.assertEquals("123E-6", token1.getText());

        Assert.assertEquals(PascalTokenType.ERROR, token2.getType());
        Assert.assertEquals(0.0, token2.getValue());
        Assert.assertEquals("10E308", token2.getText());

        Assert.assertEquals(PascalTokenType.END_OF_FILE, token3.getType());
        Assert.assertNull(token3.getValue());
        Assert.assertNull(token3.getText());
    }

}
