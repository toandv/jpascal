package io.github.toandv.wci.frontend.pascal.tokens;

import io.github.toandv.wci.frontend.*;
import io.github.toandv.wci.frontend.pascal.PascalParserTD;
import io.github.toandv.wci.frontend.pascal.PascalScanner;
import org.junit.After;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public abstract class TokenBaseTest {
    Scanner scanner;

    Source source;

    Parser parser;

    public void init(String file) {
        InputStream resourceAsStream = SourceTest.class.getResourceAsStream(file);
        source = new Source(new BufferedReader(new InputStreamReader(resourceAsStream)));
        scanner = new PascalScanner(source);
        parser = new PascalParserTD(scanner);
    }

    @Test
    public abstract void extractToken() throws Exception;

    @After
    public void clean() throws Exception {
        source.close();
    }

    public Token nextToken() throws Exception {
        return parser.nextToken();
    }

}
