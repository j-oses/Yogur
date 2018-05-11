package yogur.tree.expression;

import yogur.error.CompilationException;
import yogur.ididentification.IdentifierTable;
import yogur.tree.type.BaseType;
import yogur.typeidentification.MetaType;

import static yogur.error.CompilationException.Scope.TypeAnalyzer;
import static yogur.tree.expression.UnaryOperation.Operator.NOT;
import static yogur.tree.type.BaseType.PredefinedType.Bool;
import static yogur.tree.type.BaseType.PredefinedType.Int;

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
	public void performIdentifierAnalysis(IdentifierTable table) throws CompilationException {
		expression.performIdentifierAnalysis(table);
	}

	@Override
	public MetaType analyzeType(IdentifierTable idTable) throws CompilationException {
		MetaType argType = expression.performTypeAnalysis(idTable);
		MetaType expectedType = new BaseType(
				operator.equals(NOT) ? Bool : Int);

		if (expectedType.equals(argType)) {
			return expectedType;
		}

		throw new CompilationException("Can not apply operator " + operator.name() + " to argument with type "
				+ argType, getLine(), getColumn(), TypeAnalyzer);
	}
}
