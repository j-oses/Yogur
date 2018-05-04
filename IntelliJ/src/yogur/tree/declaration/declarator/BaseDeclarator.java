package yogur.tree.declaration.declarator;

import yogur.error.CompilationException;
import yogur.ididentification.IdIdentifier;
import yogur.tree.declaration.Declaration;
import yogur.typeidentification.MetaType;

public class BaseDeclarator extends Declarator {
	private String identifier;

	/**
	 * The declarator where the variable is declared.
	 */
	private Declaration declaration;

	public BaseDeclarator(String identifier) {
		this.identifier = identifier;
	}

	public String getIdentifier() {
		return identifier;
	}

	@Override
	public void performIdentifierAnalysis(IdIdentifier table) throws CompilationException {
		declaration = table.searchId(identifier, getLine(), getColumn());
	}

	@Override
	public MetaType analyzeType(IdIdentifier idTable) throws CompilationException {
		return declaration.performTypeAnalysis(idTable);
	}
}
