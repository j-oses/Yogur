package yogur.tree.statement;

import yogur.codegen.IntegerReference;
import yogur.codegen.PMachineOutputStream;
import yogur.utils.CompilationException;
import yogur.ididentification.IdentifierTable;
import yogur.tree.expression.Expression;
import yogur.tree.type.BaseType;
import yogur.typeanalysis.MetaType;

import java.io.IOException;

import static yogur.utils.CompilationException.Scope.TypeAnalyzer;
import static yogur.tree.type.BaseType.PredefinedType.Bool;

public class WhileStructure extends Statement {
	private Expression condition;
	private Block block;

	public WhileStructure(Expression cond, Block b) {
		this.condition = cond;
		this.block = b;
	}

	@Override
	public int getMaxDepthOnStack() {
		return Math.max(condition.getDepthOnStack(), block.getMaxDepthOnStack());
	}

	@Override
	public void performIdentifierAnalysis(IdentifierTable table) throws CompilationException {
		condition.performIdentifierAnalysis(table);
		block.performIdentifierAnalysis(table);
	}

	@Override
	public MetaType analyzeType() throws CompilationException {
		MetaType condType = condition.performTypeAnalysis();

		if (!new BaseType(Bool).equals(condType)) {
			throw new CompilationException("Invalid type on while condition: " + condType, condition.getLine(),
					condition.getColumn(), TypeAnalyzer);
		}

		block.performTypeAnalysis();

		return null;
	}

	@Override
	public void performMemoryAssignment(IntegerReference currentOffset, IntegerReference nestingDepth) {
		condition.performMemoryAssignment(currentOffset, nestingDepth);
		block.performMemoryAssignment(currentOffset, nestingDepth);
	}

	@Override
	public void generateCode(PMachineOutputStream stream) throws IOException {
		String labelStart = stream.generateLabelWithUnusedId("while");
		String labelEnd = stream.generateLabel("endWhile");

		stream.appendLabel(labelStart);
		condition.generateCodeR(stream);
		stream.appendLabelledInstruction("fjp", labelEnd);
		block.generateCode(stream);
		stream.appendLabelledInstruction("ujp", labelStart);
		stream.appendLabel(labelEnd);
	}
}
