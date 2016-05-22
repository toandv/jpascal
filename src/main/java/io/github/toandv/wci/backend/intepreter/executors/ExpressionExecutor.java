package io.github.toandv.wci.backend.intepreter.executors;

/**
 * Created by toan on 5/22/16.
 */
public class ExpressionExecutor extends StatementExecutor {
    public ExpressionExecutor(StatementExecutor assignmentExecutor) {
        super(assignmentExecutor);
    }
}
