package yogur.tree.declaration.declarator;

import yogur.codegen.PMachineOutputStream;
import yogur.tree.expression.identifier.BaseIdentifier;
import yogur.tree.type.BaseType;
import yogur.utils.CompilationException;
import yogur.ididentification.IdentifierTable;
import yogur.tree.declaration.Declaration;
import yogur.typeidentification.MetaType;

import java.io.IOException;

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
	public void performIdentifierAnalysis(IdentifierTable table) throws CompilationException {
		declaration = table.searchId(identifier, getLine(), getColumn());
	}

	@Override
	public MetaType analyzeType(IdentifierTable idTable) throws CompilationException {
		return declaration.performTypeAnalysis(idTable);
	}

	@Override
	public void generateCodeL(PMachineOutputStream stream) {
		// FIXME: Generate code
	}
}
