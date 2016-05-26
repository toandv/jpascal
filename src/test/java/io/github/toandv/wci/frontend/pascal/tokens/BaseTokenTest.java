package io.github.toandv.wci.frontend.pascal.tokens;

import io.github.toandv.wci.frontend.Token;
import io.github.toandv.wci.frontend.pascal.BaseTest;
import org.junit.Test;

/**
 * Created by toan on 5/26/16.
 */
public abstract  class BaseTokenTest extends BaseTest {

    @Test
    public abstract void extractToken() throws Exception;

    public Token nextToken() throws Exception {
        return parser.nextToken();
    }
}
