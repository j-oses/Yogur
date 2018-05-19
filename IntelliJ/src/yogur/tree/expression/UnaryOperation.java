package yogur.tree.expression;

import yogur.codegen.IntegerReference;
import yogur.codegen.PMachineOutputStream;
import yogur.utils.CompilationException;
import yogur.ididentification.IdentifierTable;
import yogur.tree.type.BaseType;
import yogur.typeidentification.MetaType;

import java.io.IOException;

import static yogur.utils.CompilationException.Scope.TypeAnalyzer;
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
	public int getDepthOnStack() {
		return expression.getDepthOnStack();
	}

	@Override
	public void performIdentifierAnalysis(IdentifierTable table) throws CompilationException {
		expression.performIdentifierAnalysis(table);
	}

	@Override
	public MetaType analyzeType() throws CompilationException {
		MetaType argType = expression.performTypeAnalysis();
		MetaType expectedType = new BaseType(
				operator.equals(NOT) ? Bool : Int);

		if (expectedType.equals(argType)) {
			return expectedType;
		}

		throw new CompilationException("Can not apply operator " + operator.name() + " to argument with type "
				+ argType, getLine(), getColumn(), TypeAnalyzer);
	}

	@Override
	public void performMemoryAssignment(IntegerReference currentOffset, IntegerReference nestingDepth) {
		expression.performMemoryAssignment(currentOffset, nestingDepth);
	}

	@Override
	public void generateCodeR(PMachineOutputStream stream) throws IOException {
		String inst = (Operator.NEG.equals(operator)) ? "neg" : "not";
		expression.generateCodeR(stream);
		stream.appendInstruction(inst);
	}
}
