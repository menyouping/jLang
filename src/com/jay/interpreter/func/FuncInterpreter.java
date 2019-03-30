package com.jay.interpreter.func;

import com.jay.exception.ParseException;
import com.jay.interpreter.basic.BasicInterpreter;
import com.jay.parser.FuncParser;

public class FuncInterpreter extends BasicInterpreter {
    public static void main(String[] args) throws ParseException {
        run(new FuncParser(), new NestedEnv());
    }
}