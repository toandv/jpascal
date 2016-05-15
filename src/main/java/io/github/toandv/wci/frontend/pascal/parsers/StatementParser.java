package io.github.toandv.wci.frontend.pascal.parsers;

import io.github.toandv.wci.frontend.EofToken;
import io.github.toandv.wci.frontend.Scanner;
import io.github.toandv.wci.frontend.Token;
import io.github.toandv.wci.frontend.pascal.PascalErrorCode;
import io.github.toandv.wci.frontend.pascal.PascalParserTD;
import io.github.toandv.wci.frontend.pascal.PascalTokenType;
import io.github.toandv.wci.intermediate.icode.ICodeFactory;
import io.github.toandv.wci.intermediate.icode.ICodeNode;
import io.github.toandv.wci.intermediate.icode.impl.ICodeNodeTypeImpl;

import static io.github.toandv.wci.frontend.pascal.PascalErrorCode.MISSING_SEMICOLON;
import static io.github.toandv.wci.frontend.pascal.PascalErrorCode.UNEXPECTED_TOKEN;
import static io.github.toandv.wci.frontend.pascal.PascalTokenType.IDENTIFIER;
import static io.github.toandv.wci.frontend.pascal.PascalTokenType.SEMICOLON;
import static io.github.toandv.wci.intermediate.icode.impl.ICodeKeyImpl.LINE;

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
        ICodeNode statementNode;
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

    protected void pareList(Token token, ICodeNode parentNode, PascalTokenType terminator, PascalErrorCode errorCode)
            throws Exception {
        while (token.getType() != terminator && !(token instanceof EofToken)) {
            // Parse child and add to parent.
            ICodeNode statementToken = parse(token);
            parentNode.addChild(statementToken);
            token = currentToken();

            // Look for semicolon between statements.
            if (token.getType() == SEMICOLON) {
                token = nextToken(); // Consume semicolon.
            }
            // Missing semicolon.
            else if (token.getType() == IDENTIFIER) {
                errorHandler.flag(token, MISSING_SEMICOLON, this);
            }
            // Unexpected token.
            else if (token.getType() != terminator) {
                errorHandler.flag(token, UNEXPECTED_TOKEN, this);
                token = nextToken(); // Consume.
            }
            // Look for the terminator.
        }
        if (token.getType() == terminator) {
            nextToken(); // Consume.
        } else {
            errorHandler.flag(token, errorCode, this);
        }
    }

    protected void setLineNumber(ICodeNode node, Token token) {
        if (node != null) {
            node.setAttribute(LINE, token.getLineNumber());
        }
    }

}
