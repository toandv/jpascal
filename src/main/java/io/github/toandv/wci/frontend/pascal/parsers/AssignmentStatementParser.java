package io.github.toandv.wci.frontend.pascal.parsers;

import io.github.toandv.wci.frontend.Scanner;
import io.github.toandv.wci.frontend.Token;
import io.github.toandv.wci.frontend.pascal.PascalErrorCode;
import io.github.toandv.wci.frontend.pascal.PascalTokenType;
import io.github.toandv.wci.intermediate.icode.ICodeFactory;
import io.github.toandv.wci.intermediate.icode.ICodeNode;
import io.github.toandv.wci.intermediate.icode.impl.ICodeKeyImpl;
import io.github.toandv.wci.intermediate.icode.impl.ICodeNodeTypeImpl;
import io.github.toandv.wci.intermediate.symtab.SymTabEntry;

/**
 * Created by toan on 5/8/16.
 */
public class AssignmentStatementParser extends StatementParser {

    public AssignmentStatementParser(Scanner scanner) {
        super(scanner);
    }

    public AssignmentStatementParser(StatementParser statementParser) {
        super(statementParser);
    }

    @Override
    public ICodeNode parse(Token token) throws Exception {
        ICodeNode assignNode = ICodeFactory.createICodeNode(ICodeNodeTypeImpl.ASSIGN);

        // look up identifier from SymTabStack
        String targetName = token.getText().toString();
        SymTabEntry targetId = symTabStack.lookup(targetName);
        if (targetId == null) {
            // not existing, enter new id
            targetId = symTabStack.enterLocal(targetName);
        }
        targetId.appendLineNumber(token.getLineNumber());

        token = nextToken(); // consume the identifier token, variable name

        // create a var node, and set its name

        ICodeNode variableNode = ICodeFactory.createICodeNode(ICodeNodeTypeImpl.VARIABLE);
        variableNode.setAttribute(ICodeKeyImpl.ID, targetId);

        assignNode.addChild(variableNode);

        if (token.getType() == PascalTokenType.COLON_EQUALS) {
            token = nextToken(); // consume :=
        }
        else {
            errorHandler.flag(token, PascalErrorCode.MISSING_COLON_EQUALS, this);
        }

        // parse the expression
        ExpressionParser  expressionParser = new ExpressionParser(this);
        ICodeNode expresisonNode = expressionParser.parse(token);
        assignNode.addChild(expresisonNode);

        return assignNode;
    }
}
