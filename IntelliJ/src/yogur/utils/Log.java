package yogur.utils;

/**
 * A simple logger for the compiler.
 */
public class Log {
	/*
	 * ASCII COLOR AND STYLE CODES
	 */
	private static final String ANSI_RESET = "\u001B[0m";
	private static final String ANSI_BOLD = "\u001B[1m";
	private static final String ANSI_RED = "\u001B[31m";
	private static final String ANSI_YELLOW = "\u001B[33m";

	/**
	 * An enum representing an error importance level.
	 */
	public enum Level {
		Debug, Info, Warning, Error;

		/**
		 * Customizes the error header for this error level.
		 * @return a header, which may contain ASCII color codes for fancy output.
		 */
		private String getLevelHeader() {
			switch (this) {
				case Debug:
					return "Debug: ";
				case Info:
					return "INFO: ";
				case Warning:
					return ANSI_YELLOW + "WARNING: ";
				case Error:
					return ANSI_RED + ANSI_BOLD + "ERROR: ";
				default:
					return "";
			}
		}
	}

	/**
	 * The less important level which will be logged.
	 */
	private static Level minimumLevel = Level.Debug;

	/**
	 * Set the minimum level which will be logged.
	 * @param newLevel the new level.
	 */
	public static void setMinimumLogLevel(Level newLevel) {
		minimumLevel = newLevel;
	}

	/**
	 * Log a message.
	 * @param level the message level.
	 * @param message the actual message.
	 */
	public static void log(Level level, String message) {
		if (level.ordinal() >= minimumLevel.ordinal()) {
			System.out.println(level.getLevelHeader() + ANSI_RESET + message);
		}
	}

	public static void debug(String message) {
		log(Level.Debug, message);
	}

	public static void info(String message) {
		log(Level.Info, message);
	}

	public static void warning(String message) {
		log(Level.Warning, message);
	}

	public static void error(String message) {
		log(Level.Error, message);
	}

	public static void error(String message, Throwable e) {
		log(Level.Error, message + ": " + e.getMessage());
	}

	public static void error(Throwable e) {
		log(Level.Error, e.getMessage());
	}
}
