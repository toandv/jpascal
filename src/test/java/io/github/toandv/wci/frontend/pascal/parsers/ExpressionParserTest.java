package io.github.toandv.wci.frontend.pascal.parsers;

import io.github.toandv.wci.intermediate.icode.ICodeNode;
import io.github.toandv.wci.intermediate.icode.impl.ICodeNodeImpl;
import io.github.toandv.wci.utils.XMLParseTreePrinter;
import org.junit.Test;

/**
 * Created by toan on 5/24/16.
 */
public class ExpressionParserTest extends BaseParserTest {

    ExpressionParser expressionParser;

    @Test
    public void testParseSimpleExpression() throws Exception {
        init("/parsers/expressions_01");
        parser.getSymTabStack().enterLocal("a");
        parser.getSymTabStack().enterLocal("b");
        expressionParser = new ExpressionParser(parser);
        ICodeNode expressionNode = expressionParser.parse(scanner.nextToken());
        XMLParseTreePrinter ps = new XMLParseTreePrinter(System.out);
        ps.printNode((ICodeNodeImpl) expressionNode);
    }
}
