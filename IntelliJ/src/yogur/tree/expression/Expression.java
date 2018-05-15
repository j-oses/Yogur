package yogur.tree.expression;

import yogur.codegen.IntegerReference;
import yogur.codegen.PMachineOutputStream;
import yogur.tree.AbstractTreeNode;

import java.io.IOException;

public abstract class Expression extends AbstractTreeNode {
	public abstract void generateCodeR(PMachineOutputStream stream) throws IOException;

	public void generateCodeA(PMachineOutputStream stream) throws IOException {
		generateCodeR(stream);
		if (metaType.getSize() > 1) {
			stream.appendInstruction("movs", metaType.getSize());
		}
	}
}
