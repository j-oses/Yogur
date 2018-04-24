package yogur.cup;

import java_cup.runtime.Symbol;

public class CustomSymbol extends Symbol {
	private int line;

	public CustomSymbol(int id, Symbol left, Symbol right, Object o, int line) {
		super(id, left, right, o);
		this.line = line;
	}

	public CustomSymbol(int id, Symbol left, Symbol right, int line) {
		super(id, left, right);
		this.line = line;
	}

	public CustomSymbol(int id, Symbol left, Object o, int line) {
		super(id, left, o);
		this.line = line;
	}

	public CustomSymbol(int id, int l, int r, Object o, int line) {
		super(id, l, r, o);
		this.line = line;
	}

	public CustomSymbol(int id, Object o, int line) {
		super(id, o);
		this.line = line;
	}

	public CustomSymbol(int id, int l, int r, int line) {
		super(id, l, r);
		this.line = line;
	}

	public CustomSymbol(int sym_num, int line) {
		super(sym_num);
		this.line = line;
	}

	public int getLine() {
		return line;
	}
}
