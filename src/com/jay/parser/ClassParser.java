package com.jay.parser;
import static com.jay.parser.Parser.rule;

import com.jay.ast.ClassBody;
import com.jay.ast.ClassStmnt;
import com.jay.ast.Dot;
import com.jay.lexer.Token;

public class ClassParser extends LambdaParser {
    Parser member = rule().or(func, simple);
    Parser class_body = rule(ClassBody.class).sep("{").option(member)
                            .repeat(rule().sep(";", Token.EOL).option(member))
                            .sep("}");
    Parser defclass = rule(ClassStmnt.class).sep("class").identifier(reserved)
                          .option(rule().sep("extends").identifier(reserved))
                          .ast(class_body);
    public ClassParser() {
        postfix.insertChoice(rule(Dot.class).sep(".").identifier(reserved));
        program.insertChoice(defclass);
    }
}