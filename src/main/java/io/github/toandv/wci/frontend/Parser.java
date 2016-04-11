package io.github.toandv.wci.frontend;

import io.github.toandv.wci.intermediate.SymTab;
import io.github.toandv.wci.message.Message;
import io.github.toandv.wci.message.MessageHandler;
import io.github.toandv.wci.message.MessageListener;
import io.github.toandv.wci.message.MessageProducer;

/**
 * The Parser controls the translation process in the front end. It repeatedly
 * asks the Scanner for the next Token and it analyzes the sequences of tokens
 * to determine what high-level language elements it's translating such as
 * arithmetic expressions, function or procedure declarations, and assignment
 * statements;
 * 
 * The Parser also verifies the syntax of the language elements written in the
 * source program.
 * 
 * In general, the Parser parses the source program and translates it.The Parser
 * owns the Scanner and requests for tokens.
 * 
 * The Parser is a language-independent framework class, this abstract methods
 * should be implemented by language-specific subclasses
 * 
 * @author toandv
 * @since 2016/04/10
 */
public abstract class Parser implements MessageProducer {

    // XXX I doubt it
    protected static SymTab symTab;

    // XXX why static???, I doubt this, it should be non-static
    protected static MessageHandler messageHandler;

    static {
        symTab = null;
        messageHandler = new MessageHandler();
    }

    // used to read tokens
    protected Scanner scanner;

    protected Parser(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Parse the source program and generate the intermediate code and symbol
     * table
     */
    public abstract void parse() throws Exception;

    public abstract int getErrorCount();

    public Token currentToken() {
        return scanner.currentToken();
    }

    public Token nextToken() throws Exception {
        return scanner.nextToken();
    }

    @Override
    public void addMessageListener(MessageListener listener) {
        messageHandler.addListener(listener);
    }

    @Override
    public void removeMessageListener(MessageListener listener) {
        messageHandler.removeListener(listener);
    }

    @Override
    public void sendMessage(Message message) {
        messageHandler.sendMessage(message);
    }

}
