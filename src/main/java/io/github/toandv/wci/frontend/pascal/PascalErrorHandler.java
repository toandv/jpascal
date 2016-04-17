package io.github.toandv.wci.frontend.pascal;

import io.github.toandv.wci.frontend.Parser;
import io.github.toandv.wci.frontend.Token;
import io.github.toandv.wci.message.Message;
import io.github.toandv.wci.message.MessageType;

public class PascalErrorHandler {
    private static final int MAX_ERRORS = 25;

    // Why static??
    private static int errorCount = 0; // count of syntax errors

    /**
     * @return the syntax error count.
     */
    public int getErrorCount() {
        return errorCount;
    }

    /**
     * Flag an error in the source line.
     * 
     * @param token
     *            the bad token.
     * @param errorCode
     *            the error code.
     * @param parser
     *            the parser.
     * @return the flagger string.
     */
    public void flag(Token token, PascalErrorCode errorCode, Parser parser) {
        // Notify the parser's listeners.
        parser.sendMessage(new Message(MessageType.SYNTAX_ERROR,
                new Object[] { token.getLineNum(), token.getPosition(), token.getText(), errorCode.toString() }));

        if (++errorCount > MAX_ERRORS) {
            abortTranslation(PascalErrorCode.TOO_MANY_ERRORS, parser);
        }
    }

    /**
     * Abort the translation.
     * 
     * @param errorCode
     *            the error code.
     * @param parser
     *            the parser.
     */
    public void abortTranslation(PascalErrorCode errorCode, Parser parser) {
        // Notify the parser's listeners and then abort.
        String fatalText = "FATAL ERROR: " + errorCode.toString();
        parser.sendMessage(new Message(MessageType.SYNTAX_ERROR, new Object[] { 0, 0, "", fatalText }));
        System.exit(errorCode.getStatus());
    }
}
