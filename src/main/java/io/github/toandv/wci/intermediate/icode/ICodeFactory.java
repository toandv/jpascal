
package io.github.toandv.wci.intermediate.icode;

import io.github.toandv.wci.intermediate.icode.impl.ICodeImpl;
import io.github.toandv.wci.intermediate.icode.impl.ICodeNodeImpl;

public class ICodeFactory {

    public static ICodeNode createICodeNode(ICodeNodeType type) {
        return new ICodeNodeImpl(type);
    }

    public static ICode createICode() {
        return new ICodeImpl();
    }

}
