package io.github.toandv.wci.frontend.pascal.parsers;

import io.github.toandv.wci.intermediate.icode.ICodeNode;
import io.github.toandv.wci.intermediate.icode.impl.ICodeKeyImpl;
import io.github.toandv.wci.intermediate.icode.impl.ICodeNodeImpl;
import io.github.toandv.wci.intermediate.icode.impl.ICodeNodeTypeImpl;
import io.github.toandv.wci.intermediate.symtab.SymTabEntry;
import io.github.toandv.wci.intermediate.symtab.impl.SymTabEntryImpl;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by toan on 5/27/16.
 */
public class FactorParserTest extends BaseParserTest {

    FactorParser factorParser;

    @Test
    public void parseVariableFactor() throws Exception {
        initContent("a");
        factorParser = new FactorParser(scanner);
        parser.getSymTabStack().enterLocal("a");
        ICodeNode varNode = factorParser.parse(scanner.nextToken());
        Assert.assertEquals(varNode.getChildren(), new ArrayList<>());
        Assert.assertEquals(varNode.getType(), ICodeNodeTypeImpl.VARIABLE);
        Assert.assertEquals(((SymTabEntry) varNode.getAttribute(ICodeKeyImpl.ID)).getName(), "a");
        Assert.assertNull(varNode.getParent());
    }

    @Test
    public void parseStringFactor() throws Exception {
        initContent("'this is a string'");
        factorParser = new FactorParser(scanner);
        ICodeNode stringNode = factorParser.parse(scanner.nextToken());
        Assert.assertEquals(stringNode.getType(), ICodeNodeTypeImpl.STRING_CONSTANT);
        Assert.assertEquals((stringNode.getAttribute(ICodeKeyImpl.VALUE)), "this is a string");
    }

    @Test
    public void parseIntegerFactor() throws Exception {
        initContent("123");
        factorParser = new FactorParser(scanner);
        ICodeNode stringNode = factorParser.parse(scanner.nextToken());
        Assert.assertEquals(stringNode.getType(), ICodeNodeTypeImpl.INTEGER_CONSTANT);
        Assert.assertEquals(stringNode.getAttribute(ICodeKeyImpl.VALUE), 123);
    }

    @Test
    public void parseFloatFactor() throws Exception {
        initContent("123.01");
        factorParser = new FactorParser(scanner);
        ICodeNode floatNode = factorParser.parse(scanner.nextToken());
        Assert.assertEquals(floatNode.getType(), ICodeNodeTypeImpl.REAL_CONSTANT);
        Assert.assertEquals((Double) floatNode.getAttribute(ICodeKeyImpl.VALUE), 123.01, 0.005);
    }

    @Test
    public void parseExponentFactor() throws Exception {
        initContent("10e-5");
        factorParser = new FactorParser(scanner);
        ICodeNode exponentNode = factorParser.parse(scanner.nextToken());
        Assert.assertEquals(exponentNode.getType(), ICodeNodeTypeImpl.REAL_CONSTANT);
        Assert.assertEquals(exponentNode.getAttribute(ICodeKeyImpl.VALUE), 10e-5);
    }

    @Test
    public void parseExpressionFactor() throws Exception {
        initContent("(1 + 3.2 * 2 - 5 / 3 + (1 - 2) / 5)");
        factorParser = new FactorParser(scanner);
        ICodeNode expressionNode = factorParser.parse(scanner.nextToken());
        Assert.assertEquals(expressionNode.getType(), ICodeNodeTypeImpl.ADD);

        // TODO - think of a way to assert the node
    }
}
