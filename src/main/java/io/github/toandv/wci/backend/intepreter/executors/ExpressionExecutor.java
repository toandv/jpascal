package io.github.toandv.wci.backend.intepreter.executors;

import com.google.common.collect.Sets;
import io.github.toandv.wci.intermediate.icode.ICode;
import io.github.toandv.wci.intermediate.icode.ICodeNode;
import io.github.toandv.wci.intermediate.icode.impl.ICodeNodeImpl;
import io.github.toandv.wci.intermediate.icode.impl.ICodeNodeTypeImpl;
import io.github.toandv.wci.intermediate.symtab.SymTab;
import io.github.toandv.wci.intermediate.symtab.SymTabEntry;
import io.github.toandv.wci.utils.NumberUtils;

import java.util.Set;

import static io.github.toandv.wci.backend.intepreter.RuntimeErrorCode.DIVISION_BY_ZERO;
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

    public ExpressionExecutor(SymTab symTab, ICode icode) {
        super(symTab, icode);
    }

    public static final Set<ICodeNodeTypeImpl> ARITH_OPS =
            Sets.immutableEnumSet(ADD, SUBTRACT, MULTIPLY, DIVIDE, ICodeNodeTypeImpl.DIVIDE);

    public static final Set<ICodeNodeTypeImpl> RELATIONAL_OPS =
            Sets.immutableEnumSet(EQ, NE, GT, GE, LE, LT);

    public static final Set<ICodeNodeTypeImpl> AND_OR =
            Sets.immutableEnumSet(AND, OR);

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

    Object executeBinaryOperator(ICodeNode node, ICodeNodeTypeImpl nodeType) {

        ICodeNodeImpl operandNode1 = (ICodeNodeImpl) node.getChildren().get(0);
        ICodeNodeImpl operandNode2 = (ICodeNodeImpl) node.getChildren().get(1);

        // FIXME: recursive calls
        // must not be null
        Object operand1 = execute(operandNode1);
        Object operand2 = execute(operandNode2);


        if (ARITH_OPS.contains(nodeType)) {
            return evalArithOperation(node, nodeType, operand1, operand2);
        }
        if (AND_OR.contains(nodeType)) {
            return evalAndOR(nodeType, operand1, operand2);
        }
        if (RELATIONAL_OPS.contains(nodeType)) {
            return evalRelationalOperation(nodeType, operand1, operand2);
        }
        return null;
    }


    private Object evalArithOperation(ICodeNode node, ICodeNodeTypeImpl nodeType, Object operand1, Object operand2) {
        if (bothIntegers(operand1, operand2)) {
            int ival1 = (int) operand1;
            int ival2 = (int) operand2;
            switch (nodeType) {
                case ADD:
                    return ival1 + ival2;
                case SUBTRACT:
                    return ival1 - ival2;
                case MULTIPLY:
                    return ival1 * ival2;
                case DIVIDE: {
                    if (ival2 == 0) {
                        runtimeErrorHandler.flag(node, DIVISION_BY_ZERO, this);
                        return 0;
                    }
                    return ival1 / ival2;
                }
            }
        } else if (anyDouble(operand1, operand2)) {

            double dval1 = ((Number) operand1).doubleValue();
            double dval2 = ((Number) operand2).doubleValue();

            switch (nodeType) {
                case ADD:
                    return dval1 + dval2;
                case SUBTRACT:
                    return dval1 - dval2;
                case MULTIPLY:
                    return dval1 * dval2;
                case DIVIDE: {
                    if (dval2 == 0) {
                        runtimeErrorHandler.flag(node, DIVISION_BY_ZERO, this);
                        return 0;
                    }
                    return dval1 / dval2;
                }
            }
        }
        // should never get here
        return null;
    }

    private Boolean evalAndOR(ICodeNodeTypeImpl nodeType, Object operand1, Object operand2) {

        boolean bval1 = (boolean) operand1;
        boolean bval2 = (boolean) operand2;

        switch (nodeType) {
            case AND:
                return bval1 && bval2;
            case OR:
                return bval1 || bval2;
        }
        // should never get here
        return null;
    }

    private Boolean evalRelationalOperation(ICodeNodeTypeImpl nodeType, Object operand1, Object operand2) {
        if (bothIntegers(operand1, operand2)) {

            int ival1 = (int) operand1;
            int ival2 = (int) operand2;

            switch (nodeType) {
                case EQ:
                    return ival1 == ival2;
                case NE:
                    return ival1 != ival2;
                case LT:
                    return ival1 < ival2;
                case LE:
                    return ival1 <= ival2;
                case GT:
                    return ival1 > ival2;
                case GE:
                    return ival1 >= ival2;
            }
        } else if (anyDouble(operand1, operand2)) {

            double dval1 = ((Number) operand1).doubleValue();
            double dval2 = ((Number) operand2).doubleValue();

            switch (nodeType) {
                case EQ:
                    return dval1 == dval2;
                case NE:
                    return dval1 != dval2;
                case LT:
                    return dval1 < dval2;
                case LE:
                    return dval1 <= dval2;
                case GT:
                    return dval1 > dval2;
                case GE:
                    return dval1 >= dval2;
            }
        }
        // should never get here
        return null;
    }

    private boolean bothIntegers(Object operand1, Object operand2) {
        return operand1 instanceof Integer && operand2 instanceof Integer;
    }

    private boolean anyDouble(Object operand1, Object operand2) {
        return operand1 instanceof Double || operand2 instanceof Double;
    }
}
