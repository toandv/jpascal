package io.github.toandv.wci.intermediate.symtabimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import io.github.toandv.wci.intermediate.SymTab;
import io.github.toandv.wci.intermediate.SymTabEntry;
import io.github.toandv.wci.intermediate.SymTabFactory;

public class SymTabImpl implements SymTab {

    private int nestingLevel;

    private Map<String, SymTabEntry> entryMap;

    public SymTabImpl(int nestingLevel) {
        this.nestingLevel = nestingLevel;

        // use TreeMap for stable performance on a large entry set
        // no rehashing, no copy, no pre-allocation (make sure???, just
        // reasoning),may be just ordering
        this.entryMap = new TreeMap<>();
    }

    @Override
    public SymTabEntry enter(String name) {
        SymTabEntry entry = SymTabFactory.createSymTabEntry(name, this);
        this.entryMap.put(name, entry);
        return entry;
    }

    @Override
    public SymTabEntry lookup(String name) {
        return this.entryMap.get(name);
    }

    @Override
    public List<SymTabEntry> sortedEntries() {
        return new ArrayList<>(entryMap.values());
    }

}