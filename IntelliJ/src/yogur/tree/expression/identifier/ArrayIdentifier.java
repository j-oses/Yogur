package yogur.tree.expression.identifier;

import yogur.error.CompilationException;
import yogur.ididentification.IdIdentifier;
import yogur.tree.expression.Expression;
import yogur.tree.type.ArrayType;
import yogur.typeidentification.MetaType;

public class ArrayIdentifier implements VarIdentifier {
	private Expression leftExpression;
	private ArrayIndex index;

	public ArrayIdentifier(Expression expression, ArrayIndex index) {
		this.leftExpression = expression;
		this.index = index;
	}

	@Override
	public void performIdentifierAnalysis(IdIdentifier table) throws CompilationException {
		leftExpression.performIdentifierAnalysis(table);
		index.performIdentifierAnalysis(table);
	}

	@Override
	public MetaType performTypeAnalysis(IdIdentifier idTable) throws CompilationException {
		MetaType leftType = leftExpression.performTypeAnalysis(idTable);
		if (leftType instanceof ArrayType) {
			return ((ArrayType)leftType).getInternalType();
		}

		throw new CompilationException("Performing [] operator on a non-array type: " + leftType,
				CompilationException.Scope.TypeAnalyzer);
	}
}
