package io.github.toandv.wci.intermediate.symtabimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import io.github.toandv.wci.intermediate.SymTab;
import io.github.toandv.wci.intermediate.SymTabEntry;

public class SymTabImpl implements SymTab {

    private int nestingLevel;

    private Map<String, SymTabEntry> entries;

    public SymTabImpl(int nestingLevel) {
        this.nestingLevel = nestingLevel;

        // use TreeMap for stable performance on a large entry set
        // no rehashing, no copy, no pre-allocation (make sure???, just
        // reasoning),may be just ordering
        this.entries = new TreeMap<>();
    }

    @Override
    public SymTabEntry enter(String name) {
        return this.entries.put(name, SymTabFactory.createSymTabEntry(name, this));
    }

    @Override
    public SymTabEntry lookup(String name) {
        return this.entries.get(name);
    }

    @Override
    public List<SymTabEntry> sortedEntries() {
        return new ArrayList<>(entries.values());
    }

}
