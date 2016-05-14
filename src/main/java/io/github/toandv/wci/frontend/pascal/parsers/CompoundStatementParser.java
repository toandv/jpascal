package io.github.toandv.wci.frontend.pascal.parsers;

import static io.github.toandv.wci.frontend.pascal.PascalErrorCode.MISSING_END;
import static io.github.toandv.wci.frontend.pascal.PascalTokenType.END;
import static io.github.toandv.wci.intermediate.icode.impl.ICodeNodeTypeImpl.COMPOUND;

import io.github.toandv.wci.frontend.Scanner;
import io.github.toandv.wci.frontend.Token;
import io.github.toandv.wci.intermediate.icode.ICodeFactory;
import io.github.toandv.wci.intermediate.icode.ICodeNode;

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
        token = nextToken(); // Consume BEGIN token.
        ICodeNode compoundStatementNode = ICodeFactory.createICodeNode(COMPOUND);

        // Parse the statement list terminated by END.
        StatementParser statementParser = new StatementParser(this);
        statementParser.pareList(token, compoundStatementNode, END, MISSING_END);
        return compoundStatementNode;
    }
}
