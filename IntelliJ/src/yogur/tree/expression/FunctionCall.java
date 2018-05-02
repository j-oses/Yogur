package yogur.tree.expression;

import yogur.error.CompilationException;
import yogur.ididentification.IdIdentifier;
import yogur.tree.declaration.Declaration;

import java.util.List;

public class FunctionCall implements Expression {
	private Expression function;
	private List<Expression> expressions;

	private Declaration declaration;

	public FunctionCall(Expression function, List<Expression> expressions) {
		this.function = function;
		this.expressions = expressions;
	}

	@Override
	public void performIdentifierAnalysis(IdIdentifier table) throws CompilationException {
		function.performIdentifierAnalysis(table);
		for (Expression e: expressions) {
			e.performIdentifierAnalysis(table);
		}
	}
}
