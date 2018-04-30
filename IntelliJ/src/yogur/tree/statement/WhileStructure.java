package yogur.tree.statement;

import yogur.error.CompilationException;
import yogur.ididentification.IdIdentifier;
import yogur.tree.expression.Expression;

public class WhileStructure implements Statement {
	private Expression condition;
	private Block block;

	public WhileStructure(Expression cond, Block b) {
		this.condition = cond;
		this.block = b;
	}

	@Override
	public void performIdentifierAnalysis(IdIdentifier table) throws CompilationException {
		condition.performIdentifierAnalysis(table);
		block.performIdentifierAnalysis(table);
	}
}
