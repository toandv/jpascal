package io.github.toandv.wci.backend.intepreter.executors;

import io.github.toandv.wci.backend.intepreter.Executor;
import io.github.toandv.wci.intermediate.icode.ICodeNode;
import io.github.toandv.wci.intermediate.icode.impl.ICodeNodeTypeImpl;
import io.github.toandv.wci.message.Message;

import static io.github.toandv.wci.backend.intepreter.RuntimeErrorCode.UNIMPLEMENTED_FEATURE;
import static io.github.toandv.wci.intermediate.icode.impl.ICodeKeyImpl.LINE;
import static io.github.toandv.wci.message.MessageType.SOURCE_LINE;

/**
 * Created by toan on 5/17/16.
 */
public class StatementExecutor extends Executor {

    public StatementExecutor(Executor parent) {
        super(parent);
    }

    public Object execute(ICodeNode node) {
        ICodeNodeTypeImpl nodeType = (ICodeNodeTypeImpl) node.getType();

        // Send a message about the current source line.
        sendSourceLineMessage(node);

        switch (nodeType) {
            case COMPOUND:
                CompoundExecutor compoundExecutor = new CompoundExecutor(this);
                return compoundExecutor.execute(node);
            case ASSIGN:
                AssignmentExecutor assignmentExecutor = new AssignmentExecutor(this);
                return assignmentExecutor.execute(node);
            case NO_OP:
                return null;
            default:
                runtimeErrorHandler.flag(node, UNIMPLEMENTED_FEATURE, this);
                return null;
        }
    }

    private void sendSourceLineMessage(ICodeNode node) {
        Object lineNumber = node.getAttribute(LINE);
        if (lineNumber != null) {
            sendMessage(new Message(SOURCE_LINE, lineNumber));
        }
    }
}
