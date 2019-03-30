package com.jay.interpreter.claze;

import com.jay.exception.ParseException;
import com.jay.interpreter.basic.BasicInterpreter;
import com.jay.interpreter.func.NestedEnv;
import com.jay.interpreter.natives.Natives;
import com.jay.parser.ClassParser;

public class ClassInterpreter extends BasicInterpreter {
    public static void main(String[] args) throws ParseException {
        NestedEnv env = new NestedEnv();
        if (args != null && args.length > 0) {
            for (String arg : args) {
                if (!arg.startsWith("-") || !arg.contains("=")) {
                    continue;
                }
                String[] arr = arg.substring(1).split("\\s*=\\s*", 2);
                if (arr.length == 2) {
                    env.put(arr[0], arr[1]);
                }
            }
        }
        run(new ClassParser(), new Natives().environment(env));
    }
}