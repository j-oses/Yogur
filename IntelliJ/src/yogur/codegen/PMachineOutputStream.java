package yogur.codegen;

import java.io.*;
import java.util.*;

public class PMachineOutputStream extends FileWriter {
	private int instructionCount = 0;

	private Map<String, Integer> labelAddresses = new HashMap<>();
	private Deque<POutputLine> queuedInstructions = new ArrayDeque<>();

	private int currentLabel = 0;

	public PMachineOutputStream(String fileName) throws IOException {
		super(fileName);
	}

	public PMachineOutputStream(String fileName, boolean append) throws IOException {
		super(fileName, append);
	}

	public PMachineOutputStream(File file) throws IOException {
		super(file);
	}

	public PMachineOutputStream(File file, boolean append) throws IOException {
		super(file, append);
	}

	public PMachineOutputStream(FileDescriptor fd) {
		super(fd);
	}


	public void appendInstruction(String name, Object... arguments) throws IOException {
		queuedInstructions.add(new PNonLabelledInstruction(name, arguments));
		instructionCount++;
	}

	public void appendLabelledInstruction(String name, String label) throws IOException {
		queuedInstructions.add(new PLabelledInstruction(name, label));
		instructionCount++;
	}

	public void appendLabelledInstruction(String name, Object arg, String label) throws IOException {
		queuedInstructions.add(new PLabelledInstruction(name, arg, label));
		instructionCount++;
	}

	public void appendLabel(String name) throws IOException {
		labelAddresses.put(name, instructionCount);
		appendComment(name + ":");
	}

	private int unusedLabelId() {
		currentLabel++;
		return currentLabel;
	}

	public String generateLabel(String prefix) {
		return prefix + currentLabel;
	}

	public String generateLabelWithUnusedId(String prefix) {
		return prefix + unusedLabelId();
	}

	public void appendComment(String string) throws IOException {
		queuedInstructions.add(new PComment(string));
	}

	private String padRight(String s, int n) {
		return String.format("%1$-" + n + "s", s);
	}

	@Override
	public void close() throws IOException {
		queuedInstructions.add(new PNonLabelledInstruction("stp"));

		int count = 0;
		for (POutputLine instr: queuedInstructions) {
			if (!instr.isComment()) {
				this.append(" " + padRight("{" + String.valueOf(count) + "}", 7));
				count++;
			}

			this.append(instr.generateCode(labelAddresses));
			this.append("\n");
		}

		super.close();
	}
}
