package net.test;

import java.util.List;

public class SomeClazz implements SomeInterface{
    @Override
    public void doSomething(List<String> withSomeArgs) {
        System.out.println(withSomeArgs);
    }


}
