package yogur.tree.statement;

import yogur.codegen.PMachineOutputStream;
import yogur.error.CompilationException;
import yogur.ididentification.IdIdentifier;
import yogur.tree.expression.Expression;
import yogur.tree.type.BaseType;
import yogur.typeidentification.MetaType;

import java.io.IOException;

import static yogur.error.CompilationException.Scope.TypeAnalyzer;
import static yogur.tree.type.BaseType.PredefinedType.Bool;

public class WhileStructure extends Statement {
	private Expression condition;
	private Block block;

	public WhileStructure(Expression cond, Block b) {
		this.condition = cond;
		this.block = b;
	}

	@Override
	public void performIdentifierAnalysis(IdIdentifier table) throws CompilationException {
		condition.performIdentifierAnalysis(table);
		block.performIdentifierAnalysis(table);
	}

	@Override
	public MetaType analyzeType(IdIdentifier idTable) throws CompilationException {
		MetaType condType = condition.performTypeAnalysis(idTable);

		if (!new BaseType(Bool).equals(condType)) {
			throw new CompilationException("Invalid type on while condition: " + condType, condition.getLine(),
					condition.getColumn(), TypeAnalyzer);
		}

		block.performTypeAnalysis(idTable);

		return null;
	}

	@Override
	public int performMemoryAnalysis(int currentOffset, int currentDepth) {
		currentOffset = condition.performMemoryAnalysis(currentOffset, currentDepth);
		currentOffset = block.performMemoryAnalysis(currentOffset, currentDepth);
		return currentOffset;
	}

	@Override
	public void generateCode(PMachineOutputStream stream) throws IOException {
		String start = stream.generateUnusedLabel();
		String end = stream.generateUnusedLabel();

		stream.appendLabel(start);
		stream.appendInstruction("fjp", end);
		block.generateCode(stream);
		stream.appendInstruction("ujp", start);
		stream.appendLabel(end);
	}
}
