package yogur.ididentification;

import yogur.error.CompilationException;
import yogur.tree.declaration.Declaration;

import java.util.*;

public class IdIdentifier {
    /*
     * Las declaraciones duplicadas se detectan en insertaId, si ya existe id en el bloque current.
     * Las ausentes en searchId si Id no existe en ningún bloque.
     */

	/**
	 * Maps each class to its own identifier environment. Note that maps the null string
	 * to the default, out-of-class identifier environment.
	 */
	private Map<String, Deque<Map<String, Declaration>>> classMap = new HashMap<>();

    private String currentClass = null;

    public IdIdentifier() {
		// The program is a "block"
		classMap.put(null, new ArrayDeque<>());
    	openBlock();
	}

	public int getCurrentId() {
		return classMap.get(currentClass).size();
	}

    /**
     * Starts a new block
     */
	public void openBlock() {
        classMap.get(currentClass).push(new HashMap<>());
    }

	/**
	 * Ends the current block
	 */
	public void closeBlock() {
		classMap.get(currentClass).pop();
    }

    public void openClass(String name) {
		classMap.put(name, new ArrayDeque<>());
		currentClass = name;
		openBlock();
	}

	public void closeClass() {
		currentClass = null;
	}

	/**
	 * Añade el identificador id al bloque en curso y la posición
	 * del árbol donde está su aparición de definición
	 * puntero
	 */
	public void insertId(String id, Declaration declaration) throws CompilationException {
		Deque<Map<String, Declaration>> deque = null;

		deque = classMap.get(currentClass);

		if (deque == null && currentClass != null) {
			deque = classMap.get(null);
		}

        // null check is preferable alas we don't store null
        if (deque.peek().get(id) != null) {
            throw new CompilationException("Duplicate id detected: " + id, 0, CompilationException.Scope.IdentificatorIdentification);
        } else {
			deque.peek().put(id, declaration);
        }
    }

    /**
	 * Busca la aparición de definición más interna para id
     */
	public Declaration searchId(String id) throws CompilationException {
		Declaration declaration = null;
		int counter = getCurrentId();

        for (Map<String, Declaration> hm: classMap.get(currentClass)) {
            if (hm.get(id) != null) {
                declaration = hm.get(id);
                System.out.println(id + " exists, declared inside " + currentClass + ", level " + counter);
            }
            counter--;
        }

        counter = classMap.get(null).size();
        if (declaration == null && currentClass != null) {
			for (Map<String, Declaration> hm : classMap.get(null)) {
				if (hm.get(id) != null) {
					declaration = hm.get(id);
					System.out.println(id + " exists, declared globally" + ", level " + counter);
				}
				counter--;
			}
		}

        if (declaration == null) {
            throw new CompilationException(id + " doesn't exist in any block", 0, CompilationException.Scope.IdentificatorIdentification);
        }

        return declaration;
    }


}
