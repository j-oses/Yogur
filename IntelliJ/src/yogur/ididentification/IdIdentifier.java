package yogur.ididentification;

import yogur.error.CompilationException;
import yogur.tree.declaration.Declaration;

import java.util.*;

public class IdIdentifier {
    /*
     * Las declaraciones duplicadas se detectan en insertaId, si ya existe id en el bloque current.
     * Las ausentes en searchId si Id no existe en ningún bloque.
     */

    private Deque<Map<String, Declaration>> idMap = new ArrayDeque<>();

    public IdIdentifier() {
		// The program is a "block"
    	openBlock();
	}

	public int getCurrentId() {
		return idMap.size();
	}

    /**
     * Starts a new block
     */
	public void openBlock() {
        idMap.push(new HashMap<>());
    }

	/**
	 * Ends the current block
	 */
	public void closeBlock() {
		idMap.pop();
    }

	/**
	 * Añade el identificador id al bloque en curso y la posición
	 * del árbol donde está su aparición de definición
	 * puntero
	 */
	public void insertId(String id, Declaration declaration) throws CompilationException {
        // null check is preferable alas we don't store null
        if (idMap.peek().get(id) != null) {
            throw new CompilationException("Duplicate id detected: " + id, 0, CompilationException.Scope.IdentificatorIdentification);
        } else {
            idMap.peek().put(id, declaration);
        }
    }

    /**
	 * Busca la aparición de definición más interna para id
     */
	public Declaration searchId(String id) throws CompilationException {
		Declaration declaration = null;
		int counter = getCurrentId();

        for (Map<String, Declaration> hm: idMap) {
            if (hm.get(id) != null) {
                declaration = hm.get(id);
                System.out.println(id + " exists, declared on " + declaration + ", level " + counter);
            }
            counter--;
        }

        if (declaration == null) {
            throw new CompilationException(id + " doesn't exist in any block", 0, CompilationException.Scope.IdentificatorIdentification);
        }

        return declaration;
    }


}
