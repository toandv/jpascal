package io.github.toandv.wci.frontend.pascal.parsers;

import io.github.toandv.wci.frontend.EofToken;
import io.github.toandv.wci.frontend.Scanner;
import io.github.toandv.wci.frontend.Token;
import io.github.toandv.wci.frontend.pascal.PascalErrorCode;
import io.github.toandv.wci.frontend.pascal.PascalParserTD;
import io.github.toandv.wci.frontend.pascal.PascalTokenType;
import io.github.toandv.wci.intermediate.icode.ICodeFactory;
import io.github.toandv.wci.intermediate.icode.ICodeNode;
import io.github.toandv.wci.intermediate.icode.impl.ICodeKeyImpl;
import io.github.toandv.wci.intermediate.icode.impl.ICodeNodeTypeImpl;

/**
 * Created by toan on 5/8/16.
 */
public class StatementParser extends PascalParserTD {

    public StatementParser(Scanner scanner) {
        super(scanner);
    }

    public StatementParser(PascalParserTD parent) {
        super(parent);
    }

    public ICodeNode parse(Token token) throws Exception {
        ICodeNode statementNode = null;
        switch ((PascalTokenType) token.getType()) {
            case BEGIN:
                CompoundStatementParser compoundStatementParser = new CompoundStatementParser(this);
                statementNode = compoundStatementParser.parse(token);
                break;
            case IDENTIFIER:
                AssignmentStatementParser assignmentStatementParser = new AssignmentStatementParser(this);
                statementNode = assignmentStatementParser.parse(token);
                break;
            default:
                statementNode = ICodeFactory.createICodeNode(ICodeNodeTypeImpl.NO_OP);
                break;
        }
        setLineNumber(statementNode, token);
        return statementNode;
    }

    protected void pareList(Token token, ICodeNode parentNode, PascalTokenType terminator, PascalErrorCode errorCode) throws Exception {
        while (token.getType() != terminator && !(token instanceof EofToken)) {
            // parse child and add to parent
            ICodeNode statementToken = parse(token);
            parentNode.addChild(statementToken);

            token = currentToken();

            // look for semicolon between statements.
            if (token.getType() == PascalTokenType.SEMICOLON) {
                token = nextToken(); // consume semicolon
            }
            // missing semicolon
            else if (token.getType() == PascalTokenType.IDENTIFIER) {
                errorHandler.flag(token, PascalErrorCode.MISSING_SEMICOLON, this);
            }
            // unexpected token
            else if (token.getType() != terminator) {
                errorHandler.flag(token, PascalErrorCode.UNEXPECTED_TOKEN, this);
                token = nextToken(); // consume
            }

            // look for terminator
            if (token.getType() == terminator) {
                token = nextToken(); // consume
            }
            else {
                errorHandler.flag(token, errorCode, this);
            }
        }

    }

    protected void setLineNumber(ICodeNode node, Token token) {
        if (node != null) {
            node.setAttribute(ICodeKeyImpl.LINE, token.getLineNumber());
        }
    }

}
