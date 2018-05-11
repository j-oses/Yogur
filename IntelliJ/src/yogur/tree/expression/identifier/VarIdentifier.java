package yogur.tree.expression.identifier;

import yogur.codegen.PMachineOutputStream;
import yogur.tree.AbstractTreeNode;
import yogur.tree.declaration.Declaration;
import yogur.tree.expression.Expression;

import java.io.IOException;

public abstract class VarIdentifier extends Expression {
	public abstract Declaration getDeclaration();
}
