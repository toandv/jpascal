package io.github.toandv.wci.frontend.pascal.tokens;

import io.github.toandv.wci.frontend.Source;
import io.github.toandv.wci.frontend.pascal.PascalTokenType;
import io.github.toandv.wci.utils.StringUtils;

public class PascalSpecialSymbolToken extends PascalToken {

    public PascalSpecialSymbolToken(Source source) throws Exception {
        super(source);
    }

    @Override
    protected void extract() throws Exception {
        char currentChar = currentChar();
        char nextChar = nextChar();

        PascalTokenType type = PascalTokenType.getSpecialSymbol(StringUtils.valueOf(currentChar, nextChar));
        if (type != null) {
            // consume next char
            nextChar();
        } else {
            type = PascalTokenType.getSpecialSymbol(String.valueOf(currentChar));
        }
        this.type = type;
        this.text = type.getText();
    }

}
