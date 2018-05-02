package yogur.tree.statement;

import yogur.error.CompilationException;
import yogur.ididentification.IdIdentifier;
import yogur.tree.expression.Expression;
import yogur.tree.type.BaseType;
import yogur.typeidentification.MetaType;

public class WhileStructure implements Statement {
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
	public MetaType performTypeAnalysis(IdIdentifier idTable) throws CompilationException {
		MetaType condType = condition.performTypeAnalysis(idTable);
		block.performTypeAnalysis(idTable);

		if (new BaseType(BaseType.PredefinedType.Bool).equals(condType)) {
			return null;
		}

		throw new CompilationException("Invalid type on while condition: " + condType,
				CompilationException.Scope.TypeAnalyzer);
	}
}
