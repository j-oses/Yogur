package yogur.tree.expression.identifier;

import yogur.tree.ArrayType;
import yogur.tree.expression.Expression;

public class ArrayIdentifier implements VarIdentifier {
	private Expression leftExpression;
	private ArrayIndex index;

	public ArrayIdentifier(Expression expression, ArrayIndex index) {
		this.leftExpression = expression;
		this.index = index;
	}
}
