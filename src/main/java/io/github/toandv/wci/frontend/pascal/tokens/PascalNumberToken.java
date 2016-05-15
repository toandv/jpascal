package io.github.toandv.wci.frontend.pascal.tokens;

import io.github.toandv.wci.frontend.Source;

import static io.github.toandv.wci.frontend.pascal.PascalErrorCode.*;
import static io.github.toandv.wci.frontend.pascal.PascalTokenType.*;

/**
 * A number token may contain four parts:
 * <p>
 * wholeDigits: digits before decimal point
 * <p>
 * fractionDigits: digits after decimal point
 * <p>
 * exponentDigits:
 * <p>
 * exponentSign
 *
 * @author toan
 */
public class PascalNumberToken extends PascalToken {

    public PascalNumberToken(Source source) throws Exception {
        super(source);
    }

    @Override
    protected void extract() throws Exception {
        // 10.5E10 vs 1..5e+10
        String wholeDigits;
        String fractionDigits;
        String exponentSign;
        String exponentDigits = EMPTY_STRING;
        StringBuilder textBuffer = new StringBuilder();

        this.type = INTEGER;
        // first, extract wholeDigits,
        // second, extract fractionDigits if present
        // third, find e or E
        // fourth, extract exponentSign
        // fifth, extract exponentDigits
        // sixth, compute number value

        wholeDigits = extractDigits(textBuffer);
        fractionDigits = extractFractionDigits(textBuffer);

        if (!fractionDigits.isEmpty()) {
            this.type = REAL;
        }

        exponentSign = extractExponentSign(textBuffer);

        if (!exponentSign.isEmpty()) {
            this.type = REAL;
            exponentDigits = extractDigits(textBuffer);
        }

        // compute

        if (this.type == INTEGER) {
            this.value = computeUnsignedInteger(wholeDigits);
        }

        if (this.type == REAL) {
            this.value = computeDouble(wholeDigits, fractionDigits, exponentSign, exponentDigits);
        }

        this.text = textBuffer.toString();

    }

    private double computeDouble(String wholeDigits, String fractionDigits, String exponentSign,
                                 String exponentDigits) {

        String digits = wholeDigits;

        double exponentValue = computeUnsignedDouble(exponentDigits);

        if (!fractionDigits.isEmpty()) {
            digits += fractionDigits;
            exponentValue -= fractionDigits.length();
        }
        // check for real number out of range error
        // XXX wholeDigits or digits???, (may minus  1)
        if (Math.abs(exponentValue + digits.length()) > MAX_EXPONENT) {
            type = ERROR;
            value = RANGE_REAL;
            return 0.0f;
        }
        double digitsValue = computeUnsignedDouble(digits);

        if (MINUS_SIGN.equals(exponentSign)) {
            exponentValue = -exponentValue;
        }
        return digitsValue * Math.pow(10, exponentValue);
    }

    private double computeUnsignedDouble(String digits) {
        double currentValue = 0.0;
        double prevValue = -1.0;
        int index = 0;
        // abc = a* 10^2 + b * 10^1 + c * 10^0
        // or abc = 10 * (10 * (10 * 0 + a) + b) + c
        while (index < digits.length() && currentValue >= prevValue) {
            char digit = digits.charAt(index++);
            currentValue = BASE_10 * currentValue + Character.getNumericValue(digit);
        }
        if (currentValue >= prevValue) {
            return currentValue;
        }
        //error, overflow prevValue > currentValue
        this.type = ERROR;
        this.value = RANGE_REAL;
        return 0.0;
    }

    private int computeUnsignedInteger(String digits) {
        int currentValue = 0;
        int prevValue = -1;
        int index = 0;
        // abc = a* 10^2 + b * 10^1 + c * 10^0
        // or abc = 10 * (10 * (10 * 0 + a) + b) + c
        while (index < digits.length() && currentValue >= prevValue) {
            char digit = digits.charAt(index++);
            currentValue = BASE_10 * currentValue + Character.getNumericValue(digit);
        }
        if (currentValue >= prevValue) {
            return currentValue;
        }
        //error, overflow prevValue > currentValue
        this.type = ERROR;
        this.value = RANGE_INTEGER;
        return 0;
    }

    private String extractExponentSign(StringBuilder textBuffer) throws Exception {
        char currentChar = currentChar();
        if (currentChar == EXPONENT_LOWER_CHAR || currentChar == EXPONENT_UPPER_CHAR) {
            // consume
            textBuffer.append(currentChar);
            currentChar = nextChar();
            if (currentChar == MINUS_CHAR || currentChar == PLUS_CHAR) {
                // consume
                textBuffer.append(currentChar);
                nextChar();
                return Character.toString(currentChar);
            }
            return PLUS_SIGN;
        }
        return EMPTY_STRING;
    }

    private String extractFractionDigits(StringBuilder textBuffer) throws Exception {
        if (currentChar() == PascalToken.DOT_CHAR && peekChar() == PascalToken.DOT_CHAR) {
            // dot dot token
            return EMPTY_STRING;
        }
        if (currentChar() == PascalToken.DOT_CHAR) {
            // consume
            textBuffer.append(PascalToken.DOT_CHAR);
            nextChar();
        } else {
            // Meaning this is not a REAL but an Integer.
            return EMPTY_STRING;
        }
        return extractDigits(textBuffer);
    }

    private String extractDigits(StringBuilder textBuffer) throws Exception {
        StringBuilder buffer = new StringBuilder();
        char currentChar = currentChar();
        if (!Character.isDigit(currentChar)) {
            this.type = ERROR;
            this.value = INVALID_NUMBER;
            return EMPTY_STRING;
        }
        while (Character.isDigit(currentChar)) {
            buffer.append(currentChar);
            textBuffer.append(currentChar);
            currentChar = nextChar();
        }
        return buffer.toString();
    }

}
