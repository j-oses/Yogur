package yogur.tree.statement;

import yogur.codegen.PMachineOutputStream;
import yogur.error.CompilationException;
import yogur.ididentification.IdIdentifier;
import yogur.tree.declaration.Argument;
import yogur.tree.declaration.FunctionOrVarDeclaration;
import yogur.tree.expression.Expression;
import yogur.typeidentification.MetaType;

import java.io.IOException;

import static yogur.error.CompilationException.Scope;
import static yogur.error.CompilationException.Scope.TypeAnalyzer;

public class VarDeclaration extends Statement implements FunctionOrVarDeclaration {
	private Argument argument;
	private Expression assignTo;	// May be null

	private int size;

	public VarDeclaration(Argument argument) {
		this(argument, null);
	}

	public VarDeclaration(Argument argument, Expression assignTo) {
		this.argument = argument;
		this.assignTo = assignTo;
	}

	public int getSize() {
		return size;
	}

	@Override
	public void performIdentifierAnalysis(IdIdentifier table) throws CompilationException {
		argument.performIdentifierAnalysis(table);
		if (assignTo != null) {
			assignTo.performIdentifierAnalysis(table);
		}
	}

	@Override
	public MetaType analyzeType(IdIdentifier idTable) throws CompilationException {
		MetaType argType = argument.performTypeAnalysis(idTable);

		if (assignTo == null) {
			return null;
		}

		MetaType assType = assignTo.performTypeAnalysis(idTable);

		if (argType.equals(assType)) {
			return null;
		}

		throw new CompilationException("Assigning an expression of type: " + assType +
				" to a variable of type: " + argType, getLine(), getColumn(), TypeAnalyzer);
	}

	@Override
	public MetaType performTypeAnalysis(IdIdentifier idTable) throws CompilationException {
		MetaType type = super.performTypeAnalysis(idTable);
		size = type.sizeOf();
		return type;
	}
}
