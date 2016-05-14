
package io.github.toandv.wci.frontend.pascal;

import io.github.toandv.wci.frontend.*;
import io.github.toandv.wci.frontend.pascal.parsers.StatementParser;
import io.github.toandv.wci.intermediate.icode.ICode;
import io.github.toandv.wci.intermediate.icode.ICodeFactory;
import io.github.toandv.wci.intermediate.icode.ICodeNode;
import io.github.toandv.wci.message.Message;
import io.github.toandv.wci.message.MessageType;

/**
 * The top-down Pascal parser
 *
 * @author toan
 */
public class PascalParserTD extends Parser {

    protected static PascalErrorHandler errorHandler = new PascalErrorHandler();

    public PascalParserTD(Scanner scanner) {
        super(scanner);
    }

    public PascalParserTD(PascalParserTD parent) {
        super(parent.scanner);
    }

    /**
     * Parse a Pascal program to generate the symbol table and the intermediate
     * code
     */
    @Override
    public void parse() throws Exception {
        long startTime = System.currentTimeMillis();
        iCode = ICodeFactory.createICode();
        try {
            Token token = nextToken();
            ICodeNode rootNode = null;
            if (token.getType() == PascalTokenType.BEGIN) {
                StatementParser statementParser = new StatementParser(this);
                rootNode = statementParser.parse(token);

                // Update current token.
                token = currentToken();
            } else {
                errorHandler.flag(token, PascalErrorCode.UNEXPECTED_TOKEN, this);
            }

            // Look for final token, final period.
            if (token.getType() != PascalTokenType.DOT) {
                errorHandler.flag(token, PascalErrorCode.MISSING_PERIOD, this);
            }

            token = currentToken();
            // Set the AST root node.
            if (rootNode != null) {
                iCode.setRoot(rootNode);
            }

            // Send the parser summary message.
            float elapsedTime = (System.currentTimeMillis() - startTime) / 1000f;
            sendMessage(new Message(MessageType.PARSER_SUMMARY,
                    new Number[]{token.getLineNumber(), getErrorCount(), elapsedTime}));
        } catch (java.io.IOException ex) {
            errorHandler.abortTranslation(PascalErrorCode.IO_ERROR, this);
        }
    }

    @Override
    public int getErrorCount() {
        return errorHandler.getErrorCount();
    }

}
