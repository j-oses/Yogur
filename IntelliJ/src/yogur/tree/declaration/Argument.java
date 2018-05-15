package yogur.tree.declaration;

import yogur.codegen.IntegerReference;
import yogur.codegen.PMachineOutputStream;
import yogur.utils.CompilationException;
import yogur.ididentification.IdentifierTable;
import yogur.tree.AbstractTreeNode;
import yogur.tree.type.Type;
import yogur.tree.declaration.declarator.BaseDeclarator;
import yogur.typeidentification.MetaType;
import yogur.utils.Log;

public class Argument extends AbstractTreeNode implements Declaration {
	private BaseDeclarator declarator;
	private Type type;

	private int offset;
	private int nestingDepth;
	private boolean declaredOnClass = false;

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

	public void setDeclaredOnClass(boolean declaredOnClass) {
		this.declaredOnClass = declaredOnClass;
		if (declaredOnClass) {
			Log.debug("Set declaredOnClass to true for variable " + declarator.getIdentifier());
		}
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
	public void performMemoryAssignment(IntegerReference currentOffset, IntegerReference nestingDepth) {
		offset = currentOffset.getValue();
		currentOffset.add(type.getSize());
		this.nestingDepth = nestingDepth.getValue();
		Log.debug("Assigned offset " + offset + " to variable " + declarator.getIdentifier() + " with size " + type.getSize());
		Log.debug("Assigned DEFINITION nesting depth " + this.nestingDepth + " to variable " + declarator.getIdentifier());
	}

	@Override
	public void generateCode(PMachineOutputStream stream) {
		// Do nothing
	}
}
