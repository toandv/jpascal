package io.github.toandv.wci.frontend.pascal.tokens;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.junit.After;
import org.junit.Test;

import io.github.toandv.wci.frontend.Parser;
import io.github.toandv.wci.frontend.Scanner;
import io.github.toandv.wci.frontend.Source;
import io.github.toandv.wci.frontend.SourceTest;
import io.github.toandv.wci.frontend.Token;
import io.github.toandv.wci.frontend.pascal.PascalParserTD;
import io.github.toandv.wci.frontend.pascal.PascalScanner;

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
