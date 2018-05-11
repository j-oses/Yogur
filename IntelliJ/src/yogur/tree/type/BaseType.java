package yogur.tree.type;

import yogur.error.CompilationException;
import yogur.ididentification.IdIdentifier;
import yogur.tree.declaration.Declaration;
import yogur.typeidentification.MetaType;

import java.util.Map;

public class BaseType extends Type {
	public enum PredefinedType {
		Int, Bool;

		public static boolean hasValue(String str) {
			for (PredefinedType type: values()) {
				if (type.name().equals(str)) {
					return true;
				}
			}
			return false;
		}
	}

	private String name;

	private Map<String, Declaration> classInfo;

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
	Type getBaseType() {
		return this;
	}

	@Override
	public void performIdentifierAnalysis(IdIdentifier table) throws CompilationException {
		// Do nothing
	}

	@Override
	public MetaType analyzeType(IdIdentifier idTable) throws CompilationException {
		if (PredefinedType.hasValue(name) || idTable.hasClassNamed(name)) {
			classInfo = idTable.getClassInfo(name);
			return this;
		}
		throw new CompilationException("No type declared with name: " + name, getLine(),
				getColumn(), CompilationException.Scope.TypeAnalyzer);
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

	@Override
	public int sizeOf() {
		if (PredefinedType.hasValue(name)) {
			return 1;
		} else {
			// FIXME: Return a value
			return -1;
		}
	}
}
