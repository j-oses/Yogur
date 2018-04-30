package yogur.tree.expression.identifier;

import yogur.error.CompilationException;
import yogur.ididentification.IdIdentifier;
import yogur.tree.declaration.Declaration;

public class BaseIdentifier implements VarIdentifier {
	private String name;

	private Declaration declaration;

	public BaseIdentifier(String name) {
		this.name = name;
	}

	public Declaration getDeclaration() {
		return declaration;
	}

	@Override
	public void performIdentifierAnalysis(IdIdentifier table) throws CompilationException {
		declaration = table.searchId(name);
	}
}
