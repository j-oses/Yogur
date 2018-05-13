package yogur.tree.declaration;

import yogur.codegen.IntegerReference;
import yogur.error.CompilationException;
import yogur.ididentification.IdentifierTable;
import yogur.tree.AbstractTreeNode;
import yogur.tree.type.Type;
import yogur.tree.declaration.declarator.BaseDeclarator;
import yogur.typeidentification.MetaType;

public class Argument extends AbstractTreeNode implements Declaration {
	private BaseDeclarator declarator;
	private Type type;

	private int offset;

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

	public int getOffset() {
		return offset;
	}

	@Override
	public String getDeclarationDescription() {
		return "Var declaration";
	}

	@Override
	public void performIdentifierAnalysis(IdentifierTable table) throws CompilationException {
		table.insertId(declarator.getIdentifier(), this);
		type.performIdentifierAnalysis(table);
	}

	@Override
	public MetaType analyzeType(IdentifierTable idTable) throws CompilationException {
		return type.performTypeAnalysis(idTable);
	}

	@Override
	public void performMemoryAssignment(IntegerReference currentOffset) {
		offset = currentOffset.getValue();
		currentOffset.add(type.getSize());
		System.out.println("Assigned offset " + offset + " to variable " + declarator.getIdentifier() + " with size " + type.getSize());
	}
}
