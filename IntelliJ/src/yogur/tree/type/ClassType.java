package yogur.tree.type;

import yogur.error.CompilationException;
import yogur.ididentification.IdentifierAnalyzer;
import yogur.ididentification.IdentifierTable;
import yogur.tree.declaration.ClassDeclaration;
import yogur.tree.declaration.Declaration;
import yogur.typeidentification.MetaType;

public class ClassType extends Type {
	private String name;

	private ClassDeclaration declaration;

	public ClassType(String name) {
		this.name = name;
	}

	public ClassType(ClassDeclaration declaration) {
		name = declaration.getName();
		this.declaration = declaration;
	}

	public String getName() {
		return name;
	}

	public ClassDeclaration getDeclaration() {
		return declaration;
	}

	@Override
	public void performIdentifierAnalysis(IdentifierTable table) throws CompilationException {
		Declaration dec = table.searchId(name, getLine(), getColumn());
		if (dec instanceof ClassDeclaration) {
			declaration = (ClassDeclaration)dec;
		} else {
			throw new CompilationException("Expected class identifier", getLine(), getColumn(),
					CompilationException.Scope.IdentificatorIdentification);
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof ClassType)) {
			return false;
		}
		ClassType other = (ClassType)obj;
		return other.name.equals(name);
	}

	@Override
	public String toString() {
		return name;
	}
}
