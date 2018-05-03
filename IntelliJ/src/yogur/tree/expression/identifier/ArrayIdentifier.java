package yogur.tree.expression.identifier;

import yogur.error.CompilationException;
import yogur.ididentification.IdIdentifier;
import yogur.tree.declaration.Declaration;
import yogur.tree.expression.Expression;
import yogur.tree.type.ArrayType;
import yogur.typeidentification.MetaType;

public class ArrayIdentifier extends VarIdentifier {
	private Expression leftExpression;
	private ArrayIndex index;

	public ArrayIdentifier(Expression expression, ArrayIndex index) {
		this.leftExpression = expression;
		this.index = index;
	}

	@Override
	public Declaration getDeclaration() {
		return null;
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
			if (index.returnsSingleElement()) {
				return ((ArrayType) leftType).getInternalType();
			} else {
				return leftType;
			}
		}

		throw new CompilationException("Performing [] operator on a non-array type: " + leftType,
				getLine(), getColumn(), CompilationException.Scope.TypeAnalyzer);
	}
}
