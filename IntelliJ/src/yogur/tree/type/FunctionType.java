package yogur.tree.type;

import yogur.error.CompilationException;
import yogur.ididentification.IdIdentifier;
import yogur.typeidentification.MetaType;

import java.util.List;

/**
 * A function type can not actually be declared. However, for the purposes of the type analysis,
 * it is useful to have the functions return a type.
 */
public class FunctionType implements MetaType {
	private List<MetaType> argumentTypes;

	private MetaType returnType;

	public FunctionType(List<MetaType> argumentTypes) {
		this(argumentTypes, null);
	}

	public FunctionType(List<MetaType> argumentTypes, MetaType returnType) {
		this.argumentTypes = argumentTypes;
		this.returnType = returnType;
	}

	public MetaType getReturnType() {
		return returnType;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof FunctionType)) {
			return false;
		}

		FunctionType other = (FunctionType)obj;
		return other.argumentTypes.equals(argumentTypes)
				&& ((other.returnType == null && returnType == null)
				|| (returnType != null) && returnType.equals(other.returnType));
	}

	public boolean isValidArgument(int i, MetaType argType) {
		if (0 <= i && i < argumentTypes.size()) {
			return argType.equals(argumentTypes.get(i));
		}
		return false;
	}

	@Override
	public String toString() {
		String result = "(";

		for (MetaType arg: argumentTypes) {
			if (!result.equals("(")) {
				result += ", ";
			}
			result += arg.toString();
		}

		result += ")";

		if (returnType != null) {
			result += " -> " + returnType.toString();
		}

		return result;
	}
}
