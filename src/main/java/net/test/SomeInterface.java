package net.test;

import java.util.List;

public interface SomeInterface {

    void doSomething(List<String> withSomeArgs);

    default String doAnotherSomething() {
        return "String ";
    };
}
