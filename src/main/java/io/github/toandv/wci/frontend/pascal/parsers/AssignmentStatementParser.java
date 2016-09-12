package io.github.toandv.wci.frontend.pascal.parsers;

import io.github.toandv.wci.frontend.Parser;
import io.github.toandv.wci.frontend.Scanner;
import io.github.toandv.wci.frontend.Token;
import io.github.toandv.wci.intermediate.icode.ICodeFactory;
import io.github.toandv.wci.intermediate.icode.ICodeNode;
import io.github.toandv.wci.intermediate.symtab.SymTabEntry;

import static io.github.toandv.wci.frontend.pascal.PascalErrorCode.MISSING_COLON_EQUALS;
import static io.github.toandv.wci.frontend.pascal.PascalTokenType.COLON_EQUALS;
import static io.github.toandv.wci.intermediate.icode.impl.ICodeKeyImpl.ID;
import static io.github.toandv.wci.intermediate.icode.impl.ICodeNodeTypeImpl.ASSIGN;
import static io.github.toandv.wci.intermediate.icode.impl.ICodeNodeTypeImpl.VARIABLE;

/**
 * Created by toan on 5/8/16.
 */
public class AssignmentStatementParser extends StatementParser {

    public AssignmentStatementParser(Scanner scanner) {
        super(scanner);
    }

    public AssignmentStatementParser(Parser parent) {
        super(parent);
    }

    @Override
    public ICodeNode parse(Token token) throws Exception {
        ICodeNode assignNode = ICodeFactory.createICodeNode(ASSIGN);

        // Look up identifier from SymTabStack.
        String targetName = token.getText().toString();
        SymTabEntry variableId = symTabStack.lookup(targetName);
        if (variableId == null) {
            // If not existing, enter new id.
            variableId = symTabStack.enterLocal(targetName);
        }
        variableId.appendLineNumber(token.getLineNumber());

        token = nextToken(); // Consume the identifier token, variable name.

        // Create a var node, and set its name.
        ICodeNode variableNode = ICodeFactory.createICodeNode(VARIABLE);
        variableNode.setAttribute(ID, variableId);

        assignNode.addChild(variableNode);

        if (token.getType() == COLON_EQUALS) {
            token = nextToken(); // Consume := token.
        }
        else {
            errorHandler.flag(token, MISSING_COLON_EQUALS, this);
        }

        // Parse the expression.
        ExpressionParser expressionParser = new ExpressionParser(this);
        ICodeNode expressionNode = expressionParser.parse(token);
        assignNode.addChild(expressionNode);
        return assignNode;
    }
}
