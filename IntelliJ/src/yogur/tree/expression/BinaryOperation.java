package yogur.tree.expression;

import yogur.codegen.PMachineOutputStream;
import yogur.utils.CompilationException;
import yogur.ididentification.IdentifierTable;
import yogur.tree.type.BaseType;
import yogur.typeidentification.MetaType;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static yogur.utils.CompilationException.Scope.TypeAnalyzer;

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

	static Map<Operator, String> instructionName = new HashMap<>();

	static {
		instructionName.put(Operator.SUM, "add");
		instructionName.put(Operator.SUBS, "sub");
		instructionName.put(Operator.PROD, "mul");
		instructionName.put(Operator.DIV, "div");
		instructionName.put(Operator.LT, "les");
		instructionName.put(Operator.LEQ, "leq");
		instructionName.put(Operator.GT, "grt");
		instructionName.put(Operator.GEQ, "geq");
		instructionName.put(Operator.EQ, "equ");
		instructionName.put(Operator.NEQ, "neq");
		instructionName.put(Operator.AND, "and");
		instructionName.put(Operator.OR, "or");
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
	public void performIdentifierAnalysis(IdentifierTable table) throws CompilationException {
		left.performIdentifierAnalysis(table);
		right.performIdentifierAnalysis(table);
	}

	@Override
	public MetaType analyzeType(IdentifierTable idTable) throws CompilationException {
		MetaType argType = operator.getArgumentsType();
		MetaType leftType = left.performTypeAnalysis(idTable);
		MetaType rightType = right.performTypeAnalysis(idTable);

		if (argType.equals(leftType) && argType.equals(rightType)) {
			return operator.getReturnType();
		}

		throw new CompilationException("Can not apply operator " + operator.name() + " to arguments with types "
				+ leftType + " and " + rightType, getLine(), getColumn(), TypeAnalyzer);
	}

	@Override
	public void generateCodeR(PMachineOutputStream stream) throws IOException {
		if (operator.equals(Operator.MOD)) {
			Expression generatedExp = generateModExpression();
			generatedExp.generateCodeR(stream);
		} else {
			left.generateCodeR(stream);
			right.generateCodeR(stream);
			stream.appendInstruction(instructionName.get(operator));
		}
	}

	private Expression generateModExpression() {
		// l % r is equivalent to l - ((l / r) * r)
		Expression division = new BinaryOperation(left, right, operator.DIV);	// l / r
		Expression product = new BinaryOperation(division, right, Operator.PROD);	// (l / r) * r
		return new BinaryOperation(left, product, Operator.SUBS);	// l - ((l / r) * r)
	}
}
