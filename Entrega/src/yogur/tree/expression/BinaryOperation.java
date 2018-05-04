package yogur.tree.expression;

import yogur.error.CompilationException;
import yogur.ididentification.IdIdentifier;
import yogur.tree.type.BaseType;
import yogur.typeidentification.MetaType;

import static yogur.error.CompilationException.Scope;
import static yogur.error.CompilationException.Scope.TypeAnalyzer;

public class BinaryOperation extends Expression {
	public enum Operator {
		SUM, SUBS, PROD, DIV, MOD,
		LT, LEQ, GT, GEQ, EQ, NEQ,
		AND, OR;

		static MetaType boolT = new BaseType(BaseType.PredefinedType.Bool.name());
		static MetaType intT = new BaseType(BaseType.PredefinedType.Int.name());

		public MetaType getReturnType() {
			if (ordinal() < 5) {
				return intT;
			} else {
				return boolT;
			}
		}

		public MetaType getArgumentsType() {
			if (ordinal() < 10) {
				return intT;
			} else {
				return boolT;
			}
		}
	}

	private Expression left;
	private Expression right;
	private Operator operator;

	public BinaryOperation(Expression l, Expression r, Operator o) {
		this.left = l;
		this.right = r;
		this.operator = o;
	}

	@Override
	public void performIdentifierAnalysis(IdIdentifier table) throws CompilationException {
		left.performIdentifierAnalysis(table);
		right.performIdentifierAnalysis(table);
	}

	@Override
	public MetaType analyzeType(IdIdentifier idTable) throws CompilationException {
		MetaType argType = operator.getArgumentsType();
		MetaType leftType = left.performTypeAnalysis(idTable);
		MetaType rightType = right.performTypeAnalysis(idTable);

		if (argType.equals(leftType) && argType.equals(rightType)) {
			return operator.getReturnType();
		}

		throw new CompilationException("Can not apply operator " + operator.name() + " to arguments with types "
				+ leftType + " and " + rightType, getLine(), getColumn(), TypeAnalyzer);
	}
}
