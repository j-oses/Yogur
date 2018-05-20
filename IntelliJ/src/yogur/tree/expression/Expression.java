package yogur.tree.expression;

import yogur.codegen.PMachineOutputStream;
import yogur.tree.statement.Statement;
import yogur.tree.type.BaseType;
import yogur.tree.type.ClassType;
import yogur.utils.Log;

import java.io.IOException;

public abstract class Expression extends Statement {
	/**
	 * Whether it forms a valid expression for the left side of an assignment.
	 * @return
	 */
	public boolean isAssignable() {
		return false;
	}

	/**
	 * The depth of this expression on the evaluation stack.
	 * @return
	 */
	public abstract int getDepthOnStack();

	@Override
	public int getMaxDepthOnStack() {
		return getDepthOnStack();
	}

	/**
	 * Generate code for the right hand side of an assignment.
	 * @param stream the output code stream.
	 * @throws IOException if there is an error writing to file.
	 */
	public abstract void generateCodeR(PMachineOutputStream stream) throws IOException;

	/**
	 * Generate code for the left hand side of an assignment. It will only be called if {@link #isAssignable()} is true.
	 * @param stream the output code stream.
	 * @throws IOException if there is an error writing to file.
	 */
	public void generateCodeL(PMachineOutputStream stream) throws IOException {
		generateCodeR(stream);
	}

	/**
	 * Generate code for when the expression is passed as an argument.
	 * @param stream the output code stream.
	 * @throws IOException if there is an error writing to file.
	 */
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
