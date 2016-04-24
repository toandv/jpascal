package io.github.toandv.wci.frontend.pascal.tokens;

import io.github.toandv.wci.frontend.Source;
import io.github.toandv.wci.frontend.pascal.PascalTokenType;

/**
 * A number token may contain four parts:
 * 
 * wholeDigits: digits before decimal point
 * 
 * fractionDigits: digits after decimal point
 * 
 * exponentDigits:
 * 
 * exponentSign
 * 
 * @author toan
 *
 */
public class PascalNumberToken extends PascalToken {

    public PascalNumberToken(Source source) throws Exception {
        super(source);
    }

    @Override
    protected void extract() throws Exception {
        // 10.5E10 vs 1..5e+10
        String wholeDigits = "";
        String fractionDigits = "";
        String exponentSign = "";
        String exponentDigits = "";
        StringBuilder textBuffer = new StringBuilder();

        this.type = PascalTokenType.INTEGER;
        // first, extract wholeDigits,
        // second, extract fractionDigits if present
        // third, find e or E
        // fourth, extract exponentSign
        // fifth, extract exponentDigits
        // sixth, compute number value

        wholeDigits = extractDigits();
        fractionDigits = extractFractionDigits();

        if (!fractionDigits.isEmpty()) {
            this.type = PascalTokenType.REAL;
        }

        exponentSign = extractExponentSign();

        if (!exponentSign.isEmpty()) {
            this.type = PascalTokenType.REAL;
            exponentDigits = extractDigits();
            if (exponentDigits.isEmpty()) {
                this.type = PascalTokenType.ERROR;
            }
        }

    }

    private String extractExponentSign() throws Exception {
        char currentChar = currentChar();
        if (currentChar == 'e' || currentChar == 'E') {
            // consume
            currentChar = nextChar();
            if (currentChar == '-' || currentChar == '+') {
                // consume
                nextChar();
                return Character.toString(currentChar);
            }
            return "+";
        }
        return "";
    }

    private String extractFractionDigits() throws Exception {
        if (currentChar() == '.' && peekChar() == '.') {
            // dot dot token
            return "";
        }
        if (currentChar() == '.') {
            // consume
            nextChar();
        }
        return extractDigits();
    }

    private String extractDigits() throws Exception {

        // this must be digit obviously
        StringBuilder buffer = new StringBuilder();
        char currentChar = currentChar();
        while (Character.isDigit(currentChar)) {
            buffer.append(currentChar);
            currentChar = nextChar();
        }
        return buffer.toString();
    }

}
