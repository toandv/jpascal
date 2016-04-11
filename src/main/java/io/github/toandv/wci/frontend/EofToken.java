package io.github.toandv.wci.frontend;

public class EofToken extends Token {

    public EofToken(Source source) throws Exception {
        super(source);
    }

    /**
     * Does nothing
     */
    @Override
    protected void extract() throws Exception {
    }

}
