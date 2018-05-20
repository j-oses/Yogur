package yogur.tree.declaration;

import yogur.utils.CompilationException;
import yogur.ididentification.IdentifierTable;

public interface FunctionOrVarDeclaration extends Declaration {
	/**
	 * Performs the first half of the identifier analysis, saving ids to the table.
	 * @param table the identifier table.
	 * @throws CompilationException if an identifier is already declared.
	 */
	void performInsertIdentifierAnalysis(IdentifierTable table) throws CompilationException;

	/**
	 * Performs the second half of the identifier analysis, analyzing the bodies recursively.
	 * @param table the identifier table.
	 * @throws CompilationException if an identifier is already declared or if it is not declared when read.
	 */
	void performBodyIdentifierAnalysis(IdentifierTable table) throws CompilationException;

	/**
	 * The identifier associated with the declaration.
	 * @return the name string.
	 */
	String getName();

	/**
	 * If the declaration is inside a class, saves the class.
	 * @param clazz the class declaration.
	 */
	void setDeclaredOnClass(ClassDeclaration clazz);
}
