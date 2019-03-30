package com.jay.interpreter.natives;

import com.jay.exception.ParseException;
import com.jay.interpreter.basic.BasicInterpreter;
import com.jay.interpreter.func.NestedEnv;
import com.jay.parser.LambdaParser;

public class NativeInterpreter extends BasicInterpreter {
    public static void main(String[] args) throws ParseException {
        run(new LambdaParser(), new Natives().environment(new NestedEnv()));
    }
}