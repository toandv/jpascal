package io.github.toandv.wci.frontend.pascal;

import io.github.toandv.wci.frontend.EofToken;
import io.github.toandv.wci.frontend.Parser;
import io.github.toandv.wci.frontend.Scanner;
import io.github.toandv.wci.frontend.Token;
import io.github.toandv.wci.message.Message;
import io.github.toandv.wci.message.MessageType;

/**
 * The top-down Pascal parser
 * 
 * @author toan
 *
 */
public class PascalParserTD extends Parser {

    public PascalParserTD(Scanner scanner) {
        super(scanner);
    }

    @Override
    public void parse() throws Exception {
        Token token;
        long startTime = System.currentTimeMillis();
        while (!((token = nextToken()) instanceof EofToken)) {
            // do nothing, just reach to the end
        }
        // Send the parser summary message.
        float elapsedTime = (System.currentTimeMillis() - startTime) / 1000f;
        sendMessage(new Message(MessageType.PARSER_SUMMARY,
                new Number[] { token.getLineNum(), getErrorCount(), elapsedTime }));
    }

    @Override
    public int getErrorCount() {
        return 0;
    }

}
