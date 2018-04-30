package yogur.tree.statement;

import yogur.error.CompilationException;
import yogur.ididentification.IdIdentifier;
import yogur.tree.declaration.Argument;
import yogur.tree.declaration.FunctionOrVarDeclaration;
import yogur.tree.expression.Expression;

public class VarDeclaration implements Statement, FunctionOrVarDeclaration {
	Argument argument;
	Expression assignTo;	// May be null

	public VarDeclaration(Argument argument) {
		this(argument, null);
	}

	public VarDeclaration(Argument argument, Expression assignTo) {
		this.argument = argument;
		this.assignTo = assignTo;
	}

	@Override
	public void performIdentifierAnalysis(IdIdentifier table) throws CompilationException {
		argument.performIdentifierAnalysis(table);
		if (assignTo != null) {
			assignTo.performIdentifierAnalysis(table);
		}
	}
}
