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

	public IfStructure(Expression condition, Block ifClause) {
		this(condition, ifClause, null);
	}

	public IfStructure(Expression condition, Block ifClause, Block elseClause) {
		this.condition = condition;
		this.ifClause = ifClause;
		this.elseClause = elseClause;
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
	public MetaType analyzeType(IdentifierTable idTable) throws CompilationException {
		MetaType condType = condition.performTypeAnalysis(idTable);
		ifClause.performTypeAnalysis(idTable);
		if (elseClause != null) {
			elseClause.performTypeAnalysis(idTable);
		}

		if (new BaseType(Bool).equals(condType)) {
			return null;
		}

		throw new CompilationException("Invalid type on if condition: " + condType, condition.getLine(),
				condition.getColumn(), TypeAnalyzer);
	}

	@Override
	public void performMemoryAssignment(IntegerReference currentOffset) {
		ifClause.performMemoryAssignment(currentOffset);
		if (elseClause != null) {
			elseClause.performMemoryAssignment(currentOffset);
		}
	}

	@Override
	public void generateCode(PMachineOutputStream stream) throws IOException {
		String labelElse = stream.generateLabelWithUnusedId("else");
		String labelEndif = stream.generateLabelWithUnusedId("endif");

		condition.generateCodeR(stream);

		if (elseClause != null) {
			stream.appendInstruction("fjp", labelElse);
		} else {
			stream.appendInstruction("fjp", labelEndif);
		}

		ifClause.generateCode(stream);

		if (elseClause != null) {
			stream.appendInstruction("ujp", labelEndif);
			stream.appendLabel(labelElse);
			elseClause.generateCode(stream);
		}

		stream.appendLabel(labelEndif);
	}
}
