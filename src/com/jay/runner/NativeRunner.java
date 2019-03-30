package com.jay.runner;

import com.jay.interpreter.lambda.LambdaEvaluator;
import com.jay.interpreter.natives.NativeEvaluator;
import com.jay.interpreter.natives.NativeInterpreter;

import javassist.gluonj.util.Loader;

public class NativeRunner {
    public static void main(String[] args) throws Throwable {
        Loader.run(NativeInterpreter.class, args, NativeEvaluator.class, LambdaEvaluator.class);
    }
}