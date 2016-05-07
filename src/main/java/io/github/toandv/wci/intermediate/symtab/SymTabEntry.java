package io.github.toandv.wci.intermediate.symtab;

import java.util.List;

public interface SymTabEntry {
    String getName();

    SymTab getSymTab();

    void appendLineNumber(int lineNumber);

    List<Integer> getLineNumbers();

    void setAttribute(SymTabKey key, Object value);

    Object getAttribute(SymTabKey key);
}
