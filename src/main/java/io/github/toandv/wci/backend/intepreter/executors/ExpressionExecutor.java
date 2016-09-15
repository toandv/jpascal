package io.github.toandv.wci.backend.intepreter.executors;

import com.google.common.collect.Sets;
import io.github.toandv.wci.intermediate.icode.ICodeNode;
import io.github.toandv.wci.intermediate.icode.impl.ICodeNodeImpl;
import io.github.toandv.wci.intermediate.icode.impl.ICodeNodeTypeImpl;
import io.github.toandv.wci.intermediate.symtab.SymTabEntry;
import io.github.toandv.wci.utils.NumberUtils;

import java.util.Set;

import static io.github.toandv.wci.intermediate.icode.impl.ICodeKeyImpl.ID;
import static io.github.toandv.wci.intermediate.icode.impl.ICodeKeyImpl.VALUE;
import static io.github.toandv.wci.intermediate.icode.impl.ICodeNodeTypeImpl.*;
import static io.github.toandv.wci.intermediate.symtab.impl.SymTabKeyImpl.DATA_VALUE;

/**
 * Created by toan on 5/22/16.
 */
public class ExpressionExecutor extends StatementExecutor {
    public ExpressionExecutor(StatementExecutor assignmentExecutor) {
        super(assignmentExecutor);
    }

    public static final Set<ICodeNodeTypeImpl> ARITH_OPS =
            Sets.immutableEnumSet(ADD, SUBTRACT, MULTIPLY, FLOAT_DIVIDE, INTEGER_DIVIDE);

    // TODO - optimize recursive calls
    @Override
    public Object execute(ICodeNode node) {
        ICodeNodeTypeImpl nodeType = (ICodeNodeTypeImpl) node.getType();

        switch (nodeType) {
            case INTEGER_CONSTANT:
            case REAL_CONSTANT:
            case STRING_CONSTANT:
                return node.getAttribute(VALUE);
            case VARIABLE: {
                SymTabEntry variableEntry = (SymTabEntry) node.getAttribute(ID);
                return variableEntry.getAttribute(DATA_VALUE);
            }
            case NEGATE: {
                // Get NEGATE's expression node child
                ICodeNode expressionNode = node.getChildren().get(0);
                Object value = execute(expressionNode);
                return NumberUtils.negate(value);
            }
            case NOT: {
                ICodeNode expressionNode = node.getChildren().get(0);
                Boolean value = (Boolean) execute(expressionNode);
                return !value;
            }
            default:
                return executeBinaryOperator(node, nodeType);
        }
    }

    private Object executeBinaryOperator(ICodeNode node, ICodeNodeTypeImpl nodeType) {
        // TODO

        ICodeNodeImpl operandNode1 = (ICodeNodeImpl) node.getChildren().get(0);
        ICodeNodeImpl operandNode2 = (ICodeNodeImpl) node.getChildren().get(1);

        // recursive calls
        Object operand1 = execute(operandNode1);
        Object operand2 = execute(operandNode2);

        if (ARITH_OPS.contains(nodeType)) {
            if (operand1 instanceof Integer && operand2 instanceof Integer) {
                int value1 = (int) operand1;
                int value2 = (int) operand2;

            } else {

            }
        }

        return null;
    }
}
