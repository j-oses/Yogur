package yogur.tree.expression;

import yogur.codegen.PMachineOutputStream;
import yogur.tree.AbstractTreeNode;

import java.io.IOException;

public abstract class Expression extends AbstractTreeNode {
	public abstract void generateCodeR(PMachineOutputStream stream) throws IOException;
}
