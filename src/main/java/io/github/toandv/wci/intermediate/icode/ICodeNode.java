package io.github.toandv.wci.intermediate.icode;

public interface ICodeNode {

    ICodeNode getParent();

    ICodeNodeType getType();

    ICodeNode addChild();

    void setAttribute();

    Object getAttribute();

    ICodeNode copy();

}
