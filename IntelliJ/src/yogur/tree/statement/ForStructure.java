package yogur.tree.statement;

import yogur.tree.expression.Expression;
import yogur.tree.expression.SimpleIdentifier;

public class ForStructure implements Statement {
	private SimpleIdentifier identifier;
	private Expression start;
	private Expression end;
	private Block block;

	public ForStructure(SimpleIdentifier id, Expression s, Expression e, Block b) {
		this.identifier = id;
		this.start = s;
		this.end = e;
		this.block = b;
	}
}
