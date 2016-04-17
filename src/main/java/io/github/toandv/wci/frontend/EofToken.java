package io.github.toandv.wci.frontend;

public class EofToken extends Token {

    public EofToken(Source source, TokenType type) throws Exception {
        super(source);
        this.type = type;
    }

    /**
     * Does nothing
     */
    @Override
    protected void extract() throws Exception {
    }

}
