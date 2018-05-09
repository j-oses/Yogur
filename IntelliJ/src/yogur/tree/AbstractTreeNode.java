package yogur.tree;

import yogur.error.CompilationException;
import yogur.ididentification.IdIdentifier;
import yogur.typeidentification.MetaType;

public abstract class AbstractTreeNode implements AbstractTreeNodeInterface {
	private int line = -1;
	private int column = -1;

	protected MetaType metaType;

	public abstract void performIdentifierAnalysis(IdIdentifier table) throws CompilationException;

	public abstract MetaType analyzeType(IdIdentifier idTable) throws CompilationException;

	@Override
	public MetaType performTypeAnalysis(IdIdentifier idTable) throws CompilationException {
		if (getMetaType() == null) {
			metaType = analyzeType(idTable);
		}

		return getMetaType();
	}

	@Override
	public int performMemoryAnalysis(int currentOffset, int currentDepth) {
		// Default implementation does nothing
		return currentOffset;
	}

	public void setLine(int line) {
		this.line = line;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public int getLine() {
		return line;
	}

	public int getColumn() {
		return column;
	}

	public MetaType getMetaType() {
		return metaType;
	}

	public void setLineCol(int line, int col) {
		setLine(line);
		setColumn(col);
	}
}
