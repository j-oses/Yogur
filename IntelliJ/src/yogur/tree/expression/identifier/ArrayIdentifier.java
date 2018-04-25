package yogur.tree.expression.identifier;

import yogur.tree.ArrayType;

public class ArrayIdentifier implements VarIdentifier {
	private VarIdentifier internalId;
	private ArrayIndex index;

	public ArrayIdentifier(VarIdentifier internalId, ArrayIndex index) {
		this.internalId = internalId;
		this.index = index;
	}
}
