package yogur.tree.statement;

import yogur.tree.expression.Expression;
import yogur.tree.expression.identifier.BaseIdentifier;

public class ForStructure implements Statement {
	private BaseIdentifier identifier;
	private Expression start;
	private Expression end;
	private Block block;

	public ForStructure(BaseIdentifier id, Expression s, Expression e, Block b) {
		this.identifier = id;
		this.start = s;
		this.end = e;
		this.block = b;
	}
}
