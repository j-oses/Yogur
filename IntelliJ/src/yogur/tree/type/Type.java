package yogur.tree.type;

import yogur.tree.AbstractTreeNode;
import yogur.typeidentification.MetaType;

public abstract class Type extends AbstractTreeNode implements MetaType {
	abstract Type getBaseType();
}
