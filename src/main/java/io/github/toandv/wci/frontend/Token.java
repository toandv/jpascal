
package io.github.toandv.wci.frontend;

import io.github.toandv.wci.utils.JsonUtils;

/**
 * Tokens are language elements such as reserved keywords, identifiers, or special symbols The framework class that
 * represents a token returned from the Scanner
 * 
 * @author toandv
 *
 */
public class Token {

    protected TokenType type; // language-specific token type

    protected String text; // token text

    protected Object value; // token value

    protected Source source; // source

    private int lineNum; // line number of the token

    private int position; // position of the first token character

    public Token(Source source) throws Exception {
        this.source = source;
        this.lineNum = source.getLineNum();
        this.position = source.getCurrentPos();

        extract();
    }

    /**
     * Default method that extracts only one-character tokens from the source Language-specific subclasses should
     * override this method to construct tokens
     * 
     * After extracting the token, the current source line position will be one beyond the last token character
     * 
     * @throws Exception
     */
    protected void extract() throws Exception {
        text = Character.toString(currentChar());
        value = null;

        nextChar(); // consume the current char
    }

    protected char currentChar() throws Exception {
        return source.currentChar();
    }

    protected char nextChar() throws Exception {
        return source.nextChar();
    }

    protected char peekChar() throws Exception {
        return source.peekChar();
    }

    public int getLineNum() {
        return lineNum;
    }

    public int getPosition() {
        return position;
    }

    public String getText() {
        return text;
    }

    public TokenType getType() {
        return type;
    }

    public Object getValue() {
        return value;
    }

    public Source getSource() {
        return source;
    }

    @Override
    public String toString() {
        return JsonUtils.toJson(this);
    }
}
