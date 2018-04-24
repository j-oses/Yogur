package yogur.tree.statement;

import yogur.tree.expression.Expression;

public class WhileStructure implements Statement {
	private Expression condition;
	private Block block;

	public WhileStructure(Expression cond, Block b) {
		this.condition = cond;
		this.block = b;
	}
}
