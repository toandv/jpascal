package io.github.toandv.wci.intermediate.symtabimpl;

import io.github.toandv.wci.intermediate.SymTab;
import io.github.toandv.wci.intermediate.SymTabEntry;
import io.github.toandv.wci.intermediate.SymTabStack;

public class SymTabFactory {
    public static final SymTabStack createSymTabStack() {
        return new SymTabStackImpl();
    }

    public static final SymTab createSymTab(int nestingLevel) {
        return new SymTabImpl(nestingLevel);
    }

    public static final SymTabEntry createSymTabEntry(String name, SymTab symTab) {
        return new SymTabEntryImpl(name, symTab);
    }
}
