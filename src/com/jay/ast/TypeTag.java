package com.jay.ast;
import java.util.List;

public class TypeTag extends AstList {
	public static final String UNDEF = "<Undef>";

	public TypeTag(List<AstNode> c) {
		super(c);
	}

	public String type() {
		if (numChildren() > 0)
			return ((AstLeaf) childAt(0)).token().getText();
		else
			return UNDEF;
	}

	public String toString() {
		return ":" + type();
	}
}