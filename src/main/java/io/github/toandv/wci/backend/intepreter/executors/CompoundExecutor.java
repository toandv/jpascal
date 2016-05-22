package io.github.toandv.wci.backend.intepreter.executors;

import io.github.toandv.wci.intermediate.icode.ICodeNode;

import java.util.List;

/**
 * Created by toan on 5/17/16.
 */
public class CompoundExecutor extends StatementExecutor {
    public CompoundExecutor(StatementExecutor statementExecutor) {
        super(statementExecutor);
    }

    @Override
    public Object execute(ICodeNode node) {
        // Execute child nodes
        List<ICodeNode> childNodes = node.getChildren();
        StatementExecutor statementExecutor = new StatementExecutor(this);
        childNodes.forEach(child -> statementExecutor.execute(child));
        return null;
    }
}
