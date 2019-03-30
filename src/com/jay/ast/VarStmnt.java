package com.jay.ast;
import java.util.List;

public class VarStmnt extends AstList {
	public VarStmnt(List<AstNode> c) {
		super(c);
	}

	public String name() {
		return ((AstLeaf) childAt(0)).token().getText();
	}

	public TypeTag type() {
		return (TypeTag) childAt(1);
	}

	public AstNode initializer() {
		return childAt(2);
	}

	public String toString() {
		return "(var " + name() + " " + type() + " " + initializer() + ")";
	}
}