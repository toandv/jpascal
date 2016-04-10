package io.github.toandv.wci.frontend;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SourceTest {

    Source source;

    @Before
    public void setup() {
        InputStream resourceAsStream = SourceTest.class.getResourceAsStream("/source.pas");
        source = new Source(new BufferedReader(new InputStreamReader(resourceAsStream)));
    }

    @Test
    public void testCurrentChar() throws Exception {
        System.out.println((char) source.currentChar());
        System.out.println((char) source.nextChar());
        System.out.println((char) source.nextChar());
        System.out.println((char) source.nextChar());
        System.out.println((char) source.nextChar());
        System.out.println((char) source.nextChar());
        System.out.println((char) source.nextChar());
    }

    @After
    public void clean() throws Exception {
        source.close();
    }
}
