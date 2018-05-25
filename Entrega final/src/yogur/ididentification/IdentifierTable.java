package yogur.ididentification;

import yogur.tree.expression.identifier.BaseIdentifier;
import yogur.utils.CompilationException;
import yogur.tree.declaration.Declaration;
import yogur.tree.type.BaseType;

import java.util.*;

/**
 * A class representing the identifier table for the compiler.
 */
public class IdentifierTable {
	/**
	 * A Deque which will be used as a Stack which contains a Map for each depth level.
	 */
	private Deque<Map<String, Declaration>> idTable = new ArrayDeque<>();

    public IdentifierTable() {
		// The program is a block
    	openBlock();

    	// To prevent redeclarations
    	for (BaseType.PredefinedType type: BaseType.PredefinedType.values()) {
			idTable.peek().put(type.name(), null);
		}
	}

    /**
     * Starts a new block
     */
	public void openBlock() {
        idTable.push(new HashMap<>());
    }

	/**
	 * Ends the current block
	 */
	public void closeBlock() {
		idTable.pop();
    }

	/**
	 * Ads the id to the table at the level we are currently checking and saves the
	 * reference to its declaration.
	 * @param id the identifier
	 * @param declaration the declaration to be saved
	 * @throws CompilationException if the id already exists
	 */
	public void insertId(String id, Declaration declaration) throws CompilationException {
		if (BaseIdentifier.THIS_NAME.equals(id)) {
			throw new CompilationException("Cannot use the reserved keyword " + BaseIdentifier.THIS_NAME
					+ " as an identifier", declaration.getLine(), declaration.getColumn(),
					CompilationException.Scope.IdentifierIdentification);
		}

		insertUncheckedId(id, declaration);
    }

	/**
	 * Ads the id to the table at the level we are currently checking and saves the
	 * reference to its declaration. Does not check for this.
	 * @param id the identifier
	 * @param declaration the declaration to be saved
	 * @throws CompilationException if the id already exists
	 */
	public void insertUncheckedId(String id, Declaration declaration) throws CompilationException {
		if (idTable.peek().containsKey(id)) {
			throw new CompilationException("Invalid redeclaration of identifier " + id, declaration.getLine(),
					declaration.getColumn(), CompilationException.Scope.IdentifierIdentification);
		} else {
			idTable.peek().put(id, declaration);
		}
	}

	/**
	 * Search for the deepest (most internal) definition instance for the given id.
	 * @param id the identifier.
	 * @param line the declaration line (which is used to throw the error if needed).
	 * @param col the declaration column (which is used to throw the error if needed).
	 * @return the declaration associated with that identifier.
	 * @throws CompilationException if the identifier does not exist.
	 */
	public Declaration searchId(String id, int line, int col) throws CompilationException {
		Declaration dec = null;

		for (Map<String, Declaration> hm: idTable) {
			if (hm.containsKey(id)) {
				dec = hm.get(id);
				break;
			}
		}

		if (dec == null) {
			throw new CompilationException("Identifier " + id + " not found ", line, col,
					CompilationException.Scope.IdentifierIdentification);
		}

		return dec;
    }
}
