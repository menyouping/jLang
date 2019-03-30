package com.jay.interpreter.claze;
import com.jay.ast.ClassBody;
import com.jay.ast.ClassStmnt;
import com.jay.exception.StoneException;
import com.jay.interpreter.basic.Environment;

public class ClassInfo {
	protected ClassStmnt definition;
	protected Environment environment;
	protected ClassInfo superClass;

	public ClassInfo(ClassStmnt cs, Environment env) {
		definition = cs;
		environment = env;
		Object obj = env.get(cs.superClass());
		if (obj == null)
			superClass = null;
		else if (obj instanceof ClassInfo)
			superClass = (ClassInfo) obj;
		else
			throw new StoneException("unkonw super class: " + cs.superClass(), cs);
	}
	
	public String name() {
		return definition.name();
	}
	
	public ClassInfo superClass() {
		return superClass;
	}
	
	public ClassBody body() {
		return definition.body();
	}
	
	public Environment environment() {
		return environment;
	}
	
	public String toString() {
		return "<class " + name() + ">";
	}
}