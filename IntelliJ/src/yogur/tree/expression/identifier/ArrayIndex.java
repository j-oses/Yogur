package yogur.tree.expression.identifier;

import yogur.codegen.IntegerReference;
import yogur.utils.CompilationException;
import yogur.ididentification.IdentifierTable;
import yogur.tree.AbstractTreeNode;
import yogur.tree.expression.Expression;
import yogur.tree.type.BaseType;
import yogur.typeidentification.MetaType;

import static yogur.utils.CompilationException.Scope.TypeAnalyzer;
import static yogur.tree.expression.identifier.ArrayIndex.AccessType.LEFT_RIGHT_RANGE;
import static yogur.tree.type.BaseType.PredefinedType.Int;

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

	public Expression getOffset() {
		return offset;
	}

	public boolean returnsSingleElement() {
		return AccessType.INDEX.equals(accessType);
	}

	@Override
	public void performIdentifierAnalysis(IdentifierTable table) throws CompilationException {
		offset.performIdentifierAnalysis(table);
		if (offset2 != null) {
			offset2.performIdentifierAnalysis(table);
		}
	}

	@Override
	public MetaType analyzeType(IdentifierTable idTable) throws CompilationException {
		MetaType offsetType = offset.performTypeAnalysis(idTable);
		MetaType intType = new BaseType(Int);

		if (intType.equals(offsetType)) {
			throw new CompilationException("Invalid subscript, with type: " + offsetType, offset.getLine(),
					offset.getColumn(), TypeAnalyzer);
		}

		if (LEFT_RIGHT_RANGE.equals(accessType)) {
			MetaType offsetType2 = offset2.performTypeAnalysis(idTable);
			throw new CompilationException("Invalid subscript, with type: " + offsetType2, offset2.getLine(),
					offset2.getColumn(), TypeAnalyzer);
		}

		return null;
	}

	@Override
	public void performMemoryAssignment(IntegerReference currentOffset) {
		// Do nothing
	}
}
