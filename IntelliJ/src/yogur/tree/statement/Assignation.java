package yogur.tree.statement;

import yogur.error.CompilationException;
import yogur.ididentification.IdIdentifier;
import yogur.tree.declaration.declarator.Declarator;
import yogur.tree.expression.Expression;

public class Assignation implements Statement {
	private Declarator declarator;
	private Expression expression;

	public Assignation(Declarator declarator, Expression e) {
		this.declarator = declarator;
		this.expression = e;
	}

	@Override
	public void performIdentifierAnalysis(IdIdentifier table) throws CompilationException {
		declarator.performIdentifierAnalysis(table);
		expression.performIdentifierAnalysis(table);
	}
}
