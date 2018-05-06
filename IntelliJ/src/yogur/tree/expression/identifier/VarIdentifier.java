package yogur.tree.expression.identifier;

import yogur.codegen.PMachineOutputStream;
import yogur.tree.AbstractTreeNode;
import yogur.tree.declaration.Declaration;
import yogur.tree.expression.Expression;

import java.io.IOException;

public abstract class VarIdentifier extends Expression {
	public abstract Declaration getDeclaration();

	public abstract int getOffset();

	@Override
	public void generateCode(PMachineOutputStream stream) throws IOException {
		stream.appendInstruction("ldc", getOffset());
	}
}
