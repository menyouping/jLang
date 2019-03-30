package com.jay.runner;

import com.jay.interpreter.lambda.LambdaEvaluator;
import com.jay.interpreter.lambda.LambdaInterpreter;

import javassist.gluonj.util.Loader;

public class LambdaRunner {
    public static void main(String[] args) throws Throwable {
        Loader.run(LambdaInterpreter.class, args, LambdaEvaluator.class);
    }
}