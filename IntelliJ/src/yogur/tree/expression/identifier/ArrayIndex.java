package yogur.tree.expression.identifier;

import yogur.tree.expression.Expression;

public class ArrayIndex {
	public enum AccessType {
		INDEX, LEFT_RANGE, RIGHT_RANGE, LEFT_RIGHT_RANGE
	}

	private Expression offset;
	private Expression offset2 = null;
	private AccessType accessType;

	public ArrayIndex(Expression offset, AccessType accessType) {
		this.offset = offset;
		this.accessType = accessType;
	}

	public ArrayIndex(Expression offset, Expression offset2, AccessType accessType) {
		this.offset = offset;
		this.offset2 = offset2;
		this.accessType = accessType;
	}
}
