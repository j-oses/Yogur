package yogur.tree.expression;

import yogur.error.CompilationException;
import yogur.ididentification.IdIdentifier;
import yogur.tree.type.BaseType;
import yogur.typeidentification.MetaType;

public class UnaryOperation extends Expression {
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

	@Override
	public MetaType performTypeAnalysis(IdIdentifier idTable) throws CompilationException {
		MetaType argType = expression.performTypeAnalysis(idTable);
		MetaType expectedType = new BaseType(
				operator.equals(Operator.NOT) ? BaseType.PredefinedType.Bool : BaseType.PredefinedType.Int);

		if (expectedType.equals(argType)) {
			return expectedType;
		}

		throw new CompilationException("Can not apply operator " + operator.name() + " to argument with type "
				+ argType, getLine(), getColumn(), CompilationException.Scope.TypeAnalyzer);
	}
}
