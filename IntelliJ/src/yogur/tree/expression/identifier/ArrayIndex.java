package yogur.tree.expression.identifier;

import yogur.error.CompilationException;
import yogur.ididentification.IdIdentifier;
import yogur.tree.AbstractTreeNode;
import yogur.tree.expression.Expression;

public class ArrayIndex implements AbstractTreeNode {
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

	@Override
	public void performIdentifierAnalysis(IdIdentifier table) throws CompilationException {
		offset.performIdentifierAnalysis(table);
		if (offset2 != null) {
			offset2.performIdentifierAnalysis(table);
		}
	}
}
