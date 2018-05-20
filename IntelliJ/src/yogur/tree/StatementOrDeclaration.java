package yogur.tree;

import yogur.codegen.PMachineOutputStream;

import java.io.IOException;

public interface StatementOrDeclaration extends AbstractTreeNodeInterface {
	/**
	 * Translates the statement or declaration into code, at top-level.
	 * @param stream the code output stream.
	 * @throws IOException if the output file cannot be written.
	 */
	void generateCode(PMachineOutputStream stream) throws IOException;
}
