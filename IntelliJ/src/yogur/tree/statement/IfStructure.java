package yogur.tree.statement;

import yogur.tree.expression.Expression;

public class IfStructure implements Statement {
	private Expression condition;
	private Block ifClause;
	private Block elseClause;	// May be null

	public IfStructure(Expression condition, Block ifClause) {
		this(condition, ifClause, null);
	}

	public IfStructure(Expression condition, Block ifClause, Block elseClause) {
		this.condition = condition;
		this.ifClause = ifClause;
		this.elseClause = elseClause;
	}
}
