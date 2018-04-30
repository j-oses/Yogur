package yogur.tree;

import yogur.error.CompilationException;
import yogur.ididentification.IdIdentifier;

public class BaseType implements Type {
	private String name;

	public BaseType(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public void performIdentifierAnalysis(IdIdentifier table) throws CompilationException {

	}
}
