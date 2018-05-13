package yogur.tree.expression.identifier;

import yogur.codegen.PMachineOutputStream;
import yogur.utils.CompilationException;
import yogur.ididentification.IdentifierTable;
import yogur.tree.declaration.Argument;
import yogur.tree.declaration.Declaration;
import yogur.typeidentification.MetaType;

import java.io.IOException;

public class BaseIdentifier extends VarIdentifier {
	private String name;

	private Declaration declaration;

	public BaseIdentifier(String name) {
		this.name = name;
	}

	/**
	 * To be used internally to mock a base identifier. A node created with this constructor is not
	 * expected to be inserted in the tree.
	 * @param argument the declaration associated to this identifier.
	 */
	public BaseIdentifier(Argument argument) {
		this.name = argument.getDeclarator().getIdentifier();
		this.declaration = argument;
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

	@Override
	public void generateCodeR(PMachineOutputStream stream) throws IOException {
		// FIXME: will change with complex identifiers
		if (declaration instanceof Argument) {
			stream.appendInstruction("ldc", ((Argument)declaration).getOffset());
		} else {
			// FIXME: Currently does nothing for a function
		}
	}
}
