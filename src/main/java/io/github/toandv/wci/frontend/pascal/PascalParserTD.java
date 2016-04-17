
package io.github.toandv.wci.frontend.pascal;

import io.github.toandv.wci.frontend.EofToken;
import io.github.toandv.wci.frontend.Parser;
import io.github.toandv.wci.frontend.Scanner;
import io.github.toandv.wci.frontend.Token;
import io.github.toandv.wci.frontend.TokenType;
import io.github.toandv.wci.message.Message;
import io.github.toandv.wci.message.MessageType;

/**
 * The top-down Pascal parser
 * 
 * @author toan
 *
 */
public class PascalParserTD extends Parser {

    protected static PascalErrorHandler errorHandler = new PascalErrorHandler();

    public PascalParserTD(Scanner scanner) {
        super(scanner);
    }

    /**
     * Parse a Pascal program to generate the symbol table and the intermediate
     * code
     */
    @Override
    public void parse() throws Exception {
        Token token;
        long startTime = System.currentTimeMillis();
        try {
            // Loop over each token until the end of file.
            while (!((token = nextToken()) instanceof EofToken)) {
                TokenType tokenType = token.getType();

                if (tokenType != PascalTokenType.ERROR) {

                    // Format each token.
                    sendMessage(new Message(MessageType.TOKEN, new Object[] { token.getLineNum(), token.getPosition(),
                            tokenType, token.getText(), token.getValue() }));
                } else {
                    errorHandler.flag(token, (PascalErrorCode) token.getValue(), this);
                }

            }

            // Send the parser summary message.
            float elapsedTime = (System.currentTimeMillis() - startTime) / 1000f;
            sendMessage(new Message(MessageType.PARSER_SUMMARY,
                    new Number[] { token.getLineNum(), getErrorCount(), elapsedTime }));
        } catch (java.io.IOException ex) {
            errorHandler.abortTranslation(PascalErrorCode.IO_ERROR, this);
        }
    }

    @Override
    public int getErrorCount() {
        return errorHandler.getErrorCount();
    }

}
