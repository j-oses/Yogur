package yogur.tree.declaration;

import yogur.error.CompilationException;
import yogur.ididentification.IdentifierTable;
import yogur.tree.declaration.Declaration;

public interface FunctionOrVarDeclaration extends Declaration {
	void performInsertIdentifierAnalysis(IdentifierTable table) throws CompilationException;

	void performBodyIdentifierAnalysis(IdentifierTable table) throws CompilationException;

	String getName();
}
