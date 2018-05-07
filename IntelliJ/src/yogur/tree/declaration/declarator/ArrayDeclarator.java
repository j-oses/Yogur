package yogur.tree.declaration.declarator;

import yogur.codegen.PMachineOutputStream;
import yogur.error.CompilationException;
import yogur.ididentification.IdIdentifier;
import yogur.tree.expression.identifier.ArrayIndex;
import yogur.tree.type.ArrayType;
import yogur.typeidentification.MetaType;

import java.io.IOException;

import static yogur.error.CompilationException.Scope;
import static yogur.error.CompilationException.Scope.TypeAnalyzer;

public class ArrayDeclarator extends Declarator {
	private Declarator declarator;
	private ArrayIndex index;

	public ArrayDeclarator(Declarator declarator, ArrayIndex index) {
		this.declarator = declarator;
		this.index = index;
	}

	@Override
	public void performIdentifierAnalysis(IdIdentifier table) throws CompilationException {
		declarator.performIdentifierAnalysis(table);
		index.performIdentifierAnalysis(table);
	}

	@Override
	public MetaType analyzeType(IdIdentifier idTable) throws CompilationException {
		MetaType leftType = declarator.performTypeAnalysis(idTable);
		if (leftType instanceof ArrayType) {
			if (index.returnsSingleElement()) {
				return ((ArrayType) leftType).getInternalType();
			} else {
				return leftType;
			}
		}

		throw new CompilationException("Performing [] operator on a non-array type: " + leftType,
				getLine(), getColumn(), TypeAnalyzer);
	}

	@Override
	public void generateCodeL(PMachineOutputStream stream) throws IOException {
		declarator.generateCodeL(stream);
		this.generateCodeI(stream);
	}

	public void generateCodeI(PMachineOutputStream stream) throws IOException {
		// FIXME: We will temporarily assume that there is no range index access
		index.getOffset().generateCodeR(stream);
		stream.appendInstruction("ixa", declarator.getSize());
		stream.appendInstruction("dec", declarator.getSize());	// FIXME: What does this do?
	}
}
