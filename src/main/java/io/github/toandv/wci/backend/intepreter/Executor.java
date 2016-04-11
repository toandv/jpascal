package io.github.toandv.wci.backend.intepreter;

import io.github.toandv.wci.backend.Backend;
import io.github.toandv.wci.intermediate.ICode;
import io.github.toandv.wci.intermediate.SymTab;
import io.github.toandv.wci.message.Message;
import io.github.toandv.wci.message.MessageType;

public class Executor extends Backend {

    @Override
    public void process(ICode icode, SymTab symtab) throws Exception {
        long startTime = System.currentTimeMillis();
        float elapsedTime = (System.currentTimeMillis() - startTime) / 1000f;
        int executionCount = 0;
        int runtimeErrors = 0;
        // Send the interpreter summary message.
        sendMessage(new Message(MessageType.INTERPRETER_SUMMARY,
                new Number[] { executionCount, runtimeErrors, elapsedTime }));

    }

}
