package yogur.tree.statement;

import yogur.error.CompilationException;
import yogur.ididentification.IdIdentifier;
import yogur.tree.expression.Expression;
import yogur.tree.type.BaseType;
import yogur.typeidentification.MetaType;

import static yogur.error.CompilationException.Scope;
import static yogur.error.CompilationException.Scope.TypeAnalyzer;
import static yogur.tree.type.BaseType.PredefinedType;
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
	public void performIdentifierAnalysis(IdIdentifier table) throws CompilationException {
		condition.performIdentifierAnalysis(table);
		ifClause.performIdentifierAnalysis(table);
		if (elseClause != null) {
			elseClause.performIdentifierAnalysis(table);
		}
	}

	@Override
	public MetaType analyzeType(IdIdentifier idTable) throws CompilationException {
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
}