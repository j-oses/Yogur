package yogur.tree.expression;

import yogur.codegen.IntegerReference;
import yogur.codegen.PMachineOutputStream;
import yogur.tree.declaration.FuncDeclaration;
import yogur.utils.CompilationException;
import yogur.ididentification.IdentifierTable;
import yogur.tree.expression.identifier.Identifier;
import yogur.typeanalysis.FunctionType;
import yogur.typeanalysis.MetaType;
import yogur.typeanalysis.VoidType;

import java.io.IOException;
import java.util.List;

import static yogur.utils.CompilationException.Scope.TypeAnalyzer;

public class FunctionCall extends Expression {
	private Expression function;
	private List<Expression> expressions;

	private FuncDeclaration declaration;
	private int nestingDepth;

	public FunctionCall(Expression function, List<Expression> expressions) {
		this.function = function;
		this.expressions = expressions;
	}

	@Override
	public int getDepthOnStack() {
		// FIXME: Not correct
		int result = function.getDepthOnStack();
		for (Expression e: expressions) {
			result += e.getDepthOnStack();
		}
		return result;
	}

	@Override
	public void performIdentifierAnalysis(IdentifierTable table) throws CompilationException {
		function.performIdentifierAnalysis(table);
		for (Expression e: expressions) {
			e.performIdentifierAnalysis(table);
		}
	}

	@Override
	public MetaType analyzeType() throws CompilationException {
		MetaType type = function.performTypeAnalysis();

		if (type instanceof FunctionType) {
			if (function instanceof Identifier) {
				// If the type is a function type, the declaration must be a FuncDeclaration
				declaration = (FuncDeclaration) ((Identifier) function).getDeclaration();
			}

			FunctionType fType = (FunctionType) type;
			int i = 0;

			for (Expression exp : expressions) {
				MetaType argType = exp.performTypeAnalysis();
				if (!fType.isValidArgument(i, argType)) {
					throw new CompilationException("Invalid function " + (i + 1) + "th argument with type: "
							+ argType, getLine(), getColumn(), TypeAnalyzer);
				}
				i++;
			}

			return fType.getReturnType() != null ? fType.getReturnType() : new VoidType();
		}

		throw new CompilationException("Trying to call a function on the non-function object with type: " + type,
				getLine(), getColumn(), TypeAnalyzer);
	}

	@Override
	public void performMemoryAssignment(IntegerReference currentOffset, IntegerReference nestingDepth) {
		function.performMemoryAssignment(currentOffset, nestingDepth);
		for (Expression e: expressions) {
			e.performMemoryAssignment(currentOffset, nestingDepth);
		}
		this.nestingDepth = nestingDepth.getValue();
	}

	@Override
	public void generateCodeR(PMachineOutputStream stream) throws IOException {
		stream.appendInstruction("mst", nestingDepth);

		if (declaration.isDeclaredOnClass()) {
			// The first parameter should be the class on which is declared
			// There are two options: either function should be a DotIdentifier and thus generate the correct
			// code for the class, or it is a BaseIdentifier and it should do the same thing.
			function.generateCodeL(stream);
		}

		for (Expression e: expressions) {
			e.generateCodeA(stream);
		}

		stream.appendLabelledInstruction("cup", declaration.getFormalParameterLength(), declaration.getLabel());
	}
}
