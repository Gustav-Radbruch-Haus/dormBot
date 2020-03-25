package support.digitaleswohnheim.bot.welcomebot;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public final class WelcomeBotUtils {

	private final static String PREFIX = "!";

	/**
	 * Extract the command from a message (without prefix). If the message is not
	 * valid, returns null.
	 */
	protected static String extractCommand(GuildMessageReceivedEvent event) {
		Message message = event.getMessage();
		String content = message.getContentRaw();
		if (content.startsWith(PREFIX)) {
			String command = content.substring(1).strip();
			return command;
		}
		return null;
	}

	/**
	 * Write a message to the given Discord channel.
	 */
	protected static void writeMessage(MessageChannel channel, String message) {
		channel.sendMessage(message).queue();
	}
}
