package yogur.tree.declaration.declarator;

import yogur.codegen.PMachineOutputStream;
import yogur.tree.AbstractTreeNode;

import java.io.IOException;

public abstract class Declarator extends AbstractTreeNode {
	public abstract void generateCodeL(PMachineOutputStream stream) throws IOException;

	public int getSize() {
		return metaType.sizeOf();
	}
}
