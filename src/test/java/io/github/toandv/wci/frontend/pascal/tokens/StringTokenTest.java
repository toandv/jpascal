package io.github.toandv.wci.frontend.pascal.tokens;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import io.github.toandv.wci.frontend.Token;
import io.github.toandv.wci.frontend.pascal.PascalTokenType;

public class StringTokenTest extends TokenBaseTest {

    @Override
    @Test
    public void extractToken() throws Exception {
        init("/tokens/string_tokens.pas");
        Token token = parser.nextToken();
        assertEquals(PascalTokenType.STRING, token.getType());
        assertEquals("' '''' my name''s toandv. I''m a programmer. '''' '", token.getText());
        assertEquals(" '' my name's toandv. I'm a programmer. '' ", token.getValue());
        Token token1 = parser.nextToken();
        assertEquals(PascalTokenType.ERROR, token1.getType());
        assertEquals("' ", token1.getText());
    }

    @Test
    public void extractInvalidTokens() throws Exception {
        init("/tokens/string_invalid_tokens.pas");
        Token token1 = parser.nextToken();
        assertEquals(PascalTokenType.ERROR, token1.getType());
        assertEquals("''''' ", token1.getText());
    }
}
