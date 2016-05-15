package io.github.toandv.wci.frontend.pascal.tokens;

import io.github.toandv.wci.frontend.Source;

import static io.github.toandv.wci.frontend.pascal.PascalErrorCode.UNEXPECTED_EOF;
import static io.github.toandv.wci.frontend.pascal.PascalTokenType.ERROR;
import static io.github.toandv.wci.frontend.pascal.PascalTokenType.STRING;

/**
 * a StringToken begins and ends with a single-quote. Two immediately adjacent
 * Any chars between the single quotes are the string token's value
 * single-quotes represents a single-quote that is part of the string value
 * <p>
 * Must replace any white space with a single blank. When an EOF_CHAR encountered, an
 * error is raised - UNEXPECTED_EOF
 *
 * @author toandv
 */
public class PascalStringToken extends PascalToken {

    public PascalStringToken(Source source) throws Exception {
        super(source);
    }

    @Override
    protected void extract() throws Exception {
        StringBuilder valueBuf = new StringBuilder();
        StringBuilder textBuf = new StringBuilder();

        // consume the first char
        textBuf.append(currentChar());
        char currentChar = nextChar();
        do {
            // replace white-spaces
            if (Character.isWhitespace(currentChar)) {
                currentChar = EMPTY_CHAR;
            }

            if (currentChar != SINGLE_QUOTE_CHAR && currentChar != EOF_CHAR) {
                // just consume and next
                textBuf.append(currentChar);
                valueBuf.append(currentChar);
                currentChar = nextChar();
            }

            if (currentChar == SINGLE_QUOTE_CHAR) {
                // loop to consume adjacent single-quotes
                while (currentChar == SINGLE_QUOTE_CHAR && peekChar() == SINGLE_QUOTE_CHAR) {
                    textBuf.append(currentChar);
                    currentChar = nextChar();
                    textBuf.append(currentChar);
                    valueBuf.append(currentChar);
                    currentChar = nextChar();
                }
            }
            // m reaches here
            // ' not in pair reaches here
            // never let not ending single-quotes come here
        } while (currentChar != SINGLE_QUOTE_CHAR && currentChar != EOF_CHAR);

        // if valid, currentChar is the last char == '
        // else currentChar is not the last char != '
        // consume the final char
        if (currentChar == SINGLE_QUOTE_CHAR) {
            nextChar();
            textBuf.append(SINGLE_QUOTE_CHAR);
            this.value = valueBuf.toString();
            this.type = STRING;
        } else {
            // if the final char is not a char, this String is erroneous
            this.type = ERROR;
            this.value = UNEXPECTED_EOF;
        }

        this.text = textBuf.toString();

    }

}
