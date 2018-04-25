package yogur.tree.declaration;

import yogur.tree.Type;
import yogur.tree.expression.identifier.BaseIdentifier;

public class Argument {
	private BaseIdentifier identifier;
	private Type type;

	public Argument(String identifier, Type type) {
		this(new BaseIdentifier(identifier), type);
	}

	public Argument(BaseIdentifier identifier, Type type) {
		this.identifier = identifier;
		this.type = type;
	}
}
