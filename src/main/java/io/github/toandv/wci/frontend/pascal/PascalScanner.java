package io.github.toandv.wci.frontend.pascal;

import io.github.toandv.wci.frontend.EofToken;
import io.github.toandv.wci.frontend.Scanner;
import io.github.toandv.wci.frontend.Source;
import io.github.toandv.wci.frontend.Token;

public class PascalScanner extends Scanner {

    public PascalScanner(Source source) {
        super(source);
    }

    @Override
    public Token extractToken() throws Exception {
        Token token;
        char currentChar = currentChar();
        // Construct the next token.
        // determines the The current character token type.
        if (currentChar == Source.EOF) {
            token = new EofToken(source);
        } else {
            token = new Token(source);
        }
        return token;
    }

}
