package io.github.toandv.wci.backend.compiler;

import io.github.toandv.wci.backend.Backend;
import io.github.toandv.wci.intermediate.icode.ICode;
import io.github.toandv.wci.intermediate.symtab.SymTab;
import io.github.toandv.wci.message.Message;
import io.github.toandv.wci.message.MessageType;

public class CodeGenerator extends Backend {

    @Override
    public void process(ICode icode, SymTab symtab) throws Exception {
        long startTime = System.currentTimeMillis();
        float elapsedTime = (System.currentTimeMillis() - startTime) / 1000f;
        int instructionCount = 0;
        // Send the compiler summary message.
        sendMessage(new Message(MessageType.COMPILER_SUMMARY, new Number[] { instructionCount, elapsedTime }));
    }

}
