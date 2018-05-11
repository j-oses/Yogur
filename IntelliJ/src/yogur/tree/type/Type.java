package yogur.tree.type;

import yogur.error.CompilationException;
import yogur.ididentification.IdentifierTable;
import yogur.tree.AbstractTreeNode;
import yogur.tree.expression.identifier.BaseIdentifier;
import yogur.typeidentification.MetaType;

public abstract class Type extends AbstractTreeNode implements MetaType {
	public static Type fromName(String name) {
		if (BaseType.PredefinedType.hasValue(name)) {
			return new BaseType(name);
		} else {
			return new ClassType(name);
		}
	}

	@Override
	public MetaType analyzeType(IdentifierTable idTable) throws CompilationException {
		return this;
	}
}
