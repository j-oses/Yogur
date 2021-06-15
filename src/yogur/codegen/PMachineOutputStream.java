package yogur.codegen;

import java.io.*;
import java.util.*;

/**
 * A modified FileWriter which stores a list of instructions for the P-machine and
 * writes them to a file on close().
 *
 * The stream also provides an abstraction to manage labels, so that it can be provided abstract (string)
 * labels from the AST. The stream is responsible of translating them to absolute offsets.
 */
public class PMachineOutputStream extends FileWriter {
	/**
	 * The current number of instructions. Note that this count actual instructions and not comments, to
	 * compute the label's absolute addresses.
	 */
	private int instructionCount = 0;

	/**
	 * Saves the label absolute addresses, to be used in the translation.
	 */
	private Map<String, Integer> labelAddresses = new HashMap<>();

	/**
	 * Saves the whole array of instructions which will be printed.
	 */
	private List<POutputLine> instructionList = new ArrayList<>();

	/**
	 * The last label id used.
	 */
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


	/**
	 * Append a new instruction to the stream.
	 * @param name the name of the instruction.
	 * @param arguments the arguments of the instruction.
	 */
	public void appendInstruction(String name, Object... arguments) {
		instructionList.add(new PNonLabelledInstruction(name, arguments));
		instructionCount++;
	}

	/**
	 * Append a new instruction to the stream, which depends on a label. The label must be identified as a separate
	 * parameter to enable the translation to absolute offsets.
	 * @param name the name of the instruction.
	 * @param label the label, which is the first and only argument to the instruction.
	 */
	public void appendLabelledInstruction(String name, String label) {
		instructionList.add(new PLabelledInstruction(name, label));
		instructionCount++;
	}

	/**
	 * Append a new instruction to the stream, which depends on a label. The label must be identified as a separate
	 * parameter to enable the translation to absolute offsets.
	 * @param name the name of the instruction.
	 * @param arg the first argument to the instruction.
	 * @param label the label, which is the second and last argument to the instruction.
	 */
	public void appendLabelledInstruction(String name, Object arg, String label) {
		instructionList.add(new PLabelledInstruction(name, arg, label));
		instructionCount++;
	}

	/**
	 * Append a label to mark the current position on the code. Its location will be saved and the label name
	 * will be translated to a comment, to make the machine code readable.
	 * @param name the name of the label. Must be unique. See {@link #generateLabelWithUnusedId(String)}
	 */
	public void appendLabel(String name) {
		labelAddresses.put(name, instructionCount);
		appendComment(name + ":");
	}

	private int unusedLabelId() {
		currentLabel++;
		return currentLabel;
	}

	/**
	 * Generates a label using the last uniquely generated id. To be used in combination with
	 * {@link #generateLabelWithUnusedId(String)}, so related labels can share an id (ex. for and endFor).
	 * @param prefix the label text. The id will be appended to it to form the label name.
	 * @return the full label name.
	 */
	public String generateLabel(String prefix) {
		return prefix + currentLabel;
	}

	/**
	 * Generates a label using a new uniquely generated id.
	 * @param prefix the label text. The id will be appended to it to form the label name.
	 * @return the full label name.
	 */
	public String generateLabelWithUnusedId(String prefix) {
		return prefix + unusedLabelId();
	}

	/**
	 * Appends a single-line comment in the current line position.
	 * @param string
	 */
	public void appendComment(String string) {
		instructionList.add(new PComment(string));
	}

	private String padRight(String s, int n) {
		return String.format("%1$-" + n + "s", s);
	}

	@Override
	public void close() throws IOException {
		// The last instruction should be stop
		instructionList.add(new PNonLabelledInstruction("stp"));

		int count = 0;
		for (POutputLine instr: instructionList) {
			if (!instr.isComment()) {
				this.append(" ");
				this.append(padRight("{" + String.valueOf(count) + "}", 7));
				count++;
			}

			this.append(instr.generateCode(labelAddresses));
			this.append("\n");
		}

		super.close();
	}
}
