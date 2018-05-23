package yogur.tree.expression.identifier;

import yogur.codegen.IntegerReference;
import yogur.codegen.PMachineOutputStream;
import yogur.tree.declaration.FuncDeclaration;
import yogur.utils.CompilationException;
import yogur.ididentification.IdentifierTable;
import yogur.tree.declaration.Argument;
import yogur.tree.declaration.Declaration;
import yogur.typeanalysis.MetaType;
import yogur.utils.Log;

import java.io.IOException;

public class BaseIdentifier extends Identifier {
	private String name;

	private Declaration declaration;
	private int nestingDepth;

	public static final String THIS_NAME = "this";

	public BaseIdentifier(String name) {
		this.name = name;
	}

	/**
	 * To be used internally to mock a base identifier. A node created with this constructor is not
	 * expected to be inserted in the tree.
	 * @param argument the declaration associated to this identifier.
	 */
	public BaseIdentifier(Argument argument) {
		this.name = argument.getDeclarator().getName();
		this.declaration = argument;
		this.nestingDepth = argument.getNestingDepth();
	}

	@Override
	public boolean isAssignable() {
		return declaration instanceof Argument && !THIS_NAME.equals(name);
	}

	public Declaration getDeclaration() {
		return declaration;
	}

	public String getName() {
		return name;
	}

	private boolean isThis() {
		return THIS_NAME.equals(name);
	}

	@Override
	public int getDepthOnStack() {
		return 2;	// At most 2, may be less
	}

	@Override
	public void performIdentifierAnalysis(IdentifierTable table) throws CompilationException {
		declaration = table.searchId(name, getLine(), getColumn());
	}

	@Override
	public MetaType analyzeType() throws CompilationException {
		return declaration.performTypeAnalysis();
	}

	@Override
	public void performMemoryAssignment(IntegerReference currentOffset, IntegerReference nestingDepth) {
		this.nestingDepth = nestingDepth.getValue();
		Log.debug("Assigned USE nesting depth " + this.nestingDepth + " for identifier " + name + " in line " + getLine());
	}

	@Override
	public void generateCodeL(PMachineOutputStream stream) throws IOException {
		if (isThis()) {
			// Referencing to the class on which the function is declared: load the address at the first parameter
			stream.appendInstruction("lod", 0, FuncDeclaration.START_PARAMETER_INDEX);
			return;
		}

		if (declaration instanceof Argument) {
			Argument arg = (Argument) declaration;

			if (arg.isDeclaredOnClass()) {
				// We are accessing a class field within a function
				// We load the (absolute) direction of the start of the parameter, and we add the actual offset
				// inside the class to it.
				stream.appendInstruction("lod", 0, FuncDeclaration.START_PARAMETER_INDEX);
				stream.appendInstruction("inc", arg.getOffset());
			} else if (arg.isPassedByReference()) {
				stream.appendInstruction("lod", 0, arg.getOffset());
			} else {
				stream.appendInstruction("lda", nestingDepth - arg.getNestingDepth(), arg.getOffset());
			}
		} else {
			// For a function, that the code has reached this point means that we are calling a class function
			// from the same class. In that case, the value we want is on the location of the first parameter
			// of the current function
			stream.appendInstruction("lod", 0, FuncDeclaration.START_PARAMETER_INDEX);
		}
	}
}
