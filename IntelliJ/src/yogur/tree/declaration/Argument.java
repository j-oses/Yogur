package yogur.tree.declaration;

import yogur.error.CompilationException;
import yogur.ididentification.IdIdentifier;
import yogur.tree.type.Type;
import yogur.tree.declaration.declarator.BaseDeclarator;
import yogur.typeidentification.MetaType;

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

	@Override
	public MetaType performTypeAnalysis(IdIdentifier idTable) throws CompilationException {
		return type.performTypeAnalysis(idTable);
	}
}
