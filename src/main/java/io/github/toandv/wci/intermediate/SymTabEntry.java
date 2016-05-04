package io.github.toandv.wci.intermediate;

import java.util.List;

public interface SymTabEntry {
    String getName();

    SymTab getSymTab();

    List<Integer> getLinesNumber();

    void setAttribute(SymTabKey key, Object value);

    Object getAttribute(SymTabKey key);
}
