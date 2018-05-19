package yogur.tree.expression;

import yogur.codegen.PMachineOutputStream;
import yogur.tree.statement.Statement;
import yogur.tree.type.BaseType;
import yogur.tree.type.ClassType;
import yogur.utils.Log;

import java.io.IOException;

public abstract class Expression extends Statement {
	public boolean isAssignable() {
		return false;
	}

	public abstract int getDepthOnStack();

	@Override
	public int getMaxDepthOnStack() {
		return getDepthOnStack();
	}

	public abstract void generateCodeR(PMachineOutputStream stream) throws IOException;

	public void generateCodeL(PMachineOutputStream stream) throws IOException {
		generateCodeR(stream);
	}

	public void generateCodeA(PMachineOutputStream stream) throws IOException {
		if (metaType instanceof BaseType) {
			generateCodeR(stream);
		} else {
			generateCodeL(stream);
			if (!(metaType instanceof ClassType)) {
				stream.appendInstruction("movs", metaType.getSize());
			}
		}
	}

	@Override
	public void generateCode(PMachineOutputStream stream) throws IOException {
		if (!(this instanceof FunctionCall)) {
			Log.warning(getLine() + ":" + getColumn() + " | Expression at statement level is not a function call");
		}

		generateCodeR(stream);
	}
}
