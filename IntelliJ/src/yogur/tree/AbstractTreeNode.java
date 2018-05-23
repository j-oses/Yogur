package yogur.tree;

import yogur.utils.CompilationException;
import yogur.typeanalysis.MetaType;

/**
 * The default implementation of an AST node. Contains integers for the line and column and also a variable which
 * will store the (meta)type after the type analysis has ended.
 */
public abstract class AbstractTreeNode implements AbstractTreeNodeInterface {
	private int line = -1;
	private int column = -1;

	protected MetaType metaType;

	public abstract MetaType analyzeType() throws CompilationException;

	@Override
	public MetaType performTypeAnalysis() throws CompilationException {
		if (getMetaType() == null) {
			metaType = analyzeType();
		}

		return getMetaType();
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
