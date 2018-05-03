package yogur.ididentification;

import yogur.error.CompilationException;
import yogur.tree.declaration.Declaration;
import yogur.tree.type.BaseType;

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

    public void openClass(String name) throws CompilationException {
		for (BaseType.PredefinedType typeStr: BaseType.PredefinedType.values()) {
			if (typeStr.equals(name)) {
				throw new CompilationException("Invalid redeclaration of type: " + typeStr,
						CompilationException.Scope.IdentificatorIdentification);
			}
		}

		if (classMap.get(name) == null){
			classMap.put(name, new ArrayDeque<>());
			currentClass = name;
			openBlock();
		} else {
			throw new CompilationException("Class name already exists: " + name,
					CompilationException.Scope.IdentificatorIdentification);
		}
	}

	public void closeClass() {
		currentClass = null;
	}

	public boolean hasClassNamed(String name) {
		return classMap.containsKey(name);
	}

	public Declaration searchIdOnClass(String id, String classStr, int line, int col) throws CompilationException {
		Declaration declaration = null;
		int counter = classMap.get(classStr).size();

		for (Map<String, Declaration> hm: classMap.get(classStr)) {
			if (hm.get(id) != null) {
				declaration = hm.get(id);
				System.out.println(id + " exists, declared inside " + classStr + ", at level " + counter);
			}
			counter--;
		}

		if (declaration == null) {
			String forClass = (classStr != null) ? ("for class " + classStr) : "globally";
			throw new CompilationException(id + " not found " + forClass, line, col,
					CompilationException.Scope.IdentificatorIdentification);
		}

		return declaration;
	}

	/**
	 * Añade el identificador id al bloque en curso y la posición
	 * del árbol donde está su aparición de definición
	 * puntero
	 */
	public void insertId(String id, Declaration declaration) throws CompilationException {
		Deque<Map<String, Declaration>> deque = null;

		deque = classMap.get(currentClass);

		/*if (deque == null && currentClass != null) {
			deque = classMap.get(null);
		}*/

        // null check is preferable alas we don't store null
        if (deque.peek().get(id) != null) {
            throw new CompilationException("Duplicate id detected: " + id, declaration.getLine(),
					declaration.getColumn(), CompilationException.Scope.IdentificatorIdentification);
        } else {
			deque.peek().put(id, declaration);
        }
    }

    /**
	 * Busca la aparición de definición más interna para id
     */
	public Declaration searchId(String id, int line, int col) throws CompilationException {
		Declaration dec;
		try {
			dec = this.searchIdOnClass(id, currentClass, line, col);
		} catch (CompilationException e) {
			if (currentClass != null) {
				dec = this.searchIdOnClass(id, null, line, col);
			} else {
				throw e;
			}
		}

		return dec;
    }
}
