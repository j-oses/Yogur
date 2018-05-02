package yogur.tree;

import yogur.error.CompilationException;
import yogur.ididentification.IdIdentifier;
import yogur.typeidentification.MetaType;

public interface AbstractTreeNode {
	void performIdentifierAnalysis(IdIdentifier table) throws CompilationException;
	MetaType performTypeAnalysis(IdIdentifier idTable) throws CompilationException;
}
