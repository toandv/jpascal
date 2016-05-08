package io.github.toandv.wci.frontend.pascal.tokens;

import io.github.toandv.wci.frontend.Token;
import io.github.toandv.wci.frontend.pascal.PascalTokenType;

import static org.junit.Assert.assertEquals;

public class WordTokenTest extends TokenBaseTest {

    @Override
    public void extractToken() throws Exception {
        init("/tokens/word_tokens.pas");
        Token nextToken = parser.nextToken();
        Token nextToken1 = parser.nextToken();
        Token nextToken2 = parser.nextToken();
        assertEquals("begin", nextToken.getText());
        assertEquals(PascalTokenType.BEGIN, nextToken.getType());
        assertEquals(PascalTokenType.END, nextToken1.getType());
        assertEquals("end", nextToken1.getText());
        assertEquals(PascalTokenType.END_OF_FILE, nextToken2.getType());
    }

}
