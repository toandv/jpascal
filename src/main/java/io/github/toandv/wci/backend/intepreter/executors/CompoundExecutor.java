package io.github.toandv.wci.backend.intepreter.executors;

import io.github.toandv.wci.intermediate.icode.ICode;
import io.github.toandv.wci.intermediate.icode.ICodeNode;
import io.github.toandv.wci.intermediate.symtab.SymTab;

import java.util.List;

/**
 * Created by toan on 5/17/16.
 */
public class CompoundExecutor extends StatementExecutor {
    public CompoundExecutor(StatementExecutor statementExecutor) {
        super(statementExecutor);
    }

    public CompoundExecutor(SymTab symTab, ICode icode) {
        super(symTab, icode);
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
