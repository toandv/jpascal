package io.github.toandv.wci.backend.interpreter;

import io.github.toandv.wci.backend.intepreter.executors.ExpressionExecutor;
import io.github.toandv.wci.frontend.pascal.BaseTest;
import io.github.toandv.wci.frontend.pascal.parsers.ExpressionParser;
import io.github.toandv.wci.intermediate.icode.ICodeNode;
import org.junit.Test;

/**
 * Created by toan on 5/24/16.
 */
public class ExpressionExecutorTest extends BaseTest {

    @Test
    public void testEvalArithmaticOperation() throws Exception {
        initContent("3 + 3/4 + (1.6 + 2)"); // 6.6
        ExpressionParser parser = new ExpressionParser(scanner);
        ICodeNode expressionNode = parser.parse(scanner.nextToken());
        printAST(expressionNode);
        ExpressionExecutor expressionExecutor = new ExpressionExecutor(parser.getSymTab(), parser.getICode());
        Object value = expressionExecutor.execute(expressionNode);
    }
}
