
package io.github.toandv.wci.intermediate.icode.impl;

import io.github.toandv.wci.intermediate.icode.ICode;
import io.github.toandv.wci.intermediate.icode.ICodeNode;

public class ICodeImpl implements ICode {

    private ICodeNode root;

    @Override
    public ICodeNode setRoot(ICodeNode root) {
        this.root = root;
        return this.root;
    }

    @Override
    public ICodeNode getRoot() {
        return this.root;
    }

}
