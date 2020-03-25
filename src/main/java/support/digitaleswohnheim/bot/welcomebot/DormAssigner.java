package support.digitaleswohnheim.bot.welcomebot;

import javax.annotation.Nonnull;

import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class DormAssigner extends ListenerAdapter {

	@Override
	public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
		if (!event.getAuthor().isBot()) {
			String command = WelcomeBotUtils.extractCommand(event);
			if (command != null) {
				MessageChannel channel = event.getChannel();
				switch (command) {
					case "dorms":
						WelcomeBotUtils.writeMessage(channel, "liste der dorms");
						break;
					case "friend":
						WelcomeBotUtils.writeMessage(channel, "welcome huhnke");
						break;
					default:
						handleDormAssignment(event.getAuthor(), command);
						break;
				}
			}
		}
	}
	
	private void handleDormAssignment(User user, String command) {
		// TODO assignment
	}
}
