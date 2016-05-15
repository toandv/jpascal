package io.github.toandv.wci.frontend.pascal;

import io.github.toandv.wci.frontend.EofToken;
import io.github.toandv.wci.frontend.Scanner;
import io.github.toandv.wci.frontend.Source;
import io.github.toandv.wci.frontend.Token;
import io.github.toandv.wci.frontend.pascal.tokens.*;

import static io.github.toandv.wci.frontend.pascal.PascalErrorCode.INVALID_CHARACTER;
import static io.github.toandv.wci.frontend.pascal.PascalTokenType.END_OF_FILE;

public class PascalScanner extends Scanner {

    public PascalScanner(Source source) {
        super(source);
    }

    @Override
    public Token extractToken() throws Exception {

        // Skip white spaces and comments.
        skipWhiteSpacesAndComments();

        char currentChar = currentChar();

        if (currentChar == PascalToken.EOF_CHAR) {
            return new EofToken(source, END_OF_FILE);
        }

        if (Character.isLetter(currentChar)) {
            return new PascalWordToken(source);
        }

        if (Character.isDigit(currentChar)) {
            return new PascalNumberToken(source);
        }

        if (currentChar == PascalToken.SINGLE_QUOTE_CHAR) {
            return new PascalStringToken(source);
        }

        if (PascalTokenType.isSpecialSymbol(currentChar)) {
            return new PascalSpecialSymbolToken(source);
        }

        // Error token, consume the current char.
        nextChar();
        return new PascalErrorToken(source, INVALID_CHARACTER, Character.toString(currentChar));
    }

    /**
     * ' ' ' ' ' {This is a comment.} ' ' ' ' ' ' begin {This is a comment that
     * spans several source lines.}
     * <p>
     * Two{comments in}{a row} here
     * <p>
     * {Word tokens} Hello world begin BEGIN Begin BeGiN begins
     * <p>
     * {String tokens}
     *
     * @throws Exception
     */
    private void skipWhiteSpacesAndComments() throws Exception {

        char currentChar = currentChar();
        while (Character.isWhitespace(currentChar) || currentChar == PascalToken.OPEN_PAREN_CHAR) {
            // Start a comment ?
            if (currentChar == PascalToken.OPEN_PAREN_CHAR) {
                currentChar = nextChar();
                while (currentChar != PascalToken.CLOSING_PAREN_CHAR && currentChar != PascalToken.EOF_CHAR) {
                    currentChar = nextChar(); // Consume all comments chars.
                }

                // Consume the comment closing char.
                if (currentChar == PascalToken.CLOSING_PAREN_CHAR) {
                    currentChar = nextChar();
                }

            } else {
                // Consume spaces.
                currentChar = nextChar();
            }
        }
    }

}
