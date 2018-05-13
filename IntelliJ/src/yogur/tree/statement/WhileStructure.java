package yogur.tree.statement;

import yogur.codegen.IntegerReference;
import yogur.error.CompilationException;
import yogur.ididentification.IdentifierTable;
import yogur.tree.expression.Expression;
import yogur.tree.type.BaseType;
import yogur.typeidentification.MetaType;

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
	public void performIdentifierAnalysis(IdentifierTable table) throws CompilationException {
		condition.performIdentifierAnalysis(table);
		block.performIdentifierAnalysis(table);
	}

	@Override
	public MetaType analyzeType(IdentifierTable idTable) throws CompilationException {
		MetaType condType = condition.performTypeAnalysis(idTable);

		if (!new BaseType(Bool).equals(condType)) {
			throw new CompilationException("Invalid type on while condition: " + condType, condition.getLine(),
					condition.getColumn(), TypeAnalyzer);
		}

		block.performTypeAnalysis(idTable);

		return null;
	}

	@Override
	public void performMemoryAssignment(IntegerReference currentOffset) {
		block.performMemoryAssignment(currentOffset);
	}
}
