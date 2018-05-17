package yogur.tree.declaration;

import yogur.codegen.IntegerReference;
import yogur.codegen.PMachineOutputStream;
import yogur.tree.expression.identifier.BaseIdentifier;
import yogur.utils.CompilationException;
import yogur.ididentification.IdentifierTable;
import yogur.tree.AbstractTreeNode;
import yogur.tree.type.Type;
import yogur.tree.declaration.declarator.BaseDeclarator;
import yogur.typeidentification.MetaType;
import yogur.utils.Log;

import java.io.IOException;

public class Argument extends AbstractTreeNode implements Declaration {
	private BaseIdentifier declarator;
	private Type type;

	private int offset;
	private int nestingDepth;
	private ClassDeclaration declaredOnClass = null;

	public Argument(String declarator, Type type) {
		this(new BaseIdentifier(declarator), type);
	}

	public Argument(BaseIdentifier declarator, Type type) {
		this.declarator = declarator;
		this.type = type;
	}

	public BaseIdentifier getDeclarator() {
		return declarator;
	}

	public int getOffset() {
		return offset;
	}

	public int getNestingDepth() {
		return nestingDepth;
	}

	public boolean isDeclaredOnClass() {
		return declaredOnClass != null;
	}

	public void setDeclaredOnClass(ClassDeclaration clazz) {
		this.declaredOnClass = clazz;
		if (isDeclaredOnClass()) {
			Log.debug("Set declaredOnClass to true for variable " + declarator.getName());
		}
	}

	@Override
	public String getDeclarationDescription() {
		return "Var declaration";
	}

	@Override
	public void performIdentifierAnalysis(IdentifierTable table) throws CompilationException {
		table.insertId(declarator.getName(), this);
		declarator.performIdentifierAnalysis(table);
		type.performIdentifierAnalysis(table);
	}

	@Override
	public MetaType analyzeType() throws CompilationException {
		return type.performTypeAnalysis();
	}

	@Override
	public void performMemoryAssignment(IntegerReference currentOffset, IntegerReference nestingDepth) {
		offset = currentOffset.getValue();
		currentOffset.add(type.getSize());
		this.nestingDepth = nestingDepth.getValue();
		Log.debug("Assigned offset " + offset + " to variable " + declarator.getName() + " with size " + type.getSize());
		Log.debug("Assigned DEFINITION nesting depth " + this.nestingDepth + " to variable " + declarator.getName());
	}

	@Override
	public void generateCode(PMachineOutputStream stream) {
		// Do nothing
	}
}
