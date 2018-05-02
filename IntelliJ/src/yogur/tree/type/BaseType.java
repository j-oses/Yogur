package yogur.tree.type;

import yogur.error.CompilationException;
import yogur.ididentification.IdIdentifier;
import yogur.typeidentification.MetaType;

public class BaseType implements Type {
	public enum PredefinedType {
		Int, Bool;

		public static boolean hasValue(String str) {
			for (PredefinedType type: values()) {
				if (type.equals(str)) {
					return true;
				}
			}
			return false;
		}
	}

	private String name;

	public BaseType(String name) {
		this.name = name;
	}

	public BaseType(PredefinedType preType) {
		this(preType.name());
	}

	public String getName() {
		return name;
	}

	@Override
	public void performIdentifierAnalysis(IdIdentifier table) throws CompilationException {
		// Do nothing
	}

	@Override
	public MetaType performTypeAnalysis(IdIdentifier idTable) throws CompilationException {
		if (PredefinedType.hasValue(name) || idTable.hasClassNamed(name)) {
			return this;
		}
		throw new CompilationException("No type declared with name: " + name, CompilationException.Scope.TypeAnalyzer);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof BaseType)) {
			return false;
		}
		BaseType other = (BaseType)obj;
		return other.name.equals(name);
	}

	@Override
	public String toString() {
		return name;
	}
}