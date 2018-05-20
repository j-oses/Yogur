package yogur.tree.type;

import yogur.codegen.IntegerReference;
import yogur.utils.CompilationException;
import yogur.ididentification.IdentifierTable;
import yogur.tree.AbstractTreeNode;
import yogur.typeidentification.MetaType;

public abstract class Type extends AbstractTreeNode implements MetaType {
	/**
	 * A helper method to facilitate parsing.
	 * @param name the name of the type to be parsed.
	 * @return the type.
	 */
	public static Type fromName(String name) {
		if (BaseType.PredefinedType.hasValue(name)) {
			return new BaseType(name);
		} else {
			return new ClassType(name);
		}
	}

	@Override
	public MetaType analyzeType() throws CompilationException {
		return this;
	}

	@Override
	public void performMemoryAssignment(IntegerReference currentOffset, IntegerReference nestingDepth) {
		// Do nothing
	}
}
