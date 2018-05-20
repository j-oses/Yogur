package yogur.tree.expression.identifier;

import yogur.codegen.PMachineOutputStream;
import yogur.tree.AbstractTreeNode;
import yogur.tree.declaration.Declaration;
import yogur.tree.expression.Expression;

import java.io.IOException;

/**
 * Abstract class representing an identifier.
 */
public abstract class VarIdentifier extends Expression {
	/**
	 * Returns the reference to the node of the AST where the identifier was originally declarated.
	 * @return the node.
	 */
	public abstract Declaration getDeclaration();

	@Override
	public void generateCodeR(PMachineOutputStream stream) throws IOException {
		generateCodeL(stream);
		stream.appendInstruction("ind");
	}
}
