package io.github.toandv.wci.intermediate;

import java.util.List;

public interface SymTab {
    SymTabEntry enter(String name);

    SymTabEntry lookup(String name);

    List<SymTabEntry> sortedEntries();
}
