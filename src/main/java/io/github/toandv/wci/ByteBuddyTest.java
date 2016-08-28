package io.github.toandv.wci;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.NamingStrategy;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.FixedValue;

import java.io.File;
import java.io.IOException;

import static net.bytebuddy.matcher.ElementMatchers.named;

/**
 * @author toandv
 * @github https://github.com/toandv
 * @since 8/28/2016.
 */
public class ByteBuddyTest {
    public static void main(String[] args) throws IOException, IllegalAccessException, InstantiationException {
        DynamicType.Unloaded<?> dynamicType = new ByteBuddy()
                .with(new NamingStrategy.AbstractBase() {
                    @Override
                    protected String name(TypeDescription superClass) {
                        return "i.love.ByteBuddy." + superClass.getSimpleName();
                    }
                })
                .subclass(Object.class)
                .make();
        dynamicType.saveIn(new File("."));



        Greeting toString = new ByteBuddy()
                .subclass(Greeting.class)
                .name("example.Greeting")
                .method(named("hi")).intercept(FixedValue.value("Hello World!"))
                .make()
                .load(ByteBuddyTest.class.getClassLoader())
                .getLoaded()
                .newInstance();
        System.out.println(toString.hi());


    }
    public static interface Greeting {
        public  String hi();
    }
}
