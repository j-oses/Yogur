package yogur.tree.expression.identifier;

import yogur.error.CompilationException;
import yogur.ididentification.IdentifierTable;
import yogur.tree.declaration.Declaration;
import yogur.typeidentification.MetaType;

public class BaseIdentifier extends VarIdentifier {
	private String name;

	private Declaration declaration;

	public BaseIdentifier(String name) {
		this.name = name;
	}

	public Declaration getDeclaration() {
		return declaration;
	}

	@Override
	public void performIdentifierAnalysis(IdentifierTable table) throws CompilationException {
		declaration = table.searchId(name, getLine(), getColumn());
	}

	@Override
	public MetaType analyzeType(IdentifierTable idTable) throws CompilationException {
		return declaration.performTypeAnalysis(idTable);
	}
}
