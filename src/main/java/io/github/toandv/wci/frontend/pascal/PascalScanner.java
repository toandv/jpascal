package io.github.toandv.wci.frontend.pascal;

import io.github.toandv.wci.frontend.EofToken;
import io.github.toandv.wci.frontend.Scanner;
import io.github.toandv.wci.frontend.Source;
import io.github.toandv.wci.frontend.Token;
import io.github.toandv.wci.frontend.pascal.tokens.PascalErrorToken;
import io.github.toandv.wci.frontend.pascal.tokens.PascalNumberToken;
import io.github.toandv.wci.frontend.pascal.tokens.PascalSpecialSymbolToken;
import io.github.toandv.wci.frontend.pascal.tokens.PascalStringToken;
import io.github.toandv.wci.frontend.pascal.tokens.PascalWordToken;

public class PascalScanner extends Scanner {

    public PascalScanner(Source source) {
        super(source);
    }

    @Override
    public Token extractToken() throws Exception {

        // skip white spaces and comments
        skipWhiteSpacesAndComments();

        char currentChar = currentChar();

        if (currentChar == Source.EOF) {
            return new EofToken(source, PascalTokenType.END_OF_FILE);
        }

        if (Character.isLetter(currentChar)) {
            return new PascalWordToken(source);
        }

        if (Character.isDigit(currentChar)) {
            return new PascalNumberToken(source);
        }

        if (currentChar == '\'') {
            return new PascalStringToken(source);
        }

        if (PascalTokenType.isSpecialSymbol(currentChar)) {
            return new PascalSpecialSymbolToken(source);
        }

        // error token, consume the current char
        nextChar();
        return new PascalErrorToken(source, PascalErrorCode.INVALID_CHARACTER, Character.toString(currentChar));
    }

    /**
     * ' ' ' ' ' {This is a comment.} ' ' ' ' ' ' begin {This is a comment that
     * spans several source lines.}
     * 
     * Two{comments in}{a row} here
     * 
     * {Word tokens} Hello world begin BEGIN Begin BeGiN begins
     * 
     * {String tokens}
     * 
     * @throws Exception
     */
    private void skipWhiteSpacesAndComments() throws Exception {

        char currentChar = currentChar();
        while (Character.isWhitespace(currentChar) || currentChar == '{') {
            // start a comment ?
            if (currentChar == '{') {
                currentChar = nextChar();
                while (currentChar != '}' && currentChar != Source.EOF) {
                    currentChar = nextChar(); // consume all comments chars
                }

                // consume the comment closing char
                if (currentChar == '}') {
                    currentChar = nextChar();
                }

            } else {
                // consume spaces
                currentChar = nextChar();
            }
        }
    }

}
