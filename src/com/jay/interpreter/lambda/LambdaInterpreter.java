package com.jay.interpreter.lambda;

import com.jay.exception.ParseException;
import com.jay.interpreter.basic.BasicInterpreter;
import com.jay.interpreter.func.NestedEnv;
import com.jay.parser.LambdaParser;

public class LambdaInterpreter extends BasicInterpreter {
    public static void main(String[] args) throws ParseException {
        run(new LambdaParser(), new NestedEnv());
    }
}