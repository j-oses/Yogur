package yogur.tree.declaration;

import yogur.error.CompilationException;
import yogur.ididentification.IdIdentifier;
import yogur.tree.AbstractTreeNode;
import yogur.tree.Type;
import yogur.tree.declaration.declarator.BaseDeclarator;

public class Argument implements Declaration {
	private BaseDeclarator declarator;
	private Type type;

	public Argument(String declarator, Type type) {
		this(new BaseDeclarator(declarator), type);
	}

	public Argument(BaseDeclarator declarator, Type type) {
		this.declarator = declarator;
		this.type = type;
	}

	public BaseDeclarator getDeclarator() {
		return declarator;
	}

	@Override
	public void performIdentifierAnalysis(IdIdentifier table) throws CompilationException {
		table.insertId(declarator.getIdentifier(), this);
		type.performIdentifierAnalysis(table);
	}
}
