package yogur.tree.expression.identifier;

import yogur.tree.expression.Expression;

public class DotIdentifier implements VarIdentifier {
	private Expression expression;
	private String identifier;

	public DotIdentifier(Expression left, String right) {
		this.expression = left;
		this.identifier = right;
	}
}
