package io.github.toandv.wci.frontend.pascal.tokens;

import io.github.toandv.wci.frontend.Source;
import io.github.toandv.wci.frontend.Token;

public abstract class PascalToken extends Token {

    public static final char EOL_CHAR = '\n';
    public static final char SINGLE_QUOTE_CHAR = '\'';
    public static final char EOF_CHAR = (char) 0;
    public static final char OPEN_PAREN_CHAR = '{';
    public static final char CLOSING_PAREN_CHAR = '}';
    public static final char DOT_CHAR = '.';
    public static final char EMPTY_CHAR = ' ';
    public static final char PLUS_CHAR = '+';
    public static final char MINUS_CHAR = '-';
    public static final char EXPONENT_LOWER_CHAR = 'e';
    public static final char EXPONENT_UPPER_CHAR = 'E';

    public static final int BASE_10 = 10;
    public static final int MAX_EXPONENT = 308;

    public static final String EMPTY_STRING = "";
    public static final String PLUS_SIGN = "+";
    public static final String MINUS_SIGN = "-";

    public PascalToken(Source source) throws Exception {
        super(source);
    }

    protected abstract void extract() throws Exception;

}
