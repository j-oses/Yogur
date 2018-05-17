package yogur.tree.expression;

import yogur.codegen.IntegerReference;
import yogur.codegen.PMachineOutputStream;
import yogur.tree.AbstractTreeNode;
import yogur.tree.statement.Statement;

import java.io.IOException;

public abstract class Expression extends Statement {
	public boolean isAssignable() {
		return false;
	}

	public abstract void generateCodeR(PMachineOutputStream stream) throws IOException;

	public void generateCodeL(PMachineOutputStream stream) throws IOException {
		generateCodeR(stream);
	}

	public void generateCodeA(PMachineOutputStream stream) throws IOException {
		generateCodeR(stream);
		if (metaType.getSize() > 1) {
			stream.appendInstruction("movs", metaType.getSize());
		}
	}

	@Override
	public void generateCode(PMachineOutputStream stream) throws IOException {
		generateCodeR(stream);
	}
}
