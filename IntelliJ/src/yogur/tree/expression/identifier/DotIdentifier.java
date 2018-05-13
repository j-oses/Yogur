package yogur.tree.expression.identifier;

import yogur.codegen.PMachineOutputStream;
import yogur.utils.CompilationException;
import yogur.ididentification.IdentifierTable;
import yogur.tree.declaration.Declaration;
import yogur.tree.expression.Expression;
import yogur.tree.type.ClassType;
import yogur.typeidentification.MetaType;

import java.io.IOException;

import static yogur.utils.CompilationException.Scope.TypeAnalyzer;

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
	public void performIdentifierAnalysis(IdentifierTable table) throws CompilationException {
		expression.performIdentifierAnalysis(table);
	}

	@Override
	public MetaType analyzeType(IdentifierTable idTable) throws CompilationException {
		MetaType left = expression.performTypeAnalysis(idTable);
		if (left instanceof ClassType) {
			ClassType classT = (ClassType) left;
			declaration = classT.getDeclaration().getDeclaration(identifier);
			return declaration.performTypeAnalysis(idTable);
		}

		throw new CompilationException("Trying to member access (." + identifier + ") on a compound type " + left,
				getLine(), getColumn(), TypeAnalyzer);
	}

	@Override
	public void generateCodeR(PMachineOutputStream stream) throws IOException {
		// FIXME: will change with complex identifiers
	}
}
