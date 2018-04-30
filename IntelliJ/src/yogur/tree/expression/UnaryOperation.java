package yogur.tree.expression;

import yogur.error.CompilationException;
import yogur.ididentification.IdIdentifier;

public class UnaryOperation implements Expression {
	public enum Operator {
		NEG, NOT
	}

	private Expression expression;
	private Operator operator;

	public UnaryOperation(Expression e, Operator o) {
		this.expression = e;
		this.operator = o;
	}

	@Override
	public void performIdentifierAnalysis(IdIdentifier table) throws CompilationException {
		expression.performIdentifierAnalysis(table);
	}
}
