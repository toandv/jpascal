package io.github.toandv.wci.backend.intepreter;

import io.github.toandv.wci.backend.Backend;
import io.github.toandv.wci.backend.intepreter.executors.StatementExecutor;
import io.github.toandv.wci.intermediate.icode.ICode;
import io.github.toandv.wci.intermediate.icode.ICodeNode;
import io.github.toandv.wci.intermediate.symtab.SymTab;
import io.github.toandv.wci.message.Message;
import io.github.toandv.wci.message.MessageType;

public class Executor extends Backend {

    // NOTE: I have a big doubt against the author design and coding style
    protected static int executionCount;

    protected static RuntimeErrorHandler runtimeErrorHandler;

    static {
        runtimeErrorHandler = new RuntimeErrorHandler();
    }

    public Executor(Executor parent) {
        this.symTab = parent.symTab;
        this.icode = parent.icode;
    }

    @Override
    public void process(ICode icode, SymTab symtab) throws Exception {
        this.icode = icode;
        this.symTab = symtab;

        ICodeNode rootNode = this.icode.getRoot();
        StatementExecutor statementExecutor = new StatementExecutor(this);

        long startTime = System.currentTimeMillis();

        statementExecutor.execute(rootNode);

        float elapsedTime = (System.currentTimeMillis() - startTime) / 1000f;
        // Send the interpreter summary message.
        sendMessage(new Message(MessageType.INTERPRETER_SUMMARY,
                new Number[]{executionCount, runtimeErrorHandler.getRuntimeErrors(), elapsedTime}));

    }

}
