package yogur.tree.expression.identifier;

import yogur.error.CompilationException;
import yogur.ididentification.IdIdentifier;
import yogur.tree.expression.Expression;

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
}
