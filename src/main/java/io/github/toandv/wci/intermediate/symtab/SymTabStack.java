
package io.github.toandv.wci.intermediate.symtab;

public interface SymTabStack {

    int getCurrentNestingLevel();

    /**
     * 
     * @return the local symbol table at the top of the stack
     */
    SymTab getLocalSymTab();

    /**
     * Create and enter a new entry into the local symbol table
     * 
     * @param name
     * @return
     */
    SymTabEntry enterLocal(String name);

    /**
     * Look up an existing symbol entry in the local symbol table (at the top of the stack)
     * 
     * @param name
     * @return
     */
    SymTabEntry lookupLocal(String name);

    /**
     * Look up an existing entry throughout the stack
     * 
     * @param name
     * @return
     */
    SymTabEntry lookup(String name);
}
