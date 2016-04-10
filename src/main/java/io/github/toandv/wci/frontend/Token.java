package io.github.toandv.wci.frontend;

/**
 * Tokens are language elements such as reserved keywords, identifiers, or
 * special symbols
 * 
 * @author toandv
 *
 */
public class Token {

    protected Source source;

    protected TokenType type;

    protected String text;

    protected Object value;

    private int lineNum;

    private int position;

    protected void exract() {
    }

    protected char currentChar() {
        return 0;
    }

    protected char nextChar() {
        return 0;
    }

    protected char peekChar() {
        return 0;
    }
}
