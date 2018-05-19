package yogur.codegen;

import java.util.Map;

class PLabelledInstruction implements POutputLine {
	private String label;
	private Object arg;
	private String name;

	PLabelledInstruction(String name, String label) {
		this(name, null, label);
	}

	PLabelledInstruction(String name, Object arg, String label) {
		this.label = label;
		this.name = name;
		this.arg = arg;
	}

	@Override
	public String generateCode(Map<String, Integer> labelAddresses) {
		return name + " " + (arg != null ? arg + " " : "") + labelAddresses.get(label) + ";" + " \\\\ " + label;
	}

	@Override
	public boolean isComment() {
		return false;
	}
}
