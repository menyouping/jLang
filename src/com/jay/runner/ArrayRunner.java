package com.jay.runner;

import javassist.gluonj.util.Loader;

import com.jay.interpreter.array.ArrayEvaluator;
import com.jay.interpreter.claze.ClassEvaluator;
import com.jay.interpreter.claze.ClassInterpreter;
import com.jay.interpreter.lambda.LambdaEvaluator;
import com.jay.interpreter.natives.NativeEvaluator;

public class ArrayRunner {
    public static void main(String[] args) throws Throwable {
        Loader.run(ClassInterpreter.class, args, ClassEvaluator.class, ArrayEvaluator.class, NativeEvaluator.class,
                LambdaEvaluator.class);
    }
}