package yogur.tree.declaration.declarator;

import yogur.codegen.PMachineOutputStream;
import yogur.utils.CompilationException;
import yogur.ididentification.IdentifierTable;
import yogur.tree.expression.identifier.ArrayIndex;
import yogur.tree.type.ArrayType;
import yogur.typeidentification.MetaType;

import java.io.IOException;

import static yogur.utils.CompilationException.Scope.TypeAnalyzer;

public class ArrayDeclarator extends Declarator {
	private Declarator declarator;
	private ArrayIndex index;

	private int elementSize;
	private int length;

	public ArrayDeclarator(Declarator declarator, ArrayIndex index) {
		this.declarator = declarator;
		this.index = index;
	}

	@Override
	public void performIdentifierAnalysis(IdentifierTable table) throws CompilationException {
		declarator.performIdentifierAnalysis(table);
		index.performIdentifierAnalysis(table);
	}

	@Override
	public MetaType analyzeType(IdentifierTable idTable) throws CompilationException {
		MetaType leftType = declarator.performTypeAnalysis(idTable);
		if (leftType instanceof ArrayType) {
			if (index.returnsSingleElement()) {
				ArrayType leftT = (ArrayType)leftType;
				MetaType internalType = leftT.getInternalType();
				length = leftT.getLength();
				elementSize = internalType.getSize();
				return internalType;
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
		generateCodeI(stream);
	}

	public void generateCodeI(PMachineOutputStream stream) throws IOException {
		// TODO: We will suppose all indices are singular
		index.getOffset().generateCodeR(stream);
		stream.appendInstruction("chk", 0, length - 1);
		stream.appendInstruction("ixa", elementSize);
	}
}
