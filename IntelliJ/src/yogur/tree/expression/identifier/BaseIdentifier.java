package yogur.tree.expression.identifier;

import yogur.error.CompilationException;
import yogur.ididentification.IdIdentifier;
import yogur.tree.declaration.Declaration;
import yogur.tree.statement.VarDeclaration;
import yogur.typeidentification.MetaType;

public class BaseIdentifier extends VarIdentifier {
	private String name;

	private Declaration declaration;
	private int nestedDepth;

	public BaseIdentifier(String name) {
		this.name = name;
	}

	public Declaration getDeclaration() {
		return declaration;
	}

	public int getUseNestedDepth() {
		return nestedDepth;
	}

	public int getDefNestedDepth() {
		return (declaration instanceof VarDeclaration) ? ((VarDeclaration)declaration).getNestingDepth() : -1;
	}

	@Override
	public void performIdentifierAnalysis(IdIdentifier table) throws CompilationException {
		declaration = table.searchId(name, getLine(), getColumn());
	}

	@Override
	public MetaType analyzeType(IdIdentifier idTable) throws CompilationException {
		return declaration.performTypeAnalysis(idTable);
	}

	@Override
	public int performMemoryAnalysis(int currentOffset, int currentDepth) {
		nestedDepth = currentDepth;
		return currentOffset;
	}
}
