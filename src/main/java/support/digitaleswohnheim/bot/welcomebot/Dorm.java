package support.digitaleswohnheim.bot.welcomebot;

public class Dorm implements Comparable<Dorm> {
	private final static int ABBREVIATION_LENGTH = 3;

	private final long roleId;
	private final String fullName;
	private final String abbreviation;

	private Dorm(long roleId, String fullName, String abbreviation) {
		this.roleId = roleId;
		this.fullName = fullName;
		this.abbreviation = abbreviation;
	}

	/**
	 * Creates a new Dorm POJO. The {@code abbreviation} is determined automatically
	 * in one of two ways:
	 * 
	 * <ol>
	 * <li>If the {@code fullName} is less than three words, the first three letters
	 * are used (e.g. "Hammerbrook" -> "ham")</li>
	 * <li>If the {@code fullName} is three words or more, each words' first letter
	 * is used (e.g. "Gustav-Radbruch-Haus" -> "grh")</li>
	 * </ol>
	 */
	public static Dorm of(long roleId, String fullName) {
		String abbreviation = extractAbbreviation(fullName);
		return new Dorm(roleId, fullName, abbreviation);
	}

	/**
	 * Extract the abbreviation from the full name, see
	 * {@link Dorm#of(long, String)}.
	 */
	private static String extractAbbreviation(String fullName) {
		String abbreviation;
		String[] split = fullName.split("\\W+");
		if (split.length < ABBREVIATION_LENGTH) {
			abbreviation = fullName.substring(0, ABBREVIATION_LENGTH);
		} else {
			StringBuilder sb = new StringBuilder();
			sb.append(split[0].charAt(0));
			sb.append(split[1].charAt(0));
			sb.append(split[2].charAt(0));
			abbreviation = sb.toString();
		}
		return abbreviation.toLowerCase().strip();
	}

	/**
	 * Creates a new Dorm POJO with a custom abbreviation (used for edge cases).
	 */
	public static Dorm of(long roleId, String fullName, String customAbbreviation) {
		return new Dorm(roleId, fullName, customAbbreviation);
	}

	/**
	 * Get the role ID of this dorm.
	 */
	public long getRoleId() {
		return roleId;
	}

	/**
	 * Get the full name of this dorm.
	 */
	public String getFullName() {
		return fullName;
	}

	/**
	 * Get the abbreviation of this dorm-
	 */
	public String getAbbreviation() {
		return abbreviation;
	}

	/**
	 * Return a String representation of this dorm as "!abr (Abreviation)".
	 */
	@Override
	public String toString() {
		return "!" + abbreviation + " (" + fullName + ")";
	}

	/**
	 * Total ordering of Dorms is done by their alphabetical full name.
	 */
	@Override
	public int compareTo(Dorm o) {
		return this.getFullName().compareTo(o.getFullName());
	}
}
