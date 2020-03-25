package support.digitaleswohnheim.bot.welcomebot;

import java.util.SortedMap;
import java.util.StringJoiner;
import java.util.TreeMap;

import javax.annotation.Nonnull;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GenericGuildMessageEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class DormAssigner extends ListenerAdapter {
	/**
	 * Static list of all dorms and their respective channel IDs. The abbreviation
	 * is determined automatically, though in some cases it gets overwritten by a
	 * custom one.
	 */
	private final static Dorm[] DORMS = {
		Dorm.of(691065463110762558L, "Allerm\u00F6he", "alm"),
		Dorm.of(691066992072982578L, "Amalie-Dietrich-Haus"),
		Dorm.of(691067050306830360L, "Armgartstra\u00DFe"),
		Dorm.of(691067077833916416L, "Berliner Tor 3"),
		Dorm.of(691067114290806846L, "Bornstra\u00DFe"),
		Dorm.of(691067152375087164L, "Die Burse", "dbu"),
		Dorm.of(691067187019907072L, "Emil-Wolff-Haus"),
		Dorm.of(691067223229333555L, "Europa- und Georgi-Haus", "egh"),
		Dorm.of(691067293748297791L, "Grandweg"),
		Dorm.of(691067321372115024L, "Grindelallee"),
		Dorm.of(691067351461920768L, "Gustav-Radbruch-Haus"),
		Dorm.of(691067380712865792L, "Hagenbeckstra\u00DFe"),
		Dorm.of(691067427022176267L, "Hammerbrook"),
		Dorm.of(691067459943268362L, "Harburg"),
		Dorm.of(691067570853511171L, "Harburger-H\u00E4user", "hah"),
		Dorm.of(691067620409212928L, "Helmut-Schmidt-Studierendenhaus"),
		Dorm.of(691067671843831889L, "Kiwittsmoor"),
		Dorm.of(691067709173268571L, "Lokstedt"),
		Dorm.of(691067738889781248L, "Margaretha-Rothe-Haus"),
		Dorm.of(691067786642063380L, "Neuwiedenthal"),
		Dorm.of(691067841956413441L, "Ottersbekallee"),
		Dorm.of(691067897833062442L, "Rahlstedt"),
		Dorm.of(691067927163568209L, "Rudolf-Laun-Haus"),
		Dorm.of(691067959388536842L, "Sophie-Schoop-Haus"),
		Dorm.of(691068023334764584L, "Triftstra\u00DFe"),
		Dorm.of(691068052485177384L, "Unnastra\u00DFe")
	};

	/**
	 * Statically initialized SortedMap with the abbreviation as key.
	 */
	private static SortedMap<String, Dorm> dormsMap;
	static {
		dormsMap = new TreeMap<>();
		for (Dorm d : DORMS) {
			dormsMap.put(d.getAbbreviation(), d);
			System.out.println(d);
		}
	}

	/**
	 * All incoming messages are handled in this method. To be relevant, the
	 * messages must not be written by the bot itself, must be posted in the
	 * #welcome channel, must start with a ! symbol, and must be a valid command
	 * (!dorms, !friend, or a dorm command).
	 */
	@Override
	public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
		if (!event.getAuthor().isBot() && WelcomeBotUtils.isWelcomeChannel(event)) {
			String command = WelcomeBotUtils.extractCommand(event);
			if (command != null) {
				switch (command) {
					case "dorms":
						printDormsList(event);
						break;
					case "friend":
						handleFriendAssignment(event);
						break;
					default:
						Dorm dorm = dormsMap.get(command);
						if (dorm != null) {
							handleDormAssignment(event, dorm);
						}
						break;
				}
			}
		}
	}

	/**
	 * Print the registration instructions and an alphabetically sorted list of all
	 * dorms.
	 */
	private void printDormsList(GenericGuildMessageEvent event) {
		StringJoiner sj = new StringJoiner("\n");
		sj.add("Please find your dormitory and enter the three-letter command to get assigned to your group (don't forget the exclamation mark):");
		sj.add("");
		for (Dorm d : dormsMap.values()) {
			String dormString = String.format("`!%s` - %s", d.getAbbreviation(), d.getFullName());
			sj.add(dormString);
		}
		sj.add("");
		sj.add("Use `!friend` if you're a guest and don't live in any of the above dormitories!");
		String dormsList = sj.toString();

		WelcomeBotUtils.writeMessage(event.getChannel(), dormsList, "List of Dormitory Abbreviations");
	}

	/**
	 * Assign the user to the entered dormitory.
	 */
	private void handleDormAssignment(GuildMessageReceivedEvent event, Dorm dorm) {
		registerUser(event, dorm.getRoleId());

		String welcomeMessage = String.format("Welcome to %s, %s!", dorm.getFullName(), event.getAuthor().getName());
		WelcomeBotUtils.writeMessage(event.getChannel(), welcomeMessage);
	}

	/**
	 * Assign the user to the generic friend group.
	 */
	private void handleFriendAssignment(GuildMessageReceivedEvent event) {
		registerUser(event, null);
		WelcomeBotUtils.writeMessage(event.getChannel(), "Welcome, friend!");
	}

	/**
	 * Add the registered user to the "Member" role and remove them from the "New
	 * User" role. If given, also add them to the {@code additionalRole} (e.g. the
	 * dorm role).
	 */
	private void registerUser(GuildMessageReceivedEvent event, Long additionalRole) {
		Guild guild = event.getGuild();
		Member member = guild.getMember(event.getAuthor());
		WelcomeBotUtils.addRoleToMember(guild, member, WelcomeBotUtils.MEMBER_ROLE_ID);
		if (additionalRole != null) {
			WelcomeBotUtils.addRoleToMember(guild, member, additionalRole);
		}
		WelcomeBotUtils.removeRoleFromMember(guild, member, WelcomeBotUtils.NEWUSER_ROLE_ID);
	}
}
