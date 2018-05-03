package yogur.tree.expression.identifier;

import yogur.tree.AbstractTreeNode;
import yogur.tree.declaration.Declaration;
import yogur.tree.expression.Expression;

public abstract class VarIdentifier extends Expression {
	public abstract Declaration getDeclaration();
}
