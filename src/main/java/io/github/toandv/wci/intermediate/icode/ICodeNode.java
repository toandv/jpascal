
package io.github.toandv.wci.intermediate.icode;

import java.util.List;

public interface ICodeNode {

    ICodeNode getParent();

    ICodeNodeType getType();

    ICodeNode addChild(ICodeNode child);
    
    List<ICodeNode> getChildren();

    Object getAttribute(ICodeKey key);

    Object setAttribute(ICodeKey key, Object value);

    Object setMultiValuesAttribute(ICodeKey key, Object value);

    ICodeNode copy();

}
