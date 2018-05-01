package yogur.tree;

import yogur.error.CompilationException;
import yogur.ididentification.IdIdentifier;
import yogur.typeidentification.TypeIdentifier;

public interface AbstractTreeNode {
	void performIdentifierAnalysis(IdIdentifier table) throws CompilationException;
	void performTypeAnalysis(TypeIdentifier table) throws CompilationException;

}
