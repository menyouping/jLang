package com.jay.parser;
import static com.jay.parser.Parser.rule;

import com.jay.ast.*;

public class TypedParser extends FuncParser {
	Parser typeTag = rule(TypeTag.class).sep(":").identifier(reserved);
	Parser variable = rule(VarStmnt.class).sep("var").identifier(reserved).maybe(typeTag).sep("=").ast(expr);

	public TypedParser() {
		reserved.add(":");
		param.maybe(typeTag);
		func.reset().sep("def").identifier(reserved).ast(paramList).maybe(typeTag).ast(block);
		statement.insertChoice(variable);
	}
}