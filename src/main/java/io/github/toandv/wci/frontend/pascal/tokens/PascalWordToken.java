package io.github.toandv.wci.frontend.pascal.tokens;

import io.github.toandv.wci.frontend.Source;
import io.github.toandv.wci.frontend.pascal.PascalTokenType;

public class PascalWordToken extends PascalToken {

    public PascalWordToken(Source source) throws Exception {
        super(source);
    }

    @Override
    protected void extract() throws Exception {
        char currentChar = currentChar();
        StringBuilder buffer = new StringBuilder();

        // The Scanner's already determined the first char is a letter.
        while (Character.isLetterOrDigit(currentChar)) {
            buffer.append(currentChar);
            currentChar = nextChar();
        }
        this.text = buffer.toString();
        this.type = PascalTokenType.getType(text);
    }

}
