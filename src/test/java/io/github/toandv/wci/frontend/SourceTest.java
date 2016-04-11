package io.github.toandv.wci.frontend;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.junit.After;
import org.junit.Assert;
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
        Assert.assertEquals('A', source.currentChar());
        Assert.assertEquals('A', source.currentChar());
        Assert.assertEquals('B', source.peekChar());
        Assert.assertEquals('B', source.nextChar());
        Assert.assertEquals('C', source.nextChar());
        Assert.assertEquals('D', source.nextChar());
        Assert.assertEquals(Source.EOL, source.nextChar());
        Assert.assertEquals(Source.EOF, source.nextChar());
    }

    @After
    public void clean() throws Exception {
        source.close();
    }
}
