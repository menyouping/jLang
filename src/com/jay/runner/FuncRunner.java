package com.jay.runner;

import com.jay.interpreter.func.FuncEvaluator;
import com.jay.interpreter.func.FuncInterpreter;

import javassist.gluonj.util.Loader;

public class FuncRunner {
    public static void main(String[] args) throws Throwable {
        Loader.run(FuncInterpreter.class, args, FuncEvaluator.class);
    }
}