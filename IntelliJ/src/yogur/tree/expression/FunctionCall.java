package yogur.tree.expression;

import yogur.codegen.PMachineOutputStream;
import yogur.utils.CompilationException;
import yogur.ididentification.IdentifierTable;
import yogur.tree.declaration.Declaration;
import yogur.tree.expression.identifier.VarIdentifier;
import yogur.typeidentification.FunctionType;
import yogur.typeidentification.MetaType;
import yogur.typeidentification.VoidType;

import java.util.List;

import static yogur.utils.CompilationException.Scope.TypeAnalyzer;

public class FunctionCall extends Expression {
	private Expression function;
	private List<Expression> expressions;

	private Declaration declaration;

	public FunctionCall(Expression function, List<Expression> expressions) {
		this.function = function;
		this.expressions = expressions;
	}

	@Override
	public void performIdentifierAnalysis(IdentifierTable table) throws CompilationException {
		function.performIdentifierAnalysis(table);
		for (Expression e: expressions) {
			e.performIdentifierAnalysis(table);
		}
	}

	@Override
	public MetaType analyzeType(IdentifierTable idTable) throws CompilationException {
		MetaType type = function.performTypeAnalysis(idTable);

		if (type instanceof FunctionType) {
			if (function instanceof VarIdentifier) {
				declaration = ((VarIdentifier) function).getDeclaration();
			}

			FunctionType fType = (FunctionType) type;
			int i = 0;

			for (Expression exp : expressions) {
				MetaType argType = exp.performTypeAnalysis(idTable);
				if (!fType.isValidArgument(i, argType)) {
					throw new CompilationException("Invalid function " + (i + 1) + "th argument with type: "
							+ argType, getLine(), getColumn(), TypeAnalyzer);
				}
				i++;
			}

			return fType.getReturnType() != null ? fType.getReturnType() : new VoidType();
		}

		throw new CompilationException("Trying to call a function on the non-function object with type: " + type,
				getLine(), getColumn(), TypeAnalyzer);
	}

	@Override
	public void generateCodeR(PMachineOutputStream stream) {
		// FIXME: Fill when functions are implemented
	}
}
