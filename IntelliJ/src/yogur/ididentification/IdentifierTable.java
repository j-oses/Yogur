package yogur.ididentification;

import yogur.utils.CompilationException;
import yogur.tree.declaration.Declaration;
import yogur.tree.type.BaseType;

import java.util.*;

public class IdentifierTable {
	/**
	 *
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
	 */
	public void insertId(String id, Declaration declaration) throws CompilationException {
        if (idTable.peek().containsKey(id)) {
            throw new CompilationException("Invalid redeclaration of identifier: " + id, declaration.getLine(),
					declaration.getColumn(), CompilationException.Scope.IdentificatorIdentification);
        } else {
			idTable.peek().put(id, declaration);
        }
    }

    /**
	 * Busca la aparición de definición más interna para id
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
			throw new CompilationException("Identifier not found ", line, col,
					CompilationException.Scope.IdentificatorIdentification);
		}

		return dec;
    }
}
