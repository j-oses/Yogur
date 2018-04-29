package yogur.tree.declaration.declarator;

public class DotDeclarator implements Declarator {
	private Declarator declarator;
	private String identifier;

	public DotDeclarator(Declarator declarator, String identifier) {
		this.declarator = declarator;
		this.identifier = identifier;
	}
}
