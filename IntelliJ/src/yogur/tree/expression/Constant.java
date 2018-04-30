package yogur.tree.expression;

import yogur.error.CompilationException;
import yogur.ididentification.IdIdentifier;

public class Constant implements Expression {
	Object value;

	public Constant(Object value) {
		this.value = value;
	}

	@Override
	public void performIdentifierAnalysis(IdIdentifier table) throws CompilationException {

	}
}
