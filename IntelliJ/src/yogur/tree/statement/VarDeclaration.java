package yogur.tree.statement;

import yogur.codegen.IntegerReference;
import yogur.codegen.PMachineOutputStream;
import yogur.tree.declaration.ClassDeclaration;
import yogur.tree.expression.identifier.BaseIdentifier;
import yogur.tree.expression.identifier.VarIdentifier;
import yogur.tree.type.BaseType;
import yogur.utils.CompilationException;
import yogur.ididentification.IdentifierTable;
import yogur.tree.declaration.Argument;
import yogur.tree.declaration.FunctionOrVarDeclaration;
import yogur.tree.expression.Expression;
import yogur.typeidentification.MetaType;
import yogur.utils.Log;

import java.io.IOException;

import static yogur.utils.CompilationException.Scope.TypeAnalyzer;

public class VarDeclaration extends Statement implements FunctionOrVarDeclaration {
	private Argument argument;
	private Expression assignTo;	// May be null

	public VarDeclaration(Argument argument) {
		this(argument, null);
	}

	public VarDeclaration(Argument argument, Expression assignTo) {
		this.argument = argument;
		this.assignTo = assignTo;
	}

	@Override
	public String getName() {
		return argument.getDeclarator().getName();
	}

	@Override
	public String getDeclarationDescription() {
		return "Var declaration";
	}

	public int getOffset() {
		return argument.getOffset();
	}

	public Argument getArgument() {
		return argument;
	}

	@Override
	public int getMaxDepthOnStack() {
		return assignTo != null ? (assignTo.getDepthOnStack() + 1) : 2;
	}

	@Override
	public void setDeclaredOnClass(ClassDeclaration clazz) {
		argument.setDeclaredOnClass(clazz);
	}

	public boolean isDeclaredOnClass() {
		return argument.isDeclaredOnClass();
	}

	@Override
	public void performIdentifierAnalysis(IdentifierTable table) throws CompilationException {
		performInsertIdentifierAnalysis(table);
		performBodyIdentifierAnalysis(table);
	}

	@Override
	public void performInsertIdentifierAnalysis(IdentifierTable table) throws CompilationException {
		argument.performIdentifierAnalysis(table);
	}

	@Override
	public void performBodyIdentifierAnalysis(IdentifierTable table) throws CompilationException {
		if (assignTo != null) {
			assignTo.performIdentifierAnalysis(table);
		}
	}

	@Override
	public MetaType analyzeType() throws CompilationException {
		MetaType argType = argument.performTypeAnalysis();

		if (assignTo == null) {
			return argType;
		}

		MetaType assType = assignTo.performTypeAnalysis();

		if (argType.equals(assType)) {
			return argType;
		}

		throw new CompilationException("Assigning an expression of type: " + assType +
				" to a variable of type: " + argType, getLine(), getColumn(), TypeAnalyzer);
	}

	@Override
	public void performMemoryAssignment(IntegerReference currentOffset, IntegerReference nestingDepth) {
		argument.performMemoryAssignment(currentOffset, nestingDepth);
		if (assignTo != null) {
			assignTo.performMemoryAssignment(currentOffset, nestingDepth);
		}
	}

	@Override
	public void generateCode(PMachineOutputStream stream) throws IOException {
		if (assignTo != null) {
			argument.getDeclarator().generateCodeL(stream);
			assignTo.generateCodeR(stream);
			stream.appendInstruction("sto");
		}
	}
}
