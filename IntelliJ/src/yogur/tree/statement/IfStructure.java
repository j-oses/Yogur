package yogur.tree.statement;

import yogur.error.CompilationException;
import yogur.ididentification.IdIdentifier;
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

	@Override
	public void performIdentifierAnalysis(IdIdentifier table) throws CompilationException {
		condition.performIdentifierAnalysis(table);
		ifClause.performIdentifierAnalysis(table);
		if (elseClause != null) {
			elseClause.performIdentifierAnalysis(table);
		}
	}
}
