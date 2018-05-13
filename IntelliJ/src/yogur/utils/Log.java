package yogur.utils;

public class Log {
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BOLD = "\u001B[1m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_YELLOW = "\u001B[33m";

	public enum Level {
		Debug, Info, Warning, Error;

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

	private static Level minimumLevel = Level.Debug;

	public static void setMinimumLogLevel(Level newLevel) {
		minimumLevel = newLevel;
	}

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
