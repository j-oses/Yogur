package yogur.tree.expression;

import yogur.tree.expression.identifier.DotIdentifier;

import java.util.List;

public class FunctionCall implements Expression {
	private DotIdentifier identifier;
	private List<Expression> expressions;

	public FunctionCall(DotIdentifier identifier, List<Expression> expressions) {
		this.identifier = identifier;
		this.expressions = expressions;
	}
}
