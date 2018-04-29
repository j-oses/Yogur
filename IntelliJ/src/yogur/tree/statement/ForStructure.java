package yogur.tree.statement;

import yogur.tree.declaration.declarator.BaseDeclarator;
import yogur.tree.expression.Expression;
import yogur.tree.expression.identifier.BaseIdentifier;

public class ForStructure implements Statement {
	private BaseDeclarator declarator;
	private Expression start;
	private Expression end;
	private Block block;

	public ForStructure(BaseDeclarator declarator, Expression s, Expression e, Block b) {
		this.declarator = declarator;
		this.start = s;
		this.end = e;
		this.block = b;
	}
}
