package yogur.tree.declaration.declarator;

import yogur.codegen.IntegerReference;
import yogur.tree.AbstractTreeNode;

public abstract class Declarator extends AbstractTreeNode {
	@Override
	public void performMemoryAssignment(IntegerReference currentOffset) {
		// Do nothing
	}
}
