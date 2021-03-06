
package io.github.toandv.wci.frontend;

import io.github.toandv.wci.intermediate.icode.ICode;
import io.github.toandv.wci.intermediate.symtab.SymTab;
import io.github.toandv.wci.intermediate.symtab.SymTabFactory;
import io.github.toandv.wci.intermediate.symtab.SymTabStack;
import io.github.toandv.wci.message.Message;
import io.github.toandv.wci.message.MessageHandler;
import io.github.toandv.wci.message.MessageListener;
import io.github.toandv.wci.message.MessageProducer;

/**
 * The Parser controls the translation process in the front end. It repeatedly asks the Scanner for the next Token and
 * it analyzes the sequences of tokens to determine what high-level language elements it's translating such as
 * arithmetic expressions, function or procedure declarations, and assignment statements;
 * <p>
 * The Parser also verifies the syntax of the language elements written in the source program.
 * <p>
 * In general, the Parser parses the source program and translates it.The Parser owns the Scanner and requests for
 * tokens.
 * <p>
 * The Parser is a language-independent framework class, this abstract methods should be implemented by
 * language-specific subclasses
 *
 * @author toandv
 * @since 2016/04/10
 */
public abstract class Parser implements MessageProducer {

    // XXX I doubt it
    protected static SymTab symTab;

    protected static SymTabStack symTabStack;

    // XXX why static???, I doubt this, it should be non-static
    protected static MessageHandler messageHandler;

    static {

        // TODO, just init
        symTab = SymTabFactory.createSymTab(0);

        symTabStack = SymTabFactory.createSymTabStack();
        messageHandler = new MessageHandler();
    }

    // used to read tokens
    protected Scanner scanner;

    protected ICode iCode; // intermediate code generated by this parser

    protected Parser(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Parse the source program and generate the intermediate code and symbol table
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

    public SymTab getSymTab() {
        return symTab;
    }

    public ICode getICode() {
        return iCode;
    }

    public SymTabStack getSymTabStack() {
        return symTabStack;
    }

    public Scanner getScanner() {
        return scanner;
    }
}
