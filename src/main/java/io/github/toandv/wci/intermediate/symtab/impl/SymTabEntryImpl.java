package io.github.toandv.wci.intermediate.symtab.impl;

import io.github.toandv.wci.intermediate.symtab.SymTab;
import io.github.toandv.wci.intermediate.symtab.SymTabEntry;
import io.github.toandv.wci.intermediate.symtab.SymTabKey;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SymTabEntryImpl implements SymTabEntry {

    private String name;

    private List<Integer> lineNumbers;

    private SymTab symTab;

    private Map<SymTabKey, Object> attributeMap;

    public SymTabEntryImpl(String name, SymTab symTab) {
        this.name = name;
        this.symTab = symTab;
        this.lineNumbers = new ArrayList<>();
        this.attributeMap = new HashMap<>();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public SymTab getSymTab() {
        return symTab;
    }

    @Override
    public void appendLineNumber(int lineNumber) {
        this.lineNumbers.add(lineNumber);
    }

    @Override
    public List<Integer> getLineNumbers() {
        return lineNumbers;
    }

    @Override
    public void setAttribute(SymTabKey key, Object value) {
        this.attributeMap.put(key, value);
    }

    @Override
    public Object getAttribute(SymTabKey key) {
        return this.attributeMap.get(key);
    }

}
