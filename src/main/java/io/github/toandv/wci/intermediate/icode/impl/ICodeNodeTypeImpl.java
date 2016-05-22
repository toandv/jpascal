package io.github.toandv.wci.intermediate.icode.impl;

import io.github.toandv.wci.intermediate.icode.ICodeNodeType;

public enum ICodeNodeTypeImpl implements ICodeNodeType {

    // Program structure
    PROGRAM, PROCEDURE, FUNCTION,

    // Statements
    COMPOUND, ASSIGN("Children are an VARIABLE node and an EXPRESSION node"),
    LOOP, TEST, CALL, PARAMETERS, IF, SELECT, SELECT_BRANCH, SELECT_CONSTANTS, NO_OP,

    // Relational operators
    EQ, NE, LT, LE, GT, GE, NOT,

    // Additive operators
    ADD, SUBTRACT, OR, NEGATE,

    // Multiplicative operators
    MULTIPLY, INTEGER_DIVIDE, FLOAT_DIVIDE, MOD, AND,

    // Operands
    VARIABLE, SUBSCRIPTS, FIELD, INTEGER_CONSTANT, REAL_CONSTANT, STRING_CONSTANT, BOOLEAN_CONSTANT,;

    private String des;

    ICodeNodeTypeImpl() {

    }

    ICodeNodeTypeImpl(String des) {
        this.des = des;
    }

    @Override
    public String toString() {
        return des;
    }
}
