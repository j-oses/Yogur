package yogur.tree.expression;

import yogur.tree.expression.identifier.DotIdentifier;

import java.util.List;

public class FunctionCall implements Expression {
	private Expression function;
	private List<Expression> expressions;

	public FunctionCall(Expression function, List<Expression> expressions) {
		this.function = function;
		this.expressions = expressions;
	}
}
