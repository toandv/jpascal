package io.github.toandv.wci.frontend.pascal.parsers;

import io.github.toandv.wci.frontend.Parser;
import io.github.toandv.wci.frontend.Scanner;
import io.github.toandv.wci.frontend.Token;
import io.github.toandv.wci.frontend.pascal.PascalTokenType;
import io.github.toandv.wci.intermediate.icode.ICodeFactory;
import io.github.toandv.wci.intermediate.icode.ICodeNode;
import io.github.toandv.wci.intermediate.symtab.SymTabEntry;

import static io.github.toandv.wci.frontend.pascal.PascalErrorCode.*;
import static io.github.toandv.wci.frontend.pascal.PascalTokenType.RIGHT_PAREN;
import static io.github.toandv.wci.intermediate.icode.impl.ICodeKeyImpl.ID;
import static io.github.toandv.wci.intermediate.icode.impl.ICodeKeyImpl.VALUE;
import static io.github.toandv.wci.intermediate.icode.impl.ICodeNodeTypeImpl.*;

/**
 * Created by toan on 5/27/16.
 */
public class FactorParser extends TermParser {

    protected ExpressionParser expressionParser;

    public FactorParser(Parser parent) {
        super(parent);
    }

    public FactorParser(Scanner scanner) {
        super(scanner);
    }

    @Override
    public ICodeNode parse(Token token) throws Exception {
        ICodeNode rootNode = null;
        switch ((PascalTokenType) token.getType()) {
            case IDENTIFIER:
                // look up the identifier in the symbol table.
                String name = token.getText().toLowerCase();
                SymTabEntry id = symTabStack.lookup(name);
                if (id == null) {
                    errorHandler.flag(token, IDENTIFIER_UNDEFINED, this);
                    id = symTabStack.enterLocal(name);
                }
                rootNode = ICodeFactory.createICodeNode(VARIABLE);
                rootNode.setAttribute(ID, id);
                id.appendLineNumber(token.getLineNumber());
                nextToken();
                break;
            case INTEGER:
                // Create an INTEGER_CONSTANT node as the root.
                rootNode = ICodeFactory.createICodeNode(INTEGER_CONSTANT);
                rootNode.setAttribute(VALUE, token.getValue());
                nextToken();
                break;
            case REAL:
                // Create an REAL_CONSTANT node as the root.
                rootNode = ICodeFactory.createICodeNode(REAL_CONSTANT);
                rootNode.setAttribute(VALUE, token.getValue());
                nextToken();
                break;
            case STRING:
                // Create an STRING_CONSTANT node as the root.
                rootNode = ICodeFactory.createICodeNode(STRING_CONSTANT);
                rootNode.setAttribute(VALUE, token.getValue());
                nextToken();
                break;
            case NOT:
                token = nextToken(); // Consume the NOT token.

                // Create an NOT node as the root.
                rootNode = ICodeFactory.createICodeNode(NOT);
                // Parse the child factor and add to the root
                rootNode.addChild(parse(token));
                break;
            case LEFT_PAREN:
                token = nextToken(); // Consume the (.
                // Parse an expression and make it the root.
                if (expressionParser == null) {
                    expressionParser = new ExpressionParser(this);
                }
                rootNode = expressionParser.parse(token);
                // Look for the matching ) token
                token = currentToken();
                if (token.getType() == RIGHT_PAREN) {
                    nextToken(); // Consume the matching ) token.
                } else {
                    errorHandler.flag(token, MISSING_RIGHT_PAREN, this);
                }
                break;
            default:
                errorHandler.flag(token, UNEXPECTED_TOKEN, this);
                break;
        }
        return rootNode;
    }
}
