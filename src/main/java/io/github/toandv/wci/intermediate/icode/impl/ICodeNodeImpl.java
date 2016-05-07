
package io.github.toandv.wci.intermediate.icode.impl;

import io.github.toandv.wci.intermediate.icode.ICodeNode;
import io.github.toandv.wci.intermediate.icode.ICodeNodeType;

public class ICodeNodeImpl implements ICodeNode {

    private ICodeNodeType type;

    public ICodeNodeImpl(ICodeNodeType type) {
        this.type = type;
    }

    @Override
    public ICodeNode getParent() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ICodeNodeType getType() {
        return this.type;
    }

    @Override
    public ICodeNode addChild() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setAttribute() {
        // TODO Auto-generated method stub

    }

    @Override
    public Object getAttribute() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ICodeNode copy() {
        // TODO Auto-generated method stub
        return null;
    }

}
