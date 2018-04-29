package yogur.tree.declaration.declarator;

import yogur.tree.declaration.Declaration;
import yogur.tree.expression.identifier.ArrayIndex;

public class ArrayDeclarator implements Declarator {
	private Declarator declarator;
	private ArrayIndex index;

	public ArrayDeclarator(Declarator declarator, ArrayIndex index) {
		this.declarator = declarator;
		this.index = index;
	}
}
