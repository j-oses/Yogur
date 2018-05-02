package yogur.tree.declaration.declarator;

import yogur.error.CompilationException;
import yogur.ididentification.IdIdentifier;
import yogur.typeidentification.MetaType;

public class DotDeclarator implements Declarator {
	private Declarator declarator;
	private String identifier;

	public DotDeclarator(Declarator declarator, String identifier) {
		this.declarator = declarator;
		this.identifier = identifier;
	}

	@Override
	public void performIdentifierAnalysis(IdIdentifier table) throws CompilationException {
		declarator.performIdentifierAnalysis(table);
	}

	@Override
	public MetaType performTypeAnalysis(IdIdentifier idTable) {
		return null;
	}
}
