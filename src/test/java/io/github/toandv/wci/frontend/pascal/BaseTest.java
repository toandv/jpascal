package io.github.toandv.wci.frontend.pascal;

import io.github.toandv.wci.frontend.*;
import io.github.toandv.wci.intermediate.icode.ICodeNode;
import io.github.toandv.wci.intermediate.icode.impl.ICodeNodeImpl;
import io.github.toandv.wci.utils.XMLParseTreePrinter;
import org.junit.After;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

public abstract class BaseTest {

    protected Scanner scanner;

    protected Source source;

    protected Parser parser;

    public void init(String file) {
        InputStream resourceAsStream = SourceTest.class.getResourceAsStream(file);
        source = new Source(new BufferedReader(new InputStreamReader(resourceAsStream)));
        scanner = new PascalScanner(source);
        parser = new PascalParserTD(scanner);
    }

    public void initContent(String content) {
        ByteArrayInputStream resourceAsStream = new ByteArrayInputStream(content.getBytes());
        source = new Source(new BufferedReader(new InputStreamReader(resourceAsStream)));
        scanner = new PascalScanner(source);
        parser = new PascalParserTD(scanner);
    }

    @After
    public void clean() throws Exception {
        source.close();
    }

    public void printAST(ICodeNode iCodeNode) {
        XMLParseTreePrinter ps = new XMLParseTreePrinter(System.out);
        ps.printNode((ICodeNodeImpl) iCodeNode);
    }
}
