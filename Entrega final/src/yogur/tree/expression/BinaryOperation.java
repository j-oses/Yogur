package yogur.tree.expression;

import yogur.codegen.IntegerReference;
import yogur.codegen.PMachineOutputStream;
import yogur.utils.CompilationException;
import yogur.ididentification.IdentifierTable;
import yogur.tree.type.BaseType;
import yogur.typeanalysis.MetaType;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static yogur.utils.CompilationException.Scope.TypeAnalyzer;

public class BinaryOperation extends Expression {
	public enum Operator {
		SUM, SUBS, PROD, DIV,
		LT, LEQ, GT, GEQ, EQ, NEQ,
		AND, OR;

		static MetaType boolT = new BaseType(BaseType.PredefinedType.Bool.name());
		static MetaType intT = new BaseType(BaseType.PredefinedType.Int.name());

		public MetaType getReturnType() {
			if (ordinal() < 4) {
				return intT;
			} else {
				return boolT;
			}
		}

		public boolean areValidArgumentTypes(MetaType arg1, MetaType arg2) {
			if (ordinal() < 8) {
				return intT.equals(arg1) && intT.equals(arg2);
			} else if (ordinal() >= 10) {
				return boolT.equals(arg1) && boolT.equals(arg2);
			} else {
				return (arg1 instanceof BaseType) && arg1.equals(arg2);
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
	public int getDepthOnStack() {
		return Math.max(left.getDepthOnStack(), right.getDepthOnStack() + 1);
	}

	@Override
	public void performIdentifierAnalysis(IdentifierTable table) throws CompilationException {
		left.performIdentifierAnalysis(table);
		right.performIdentifierAnalysis(table);
	}

	@Override
	public MetaType analyzeType() throws CompilationException {
		MetaType leftType = left.performTypeAnalysis();
		MetaType rightType = right.performTypeAnalysis();
		boolean validArgs = operator.areValidArgumentTypes(leftType, rightType);

		if (validArgs) {
			return operator.getReturnType();
		}

		throw new CompilationException("Can not apply operator " + operator.name() + " to arguments with types "
				+ leftType + " and " + rightType, getLine(), getColumn(), TypeAnalyzer);
	}

	@Override
	public void performMemoryAssignment(IntegerReference currentOffset, IntegerReference nestingDepth) {
		left.performMemoryAssignment(currentOffset, nestingDepth);
		right.performMemoryAssignment(currentOffset, nestingDepth);
	}

	@Override
	public void generateCodeR(PMachineOutputStream stream) throws IOException {
		left.generateCodeR(stream);
		right.generateCodeR(stream);
		stream.appendInstruction(instructionName.get(operator));
	}
}
