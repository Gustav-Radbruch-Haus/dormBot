package support.digitaleswohnheim.bot.welcomebot;

import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class WelcomeMessage extends ListenerAdapter {

	@Override
	public void onGuildJoin(GuildJoinEvent event) {
		event.getGuild().getTextChannelById(156).sendMessage("wasn das fürn spast");
	}
}
