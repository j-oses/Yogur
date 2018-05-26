package yogur.tree.declaration;

import yogur.codegen.IntegerReference;
import yogur.codegen.PMachineOutputStream;
import yogur.tree.expression.identifier.BaseIdentifier;
import yogur.tree.type.BaseType;
import yogur.tree.type.ClassType;
import yogur.utils.CompilationException;
import yogur.ididentification.IdentifierTable;
import yogur.tree.AbstractTreeNode;
import yogur.tree.statement.Block;
import yogur.typeanalysis.FunctionType;
import yogur.typeanalysis.MetaType;
import yogur.utils.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FuncDeclaration extends AbstractTreeNode implements FunctionOrVarDeclaration, Declaration {
	private String identifier;
	private List<Argument> arguments;
	private Argument returnArg;		// May be null
	private Block block;

	private ClassDeclaration declaredOnClass = null;
	private int frameStaticLength;
	private String label;
	private String endLabel;
	public static final int START_PARAMETER_INDEX = 5;
	private int formalParameterLength;
	private Argument thisArgument;	// May be null

	public FuncDeclaration(String identifier, List<Argument> arguments, Block block) {
		this(identifier, arguments, null, block);
	}

	public FuncDeclaration(String identifier, List<Argument> arguments, Argument returnArg, Block block) {
		this.identifier = identifier;
		this.arguments = arguments;
		this.returnArg = returnArg;
		this.block = block;

		for (Argument a: arguments) {
			a.setFunctionParameter(true);
		}
	}

	@Override
	public String getName() {
		return identifier;
	}

	@Override
	public String getDeclarationDescription() {
		return "Function declaration";
	}

	@Override
	public void setDeclaredOnClass(ClassDeclaration clazz) {
		this.declaredOnClass = clazz;
	}

	public boolean isDeclaredOnClass() {
		return declaredOnClass != null;
	}

	public String getLabel() {
		return label;
	}

	public int getFormalParameterLength() {
		return formalParameterLength;
	}

	@Override
	public void performIdentifierAnalysis(IdentifierTable table) throws CompilationException {
		performInsertIdentifierAnalysis(table);
		performBodyIdentifierAnalysis(table);
	}

	public void performInsertIdentifierAnalysis(IdentifierTable table) throws CompilationException {
		table.insertId(identifier, this);
	}

	public void performBodyIdentifierAnalysis(IdentifierTable table) throws CompilationException {
		table.openBlock();

		// Add a dummy declaration for the this keyword, if this is a class function
		if (isDeclaredOnClass()) {
			thisArgument = new Argument(BaseIdentifier.THIS_NAME, new ClassType(declaredOnClass));
			thisArgument.setFunctionParameter(true);
			table.insertUncheckedId(BaseIdentifier.THIS_NAME, thisArgument);
		}

		for (Argument a: arguments) {
			a.performIdentifierAnalysis(table);
		}
		if (returnArg != null) {
			returnArg.performIdentifierAnalysis(table);
		}
		block.performIdentifierAnalysis(table, false);	// Closes the block
	}

	@Override
	public MetaType analyzeType() throws CompilationException {
		List<MetaType> argTypes = new ArrayList<>();
		for (Argument a: arguments) {
			argTypes.add(a.performTypeAnalysis());
		}

		if (thisArgument != null) {
			thisArgument.analyzeType();
		}

		MetaType returnType = null;
		if (returnArg != null) {
			returnType = returnArg.performTypeAnalysis();

			if (!(returnType instanceof BaseType)) {
				throw new CompilationException("Function is returning a non-predefined type " + returnType,
						getLine(), getColumn(), CompilationException.Scope.TypeAnalyzer);
			}
		}

		// We have to save the metatype before visiting the block to enable recursion.
		// If we don't do that, the program will enter on an infinite loop.
		metaType = new FunctionType(argTypes, returnType);
		block.performTypeAnalysis();
		return metaType;
	}

	@Override
	public void performMemoryAssignment(IntegerReference currentOffset, IntegerReference nestingDepth) {
		// The normal arguments are given words from index 5
		IntegerReference internalOffset = new IntegerReference(START_PARAMETER_INDEX);
		IntegerReference internalDepth = new IntegerReference(nestingDepth.getValue() + 1);

		if (thisArgument != null) {
			thisArgument.performMemoryAssignment(new IntegerReference(START_PARAMETER_INDEX), internalDepth);
		}

		if (returnArg != null) {
			// The return argument has the first word of the frame
			// It can only be a simple type, so there is no worries about its size
			returnArg.performMemoryAssignment(new IntegerReference(0), internalDepth);
		}

		if (isDeclaredOnClass()) {
			// Will take the class as the first parameter
			// Classes are passed by reference
			internalOffset.add(1);
		}

		for (Argument a: arguments) {
			a.performMemoryAssignment(internalOffset, internalDepth);
		}

		formalParameterLength = internalOffset.getValue() - START_PARAMETER_INDEX;

		block.performMemoryAssignment(internalOffset, internalDepth);

		frameStaticLength = internalOffset.getValue();
	}

	public void generateLabel(PMachineOutputStream stream) {
		endLabel = stream.generateLabelWithUnusedId("endFun");
		label = stream.generateLabel("fun");
	}

	@Override
	public void generateCode(PMachineOutputStream stream) throws IOException {
		if (endLabel == null) {
			generateLabel(stream);
		}

		int maxDepthOnStack = block.getMaxDepthOnStack();
		Log.debug("Max depth on stack for function " + identifier + ": " + maxDepthOnStack);

		// Our procedures may be intermingled with normal code, so we generate them with a jump
		stream.appendLabelledInstruction("ujp", endLabel);
		stream.appendComment("def " + identifier);

		// Actual function code
		stream.appendLabel(label);
		stream.appendInstruction("ssp", frameStaticLength);
		stream.appendInstruction("sep", maxDepthOnStack);
		block.generateCode(stream);

		if (returnArg == null) {
			stream.appendInstruction("retp");
		} else {
			stream.appendInstruction("retf");
		}

		stream.appendLabel(endLabel);
	}
}
