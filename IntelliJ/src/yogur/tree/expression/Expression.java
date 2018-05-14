package yogur.tree.expression;

import yogur.codegen.IntegerReference;
import yogur.codegen.PMachineOutputStream;
import yogur.tree.AbstractTreeNode;

import java.io.IOException;

public abstract class Expression extends AbstractTreeNode {
	public abstract void generateCodeR(PMachineOutputStream stream) throws IOException;

	@Override
	public void performMemoryAssignment(IntegerReference currentOffset, IntegerReference nestingDepth) {
		// Do nothing. Expressions don't have to assign memory. That's only for declarations!
	}
}
