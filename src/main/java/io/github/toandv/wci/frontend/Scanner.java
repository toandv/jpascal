package io.github.toandv.wci.frontend;

/**
 * The Scanner reads characters from the Source program and constructs Tokens.
 * Scanner is a language-independent framework class.
 * 
 * @author toandv
 * 
 *
 */
public abstract class Scanner {

    protected Source source;

    protected Token currentToken;

    public Token currentToken() {
        return currentToken;
    }

    public Scanner(Source source) {
        super();
        this.source = source;
    }

    public Token nextToken() throws Exception {
        currentToken = extractToken();
        return currentToken;
    }

    public abstract Token extractToken() throws Exception;

    public char currentChar() throws Exception {
        return source.currentChar();
    }

    public char nextChar() throws Exception {
        return source.nextChar();
    }

}
