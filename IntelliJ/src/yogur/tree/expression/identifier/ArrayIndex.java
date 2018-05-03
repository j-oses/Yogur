package yogur.tree.expression.identifier;

import yogur.error.CompilationException;
import yogur.ididentification.IdIdentifier;
import yogur.tree.AbstractTreeNode;
import yogur.tree.expression.Expression;
import yogur.tree.type.BaseType;
import yogur.typeidentification.MetaType;

public class ArrayIndex extends AbstractTreeNode {
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

	public boolean returnsSingleElement() {
		return AccessType.INDEX.equals(accessType);
	}

	@Override
	public void performIdentifierAnalysis(IdIdentifier table) throws CompilationException {
		offset.performIdentifierAnalysis(table);
		if (offset2 != null) {
			offset2.performIdentifierAnalysis(table);
		}
	}

	@Override
	public MetaType performTypeAnalysis(IdIdentifier idTable) throws CompilationException {
		MetaType offsetType = offset.performTypeAnalysis(idTable);
		MetaType intType = new BaseType(BaseType.PredefinedType.Int);

		if (intType.equals(offsetType)) {
			throw new CompilationException("Invalid subscript, with type: " + offsetType, offset.getLine(),
					offset.getColumn(), CompilationException.Scope.TypeAnalyzer);
		}

		if (AccessType.LEFT_RIGHT_RANGE.equals(accessType)) {
			MetaType offsetType2 = offset2.performTypeAnalysis(idTable);
			throw new CompilationException("Invalid subscript, with type: " + offsetType, offset2.getLine(),
					offset2.getColumn(), CompilationException.Scope.TypeAnalyzer);
		}

		return null;
	}
}
