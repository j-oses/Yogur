package yogur.tree.statement;

import yogur.tree.declaration.declarator.Declarator;
import yogur.tree.expression.Expression;
import yogur.tree.expression.identifier.VarIdentifier;

public class Assignation implements Statement {
	private Declarator declarator;
	private Expression expression;

	public Assignation(Declarator declarator, Expression e) {
		this.declarator = declarator;
		this.expression = e;
	}
}
