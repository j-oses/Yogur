package yogur.tree.statement;

import yogur.tree.AbstractTreeNode;
import yogur.tree.StatementOrDeclaration;

public abstract class Statement extends AbstractTreeNode implements StatementOrDeclaration {
	/**
	 * Returns the maximum depth on the evaluation stack, to help working out the depth needed for a function call.
	 * @return
	 */
	public abstract int getMaxDepthOnStack();
}
