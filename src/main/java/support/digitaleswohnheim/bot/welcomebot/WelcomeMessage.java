package support.digitaleswohnheim.bot.welcomebot;

import javax.annotation.Nonnull;

import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class WelcomeMessage extends ListenerAdapter {

	/**
	 * This message gets printed to every new user into the welcome channel.
	 */
	private final static String WELCOME_MESSAGE_TEMPLATE = "Hey %s, welcome to the **Studierendenwerk Hamburg** Discord server! "
		+ "To gain access, please enter the three-letter command of your dormitory, for example `!grh` for _Gustav-Radbruch-Haus_."
		+ "For a full list of all dorm commands, please enter `!dorms`. Have fun! :tada:";

	/**
	 * Event fired when a new user enters our server. This writes the generic
	 * welcome message with the user's name into the #welcome channel.
	 */
	@Override
	public void onGuildMemberJoin(@Nonnull GuildMemberJoinEvent event) {
		try {
			WelcomeBotUtils.addRoleToMember(event.getGuild(), event.getMember(), WelcomeBotUtils.NEWUSER_ROLE_ID);

			String joinedUserName = WelcomeBotUtils.getAuthorName(event);
			String welcomeMessage = String.format(WELCOME_MESSAGE_TEMPLATE, joinedUserName);
			WelcomeBotUtils.getWelcomeChannel(event).sendMessage(welcomeMessage);
		} catch (Exception e) {}
	}
}
