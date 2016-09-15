package io.github.toandv.wci.backend.intepreter.executors.arithmetic;

import java.util.function.BinaryOperator;

/**
 * @author toandv
 * @github https://github.com/toandv
 * @since 9/15/2016.
 */
public class ArithmeticOperator {

    public static final BinaryOperator<Integer> I_ADD = (a, b) -> a + b;
    public static final BinaryOperator<Long> L_ADD = (a, b) -> a + b;
    public static final BinaryOperator<Double> D_ADD = (a, b) -> a + b;
    public static final BinaryOperator<Float> F_ADD = (a, b) -> a + b;

    public static final BinaryOperator<Integer> I_SUB = (a, b) -> a - b;
    public static final BinaryOperator<Long> L_SUB = (a, b) -> a - b;
    public static final BinaryOperator<Double> D_SUB = (a, b) -> a - b;
    public static final BinaryOperator<Float> F_SUB = (a, b) -> a - b;

    public static final BinaryOperator<Integer> I_MUL = (a, b) -> a * b;
    public static final BinaryOperator<Long> L_MUL = (a, b) -> a * b;
    public static final BinaryOperator<Double> D_MUL = (a, b) -> a * b;
    public static final BinaryOperator<Float> F_MUL = (a, b) -> a * b;

    public static final BinaryOperator<Integer> I_DIV = (a, b) -> a / (Integer) safeCheck(b);
    public static final BinaryOperator<Long> L_DIV = (a, b) -> a / (Long) safeCheck(b);
    public static final BinaryOperator<Double> D_DIV = (a, b) -> a / (Double) safeCheck(b);
    public static final BinaryOperator<Float> F_DIV = (a, b) -> a / (Float) safeCheck(b);

    static Number safeCheck(Number n) {
        if (n.intValue() == 0) {
            throw new IllegalArgumentException("Divisor is zero.");
        }
        return n;
    }
}
