package yogur.tree.declaration;

import yogur.tree.Type;
import yogur.tree.declaration.declarator.BaseDeclarator;
import yogur.tree.expression.identifier.BaseIdentifier;

public class Argument {
	private BaseDeclarator declarator;
	private Type type;

	public Argument(String declarator, Type type) {
		this(new BaseDeclarator(declarator), type);
	}

	public Argument(BaseDeclarator declarator, Type type) {
		this.declarator = declarator;
		this.type = type;
	}
}
