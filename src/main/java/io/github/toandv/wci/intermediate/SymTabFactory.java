package io.github.toandv.wci.intermediate;

import io.github.toandv.wci.intermediate.symtabimpl.SymTabEntryImpl;
import io.github.toandv.wci.intermediate.symtabimpl.SymTabImpl;
import io.github.toandv.wci.intermediate.symtabimpl.SymTabStackImpl;

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
