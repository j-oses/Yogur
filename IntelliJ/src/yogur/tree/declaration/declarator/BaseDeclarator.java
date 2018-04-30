package yogur.tree.declaration.declarator;

import yogur.error.CompilationException;
import yogur.ididentification.IdIdentifier;
import yogur.tree.declaration.Declaration;

public class BaseDeclarator implements Declarator {
	private String identifier;

	private Declaration declaration;

	public BaseDeclarator(String identifier) {
		this.identifier = identifier;
	}

	public String getIdentifier() {
		return identifier;
	}

	@Override
	public void performIdentifierAnalysis(IdIdentifier table) throws CompilationException {
		declaration = table.searchId(identifier);
	}
}
