package io.github.toandv.wci.frontend.pascal.parsers;

import io.github.toandv.wci.intermediate.icode.ICodeNode;
import io.github.toandv.wci.intermediate.icode.impl.ICodeNodeImpl;
import io.github.toandv.wci.utils.XMLParseTreePrinter;
import org.junit.Test;

/**
 * Created by toan on 5/24/16.
 */
public class StatementParserTest extends BaseParserTest {

    StatementParser statementParser;

    @Test
    public void testParseStatement() throws Exception {
        initContent(
                "BEGIN \n" +
                "   x := 1; \n" +
                "   y := x + 2 / 4; \n" +
                "   z := x + y; \n"     +
                "END");

        statementParser = new StatementParser(parser);

        ICodeNode root = statementParser.parse(scanner.nextToken());

        XMLParseTreePrinter ps = new XMLParseTreePrinter(System.out);
        ps.printNode((ICodeNodeImpl) root);

    }
}
