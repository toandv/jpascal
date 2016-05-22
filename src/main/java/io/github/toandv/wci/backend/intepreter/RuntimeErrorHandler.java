package io.github.toandv.wci.backend.intepreter;

import io.github.toandv.wci.backend.intepreter.executors.StatementExecutor;
import io.github.toandv.wci.intermediate.icode.ICodeNode;

/**
 * Created by toan on 5/22/16.
 */
public class RuntimeErrorHandler {
    private int runtimeErrors;

    public int getRuntimeErrors() {
        return runtimeErrors;
    }

    public void flag(ICodeNode node, RuntimeErrorCode unimplementedFeature, StatementExecutor statementExecutor) {

    }
}
