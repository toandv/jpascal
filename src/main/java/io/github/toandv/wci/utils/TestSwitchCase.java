package io.github.toandv.wci.utils;

import static io.github.toandv.wci.utils.TestSwitchCase.Type.A;

/**
 * Created by toan on 9/30/16.
 */
public class TestSwitchCase {

    enum Type {
        A,
        B,
        C

    }

    public static void main(String[] args) {
        Type t = A;

        switch (t) {
            case B:
                System.out.println("B");
                break;
            case C:
                System.out.println("c");
                break;
        }

        System.out.println("1");
    }
}
