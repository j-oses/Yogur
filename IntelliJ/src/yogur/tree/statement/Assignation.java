package yogur.tree.statement;

import yogur.tree.expression.Expression;
import yogur.tree.expression.identifier.VarIdentifier;

public class Assignation implements Statement {
	private VarIdentifier varId;
	private Expression expression;

	public Assignation(VarIdentifier varId, Expression e) {
		this.varId = varId;
		this.expression = e;
	}
}
