package io.github.toandv.wci.backend.intepreter.executors;

import io.github.toandv.wci.intermediate.icode.ICodeNode;
import io.github.toandv.wci.intermediate.symtab.SymTabEntry;
import io.github.toandv.wci.message.Message;

import static io.github.toandv.wci.intermediate.icode.impl.ICodeKeyImpl.ID;
import static io.github.toandv.wci.intermediate.icode.impl.ICodeKeyImpl.LINE;
import static io.github.toandv.wci.intermediate.symtab.impl.SymTabKeyImpl.DATA_VALUE;
import static io.github.toandv.wci.message.MessageType.ASSIGN;

/**
 * Created by toan on 5/22/16.
 */
public class AssignmentExecutor extends StatementExecutor {
    public AssignmentExecutor(StatementExecutor statementExecutor) {
        super(statementExecutor);
    }

    @Override
    public Object execute(ICodeNode node) {

        // The ASSIGN node's children are a variable node and an expression node.

        ICodeNode variableNode = node.getChildren().get(0);
        ICodeNode expressionNode = node.getChildren().get(1);

        // Execute the expression and get its value.
        ExpressionExecutor expressionExecutor = new ExpressionExecutor(this);
        Object value = expressionExecutor.execute(expressionNode);

        // Set the value as an attribute of the variable's symbol table entry
        SymTabEntry variableId = (SymTabEntry) variableNode.getAttribute(ID);
        variableId.setAttribute(DATA_VALUE, value);

        sendMessage(node, variableId.getName(), value);
        executionCount++;
        return null;
    }

    private void sendMessage(ICodeNode node, String variableName, Object value) {
        Object lineNumber = node.getAttribute(LINE);
        if (lineNumber != null) {
            sendMessage(new Message(ASSIGN, new Object[] {lineNumber, variableName, value}));
        }
    }
}
