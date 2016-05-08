package io.github.toandv.wci.intermediate.symtab.impl;

import io.github.toandv.wci.intermediate.symtab.SymTab;
import io.github.toandv.wci.intermediate.symtab.SymTabEntry;
import io.github.toandv.wci.intermediate.symtab.SymTabFactory;
import io.github.toandv.wci.intermediate.symtab.SymTabStack;

import java.util.ArrayList;
import java.util.List;

public class SymTabStackImpl implements SymTabStack {

    private int currentNestingLevel;

    private List<SymTab> symTabs;

    @Override
    public int getCurrentNestingLevel() {
        return currentNestingLevel;
    }

    public SymTabStackImpl() {
        this.currentNestingLevel = 0;
        this.symTabs = new ArrayList<>();
        this.symTabs.add(SymTabFactory.createSymTab(currentNestingLevel));
    }

    /**
     * Return the local table at the top of the stack
     */
    @Override
    public SymTab getLocalSymTab() {
        return this.symTabs.get(currentNestingLevel);
    }

    /**
     * Enter a new entry to the local table at the top
     */

    @Override
    public SymTabEntry enterLocal(String name) {
        return this.symTabs.get(currentNestingLevel).enter(name);
    }

    /**
     * Lookup in the local table
     * 
     * @return the entry, or null if it does not exist.
     */
    @Override
    public SymTabEntry lookupLocal(String name) {
        return this.symTabs.get(currentNestingLevel).lookup(name);
    }

    /**
     * Lookup throughout the stack,for now, nesting level is always 0, so stack
     * has only one table, lookup and looupLocal are functionally equivalent
     * 
     * @return the entry, or null if it does not exist.
     */
    @Override
    public SymTabEntry lookup(String name) {
        return lookupLocal(name);
    }

}
