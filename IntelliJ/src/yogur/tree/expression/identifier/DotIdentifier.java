package yogur.tree.expression.identifier;

import yogur.error.CompilationException;
import yogur.ididentification.IdIdentifier;
import yogur.tree.declaration.Declaration;
import yogur.tree.expression.Expression;
import yogur.tree.type.BaseType;
import yogur.typeidentification.MetaType;

import static yogur.error.CompilationException.Scope.TypeAnalyzer;

public class DotIdentifier extends VarIdentifier {
	private Expression expression;
	private String identifier;

	private Declaration declaration;

	public DotIdentifier(Expression left, String right) {
		this.expression = left;
		this.identifier = right;
	}

	@Override
	public Declaration getDeclaration() {
		return declaration;
	}

	@Override
	public void performIdentifierAnalysis(IdIdentifier table) throws CompilationException {
		expression.performIdentifierAnalysis(table);
	}

	@Override
	public MetaType analyzeType(IdIdentifier idTable) throws CompilationException {
		MetaType left = expression.performTypeAnalysis(idTable);
		if (left instanceof BaseType) {
			String name = ((BaseType) left).getName();
			declaration = idTable.searchIdOnClass(identifier, name, getLine(), getColumn());
			return declaration.performTypeAnalysis(idTable);
		}

		throw new CompilationException("Trying to member access (." + identifier + ") on a compound type " + left,
				getLine(), getColumn(), TypeAnalyzer);
	}

	@Override
	public int performMemoryAnalysis(int currentOffset, int currentDepth) {
		expression.performMemoryAnalysis(currentOffset, currentDepth);
		return currentOffset;
	}
}
