package yogur.tree;

import yogur.codegen.IntegerReference;
import yogur.utils.CompilationException;
import yogur.ididentification.IdentifierTable;
import yogur.typeanalysis.MetaType;

/**
 * The interface which all AST nodes must conform to. A default implementation is provided in
 * {@link AbstractTreeNode}, and is implemented for every node. This interface exists to circumvent the need
 * of multiple inheritance (for example, we want certain nodes to be both subclasses of Declaration and
 * Statement. Thus, one of them will be an interface instead of a class, but it has then to inherit the properties
 * of an AbstractTreeNode, and those are contained in this interface).
 */
public interface AbstractTreeNodeInterface {
	/**
	 * Performs the identifier analysis for the node.
	 * @param table the identifier table on its current state.
	 * @throws CompilationException if an identifier is referenced without being declared, if an identifier
	 * is duplicated, etc.
	 */
	void performIdentifierAnalysis(IdentifierTable table) throws CompilationException;

	/**
	 * Analyzes the type of the expression and returns it. This function will be called by
	 * {@link #performTypeAnalysis()}. It should also save the type on the node if it will be needed later.
	 *
	 * Note that dot accesses cannot be identified on the previous phase (identifier identification) and will be
	 * identified on type analysis.
	 *
	 * @return the (meta)type.
	 * @throws CompilationException if there is a type error at any point.
	 */
	MetaType analyzeType() throws CompilationException;

	/**
	 * Analyzes the type of the expression and returns it. In most implementations, this function will call
	 * {@link #analyzeType()} on the node's children.
	 *
	 * Note that dot accesses cannot be identified on the previous phase (identifier identification) and will be
	 * identified on type analysis.
	 *
	 * @return the (meta)type.
	 * @throws CompilationException if there is a type error at any point.
	 */
	MetaType performTypeAnalysis() throws CompilationException;

	/**
	 * Performs the memory assignment: offsets, nesting depths, etc.
	 * @param currentOffset an integer reference with the current offset. May be modified by the call.
	 * @param nestingDepth an integer reference with the current nesting depth. May be modified by the call.
	 */
	void performMemoryAssignment(IntegerReference currentOffset, IntegerReference nestingDepth);

	/**
	 * Sets the line where the node was declared on the original file.
	 * @param line
	 */
	void setLine(int line);

	/**
	 * Sets the column where the node was declared on the original file.
	 * @param column
	 */
	void setColumn(int column);

	/**
	 * Returns the line where the node was declared on the original file.
	 * @return
	 */
	int getLine();

	/**
	 * Returns the column where the node was declared on the original file.
	 * @return
	 */
	int getColumn();

	/**
	 * Sets the line and column in a single call.
	 * @param line
	 * @param col
	 */
	void setLineCol(int line, int col);
}
