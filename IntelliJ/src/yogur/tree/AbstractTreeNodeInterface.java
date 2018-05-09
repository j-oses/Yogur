package yogur.tree;

import yogur.error.CompilationException;
import yogur.ididentification.IdIdentifier;
import yogur.typeidentification.MetaType;

public interface AbstractTreeNodeInterface {
	void performIdentifierAnalysis(IdIdentifier table) throws CompilationException;
	MetaType analyzeType(IdIdentifier idTable) throws CompilationException;
	MetaType performTypeAnalysis(IdIdentifier idTable) throws CompilationException;
	int performMemoryAnalysis(int currentOffset, int currentDepth);

	void setLine(int line);

	void setColumn(int column);

	int getLine();

	int getColumn();

	void setLineCol(int line, int col);
}
