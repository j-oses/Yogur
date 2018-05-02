package yogur.tree.statement;

import yogur.error.CompilationException;
import yogur.ididentification.IdIdentifier;
import yogur.tree.declaration.Argument;
import yogur.tree.declaration.FunctionOrVarDeclaration;
import yogur.tree.expression.Expression;
import yogur.typeidentification.MetaType;

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

	@Override
	public MetaType performTypeAnalysis(IdIdentifier idTable) throws CompilationException {
		MetaType argType = argument.performTypeAnalysis(idTable);

		if (assignTo == null) {
			return null;
		}

		MetaType assType = assignTo.performTypeAnalysis(idTable);

		if (argType.equals(assType)) {
			return null;
		}

		throw new CompilationException("Assigning an expression of type: " + assType +
				" to a variable of type: " + argType, CompilationException.Scope.TypeAnalyzer);
	}
}
