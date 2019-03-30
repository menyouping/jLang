package com.jay.runner;

import com.jay.interpreter.claze.ClassEvaluator;
import com.jay.interpreter.claze.ClassInterpreter;
import com.jay.interpreter.lambda.LambdaEvaluator;
import com.jay.interpreter.natives.NativeEvaluator;

import javassist.gluonj.util.Loader;

public class ClassRunner {
    public static void main(String[] args) throws Throwable {
        Loader.run(ClassInterpreter.class, args, ClassEvaluator.class, NativeEvaluator.class, LambdaEvaluator.class);
    }
}