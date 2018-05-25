package yogur.tree.statement;

import yogur.codegen.IntegerReference;
import yogur.codegen.PMachineOutputStream;
import yogur.tree.type.BaseType;
import yogur.utils.CompilationException;
import yogur.ididentification.IdentifierTable;
import yogur.tree.expression.Expression;
import yogur.typeanalysis.MetaType;

import java.io.IOException;

import static yogur.utils.CompilationException.Scope.TypeAnalyzer;

public class Assignment extends Statement {
	private Expression declarator;
	private Expression expression;

	public Assignment(Expression declarator, Expression e) {
		this.declarator = declarator;
		this.expression = e;
	}

	@Override
	public int getMaxDepthOnStack() {
		return Math.max(declarator.getDepthOnStack(), expression.getDepthOnStack());
	}

	@Override
	public void performIdentifierAnalysis(IdentifierTable table) throws CompilationException {
		declarator.performIdentifierAnalysis(table);
		expression.performIdentifierAnalysis(table);
	}

	@Override
	public MetaType analyzeType() throws CompilationException {
		MetaType decType = declarator.performTypeAnalysis();
		MetaType expType = expression.performTypeAnalysis();

		if (!declarator.isAssignable()) {
			throw new CompilationException("Expected a var declaration", getLine(), getColumn(),
					CompilationException.Scope.TypeAnalyzer);
		}

		if (!expType.equals(decType)) {
			throw new CompilationException("Could not assign result of type " + expType
					+ " to variable of type " + decType, getLine(), getColumn(),
					TypeAnalyzer);
		}

		if (!(decType instanceof BaseType)) {
			throw new CompilationException("Could not assign result of non-base type " + expType,
					getLine(), getColumn(), TypeAnalyzer);
		}

		return null;
	}

	@Override
	public void performMemoryAssignment(IntegerReference currentOffset, IntegerReference nestingDepth) {
		declarator.performMemoryAssignment(currentOffset, nestingDepth);
		expression.performMemoryAssignment(currentOffset, nestingDepth);
	}

	@Override
	public void generateCode(PMachineOutputStream stream) throws IOException {
		declarator.generateCodeL(stream);
		expression.generateCodeR(stream);
		stream.appendInstruction("sto");
	}
}
