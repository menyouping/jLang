package com.jay.runner;
import com.jay.interpreter.basic.BasicEvaluator;
import com.jay.interpreter.basic.BasicInterpreter;

import javassist.gluonj.util.Loader;

public class BasicRunner {
    public static void main(String[] args) throws Throwable {
        Loader.run(BasicInterpreter.class, args, BasicEvaluator.class);
    }
}