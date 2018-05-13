package yogur.tree.type;

import yogur.codegen.IntegerReference;
import yogur.utils.CompilationException;
import yogur.ididentification.IdentifierTable;
import yogur.tree.AbstractTreeNode;
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

	@Override
	public void performMemoryAssignment(IntegerReference currentOffset) {
		// Do nothing
	}
}
