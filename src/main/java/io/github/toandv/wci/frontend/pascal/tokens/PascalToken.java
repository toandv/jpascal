package io.github.toandv.wci.frontend.pascal.tokens;

import io.github.toandv.wci.frontend.Source;
import io.github.toandv.wci.frontend.Token;

public abstract class PascalToken extends Token {

    public PascalToken(Source source) throws Exception {
        super(source);
    }

    protected abstract void extract() throws Exception;

}
