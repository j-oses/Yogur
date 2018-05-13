package yogur.tree.declaration;

import yogur.utils.CompilationException;
import yogur.ididentification.IdentifierTable;

public interface FunctionOrVarDeclaration extends Declaration {
	void performInsertIdentifierAnalysis(IdentifierTable table) throws CompilationException;

	void performBodyIdentifierAnalysis(IdentifierTable table) throws CompilationException;

	String getName();
}
