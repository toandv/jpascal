package io.github.toandv.wci.frontend.pascal.parsers;

import io.github.toandv.wci.frontend.Parser;
import io.github.toandv.wci.frontend.Scanner;
import io.github.toandv.wci.frontend.Token;
import io.github.toandv.wci.intermediate.icode.ICodeFactory;
import io.github.toandv.wci.intermediate.icode.ICodeNode;
import io.github.toandv.wci.intermediate.icode.ICodeNodeType;
import io.github.toandv.wci.intermediate.icode.impl.ICodeKeyImpl;

/**
 * Created by toan on 5/27/16.
 */
public class TermParser extends ExpressionParser {
    public TermParser(Parser parent) {
        super(parent);
    }

    public TermParser(Scanner scanner) {
        super(scanner);
    }

    @Override
    public ICodeNode parse(Token token) throws Exception {
        // Parse a factor and make its node the tree root.

        FactorParser factorParser = new FactorParser(this);
        ICodeNode rootNode = factorParser.parse(token);

        // Update current token.
        token = currentToken();

        while (MULT_OPS.contains(token.getType())) {
            ICodeNodeType opNodeType = MULT_OPS_OPS_MAP.get(token.getType());
            ICodeNode opNode = ICodeFactory.createICodeNode(opNodeType);
            opNode.addChild(rootNode);

            token = nextToken(); // Consume op node.

            // Parse another term.
            ICodeNode rightHandNode = factorParser.parse(token);
            opNode.addChild(rightHandNode);

            rootNode = opNode;
            rootNode.setMultiValuesAttribute(ICodeKeyImpl.EBNF_SYMBOL, PascalNonTerminal.TERM);
            token = currentToken(); // Update current token.
        }
        rootNode.setMultiValuesAttribute(ICodeKeyImpl.EBNF_SYMBOL, PascalNonTerminal.TERM);
        return rootNode;
    }


}
