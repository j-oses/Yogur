package yogur.tree.statement;

import yogur.codegen.IntegerReference;
import yogur.codegen.PMachineOutputStream;
import yogur.utils.CompilationException;
import yogur.ididentification.IdentifierTable;
import yogur.tree.expression.Expression;
import yogur.tree.type.BaseType;
import yogur.typeidentification.MetaType;

import java.io.IOException;

import static yogur.utils.CompilationException.Scope.TypeAnalyzer;
import static yogur.tree.type.BaseType.PredefinedType.Bool;

public class IfStructure extends Statement {
	private Expression condition;
	private Block ifClause;
	private Block elseClause;	// May be null

	public IfStructure(Expression condition, Block ifClause, Block elseClause) {
		this.condition = condition;
		this.ifClause = ifClause;
		this.elseClause = elseClause;
	}

	@Override
	public int getMaxDepthOnStack() {
		int clausesDepth = ifClause.getMaxDepthOnStack();

		if (elseClause != null) {
			clausesDepth = Math.max(clausesDepth, elseClause.getMaxDepthOnStack());
		}
		return Math.max(condition.getDepthOnStack(), clausesDepth);
	}

	@Override
	public void performIdentifierAnalysis(IdentifierTable table) throws CompilationException {
		condition.performIdentifierAnalysis(table);
		ifClause.performIdentifierAnalysis(table);
		if (elseClause != null) {
			elseClause.performIdentifierAnalysis(table);
		}
	}

	@Override
	public MetaType analyzeType() throws CompilationException {
		MetaType condType = condition.performTypeAnalysis();
		ifClause.performTypeAnalysis();
		if (elseClause != null) {
			elseClause.performTypeAnalysis();
		}

		if (new BaseType(Bool).equals(condType)) {
			return null;
		}

		throw new CompilationException("Invalid type on if condition: " + condType, condition.getLine(),
				condition.getColumn(), TypeAnalyzer);
	}

	@Override
	public void performMemoryAssignment(IntegerReference currentOffset, IntegerReference nestingDepth) {
		condition.performMemoryAssignment(currentOffset, nestingDepth);
		ifClause.performMemoryAssignment(currentOffset, nestingDepth);
		if (elseClause != null) {
			elseClause.performMemoryAssignment(currentOffset, nestingDepth);
		}
	}

	@Override
	public void generateCode(PMachineOutputStream stream) throws IOException {
		String labelElse = stream.generateLabelWithUnusedId("else");
		String labelEndif = stream.generateLabelWithUnusedId("endif");

		condition.generateCodeR(stream);

		if (elseClause != null) {
			stream.appendLabelledInstruction("fjp", labelElse);
		} else {
			stream.appendLabelledInstruction("fjp", labelEndif);
		}

		ifClause.generateCode(stream);

		if (elseClause != null) {
			stream.appendLabelledInstruction("ujp", labelEndif);
			stream.appendLabel(labelElse);
			elseClause.generateCode(stream);
		}

		stream.appendLabel(labelEndif);
	}
}
