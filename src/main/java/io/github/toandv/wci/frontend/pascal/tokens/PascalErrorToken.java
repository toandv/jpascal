package io.github.toandv.wci.frontend.pascal.tokens;

import io.github.toandv.wci.frontend.Source;
import io.github.toandv.wci.frontend.pascal.PascalErrorCode;
import io.github.toandv.wci.frontend.pascal.PascalTokenType;

public class PascalErrorToken extends PascalToken {

    public PascalErrorToken(Source source, PascalErrorCode errorCode, String tokenText) throws Exception {
        super(source);
        this.type = PascalTokenType.ERROR;
        this.text = tokenText;
        this.value = errorCode;
    }

    @Override
    protected void extract() {

    }

}
