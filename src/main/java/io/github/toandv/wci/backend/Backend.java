
package io.github.toandv.wci.backend;

import io.github.toandv.wci.intermediate.icode.ICode;
import io.github.toandv.wci.intermediate.symtab.SymTab;
import io.github.toandv.wci.message.Message;
import io.github.toandv.wci.message.MessageHandler;
import io.github.toandv.wci.message.MessageListener;
import io.github.toandv.wci.message.MessageProducer;

public abstract class Backend implements MessageProducer {

    protected static MessageHandler messageHandler;

    protected SymTab symTab;

    protected ICode icode;

    static {
        messageHandler = new MessageHandler();
    }

    public abstract void process(ICode icode, SymTab symtab) throws Exception;

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
