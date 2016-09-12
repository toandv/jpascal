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
        //String content = "a + 1 + b - 2 * 2 / 100 + (2 - 1) / 5";
        String content = "( 1 ";
        for (int i = 0; i < 10000; i++) {
            content += " + " + i;
        }
        content += ")";
        initContent(content);
        parser.getSymTabStack().enterLocal("a");
        parser.getSymTabStack().enterLocal("b");
        expressionParser = new ExpressionParser(scanner);
        ICodeNode expressionNode = expressionParser.parse(scanner.nextToken());
      //  XMLParseTreePrinter ps = new XMLParseTreePrinter(System.out);
       // ps.printNode((ICodeNodeImpl) expressionNode);
    }
}
