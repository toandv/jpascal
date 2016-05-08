package io.github.toandv.wci.frontend.pascal.parsers;

import io.github.toandv.wci.frontend.Scanner;
import io.github.toandv.wci.frontend.Token;
import io.github.toandv.wci.frontend.pascal.PascalErrorCode;
import io.github.toandv.wci.frontend.pascal.PascalTokenType;
import io.github.toandv.wci.intermediate.icode.ICodeFactory;
import io.github.toandv.wci.intermediate.icode.ICodeNode;
import io.github.toandv.wci.intermediate.icode.impl.ICodeNodeTypeImpl;

/**
 * Created by toan on 5/8/16.
 */
public class CompoundStatementParser extends StatementParser {

    public CompoundStatementParser(Scanner scanner) {
        super(scanner);
    }

    public CompoundStatementParser(StatementParser statementParser) {
        super(statementParser);
    }

    @Override
    public ICodeNode parse(Token token) throws Exception {
        token = nextToken(); // consume "BEGIN"
        ICodeNode compoundStatementNode = ICodeFactory.createICodeNode(ICodeNodeTypeImpl.COMPOUND);
        pareList(token, compoundStatementNode, PascalTokenType.END, PascalErrorCode.MISSING_END);
        return compoundStatementNode;
    }
}
