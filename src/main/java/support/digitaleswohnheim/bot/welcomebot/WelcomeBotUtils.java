package support.digitaleswohnheim.bot.welcomebot;

import java.awt.Color;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.guild.GenericGuildEvent;
import net.dv8tion.jda.api.events.guild.member.GenericGuildMemberEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public final class WelcomeBotUtils {
	/**
	 * The prefix used for all commands.
	 */
	public final static String PREFIX = "!";

	/**
	 * The ID of the channel our new users get put into.
	 */
	public final static long WELCOME_CHANNEL_ID = 691316144472326194L;

	/**
	 * The role ID of validated users
	 */
	public static final long MEMBER_ROLE_ID = 690240377164529675L;

	/**
	 * The role ID of unvalidated (new) users
	 */
	public static final long NEWUSER_ROLE_ID = 692421691527266304L;

	/**
	 * Welcome Channel (cached after first search)
	 */
	private static MessageChannel welcomeChannel;

	/**
	 * Retrieve the welcome channel. This is only queried once and then cached as
	 * static variable in {@link WelcomeMessage#welcomeChannel}.
	 */
	public static MessageChannel getWelcomeChannel(GenericGuildEvent event) {
		if (welcomeChannel == null) {
			welcomeChannel = event.getGuild().getTextChannelById(WELCOME_CHANNEL_ID);
		}
		return welcomeChannel;
	}

	/**
	 * Returns true if the given channel is the welcome channel
	 * ({@code WelcomeBotUtils#WELCOME_CHANNEL_ID}).
	 */
	public static boolean isWelcomeChannel(GuildMessageReceivedEvent event) {
		MessageChannel welcomeChannel = getWelcomeChannel(event);
		MessageChannel eventChannel = event.getChannel();
		return welcomeChannel.equals(eventChannel);
	}

	/**
	 * Extract the command from a message (without prefix). If the message is not
	 * valid, returns null.
	 */
	public static String extractCommand(GuildMessageReceivedEvent event) {
		Message message = event.getMessage();
		String content = message.getContentRaw();
		if (content.startsWith(PREFIX)) {
			String command = content.substring(1).strip();
			return command;
		}
		return null;
	}

	/**
	 * Write an embedded ("pretty") message to the given Discord channel (without
	 * title or color, defaults to black);
	 */
	public static void writeMessage(MessageChannel channel, String description) {
		writeMessage(channel, description, null, null);
	}

	/**
	 * Write an embedded ("pretty") message to the given Discord channel (without
	 * color, defaults to black);
	 */
	public static void writeMessage(MessageChannel channel, String description, String title) {
		writeMessage(channel, description, title, null);
	}

	/**
	 * Write an embedded ("pretty") message to the given Discord channel.
	 */
	public static void writeMessage(MessageChannel channel, String description, String title, Color color) {
		EmbedBuilder embedBuilder = new EmbedBuilder();
		if (description != null) {
			embedBuilder.setDescription(description);
		}
		if (title != null) {
			embedBuilder.setTitle(title);
		}
		if (color != null) {
			embedBuilder.setColor(color);
		}
		channel.sendMessage(embedBuilder.build()).queue();
	}

	/**
	 * Write a simple message to the given Discord channel.
	 */
	public static void writeSimpleMessage(MessageChannel channel, String message) {
		channel.sendMessage(message).queue();
	}

	/**
	 * Extract the displayed name of the event's author.
	 */
	public static String getAuthorName(GenericGuildMemberEvent event) {
		return event.getMember().getEffectiveName();
	}

	/**
	 * Add a role by the given role ID to the member of this server.
	 */
	public static void addRoleToMember(Guild guild, Member member, long roleToAdd) {
		guild.addRoleToMember(member, getRoleById(guild, roleToAdd)).queue();
	}

	/**
	 * Remove a role by the given role ID to the member of this server.
	 */
	public static void removeRoleFromMember(Guild guild, Member member, long roleToRemove) {
		guild.removeRoleFromMember(member, getRoleById(guild, roleToRemove)).queue();
	}

	/**
	 * Get a Role object by its role ID from this server.
	 */
	public static Role getRoleById(Guild guild, long roleId) {
		Role role = guild.getRoleById(roleId);
		return role;
	}
}
