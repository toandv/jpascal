package io.github.toandv.wci.backend.intepreter;

import io.github.toandv.wci.backend.Backend;
import io.github.toandv.wci.backend.intepreter.executors.StatementExecutor;
import io.github.toandv.wci.intermediate.icode.ICodeNode;
import io.github.toandv.wci.message.Message;

import static io.github.toandv.wci.intermediate.icode.impl.ICodeKeyImpl.LINE;
import static io.github.toandv.wci.message.MessageType.RUNTIME_ERROR;

/**
 * Created by toan on 5/22/16.
 */
public class RuntimeErrorHandler {

    private static final int MAX_ERRORS = 5;

    private int errorCount = 0;

    /**
     * Flag a runtime error
     */
    public void flag(ICodeNode node, RuntimeErrorCode errorCode, Backend backend) {
        String lineNumber = null;

        // Look for the ancestor statement node with a line number attribute.
        while (node != null && node.getAttribute(LINE) != null) {
            node = node.getParent();
        }

        //Notify the interpreter 's listeners.
        backend.sendMessage(
                new Message(RUNTIME_ERROR,
                        new Object[]{errorCode.toString(),
                                (Integer) node.getAttribute(LINE)}));
        if (++errorCount > MAX_ERRORS) {
            System.out.println("*** ABORTED AFTER TOO MANY RUNTIME ERRORS.");
            System.exit(-1);
        }
    }

    public int getRuntimeErrors() {
        return errorCount;
    }
}
