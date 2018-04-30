package yogur.tree;

import yogur.error.CompilationException;
import yogur.ididentification.IdIdentifier;

public interface AbstractTreeNode {
	void performIdentifierAnalysis(IdIdentifier table) throws CompilationException;
}
