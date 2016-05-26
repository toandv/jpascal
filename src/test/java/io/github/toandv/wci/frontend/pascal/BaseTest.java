package io.github.toandv.wci.frontend.pascal;

import io.github.toandv.wci.frontend.*;
import org.junit.After;

import java.io.BufferedReader;
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

    @After
    public void clean() throws Exception {
        source.close();
    }
}
