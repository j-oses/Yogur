package yogur.tree.statement;

import yogur.error.CompilationException;
import yogur.ididentification.IdentifierTable;
import yogur.tree.declaration.declarator.Declarator;
import yogur.tree.expression.Expression;
import yogur.typeidentification.MetaType;

import static yogur.error.CompilationException.Scope.TypeAnalyzer;

public class Assignment extends Statement {
	private Declarator declarator;
	private Expression expression;

	public Assignment(Declarator declarator, Expression e) {
		this.declarator = declarator;
		this.expression = e;
	}

	@Override
	public void performIdentifierAnalysis(IdentifierTable table) throws CompilationException {
		declarator.performIdentifierAnalysis(table);
		expression.performIdentifierAnalysis(table);
	}

	@Override
	public MetaType analyzeType(IdentifierTable idTable) throws CompilationException {
		MetaType decType = declarator.performTypeAnalysis(idTable);
		MetaType expType = expression.performTypeAnalysis(idTable);

		if (!expType.equals(decType)) {
			throw new CompilationException("Could not assign result of type " + expType
					+ " to variable of type " + decType, getLine(), getColumn(),
					TypeAnalyzer);
		}

		return null;
	}
}
