package support.digitaleswohnheim.bot;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import support.digitaleswohnheim.bot.supportbot.SupportBot;
import support.digitaleswohnheim.bot.welcomebot.DormAssigner;
import support.digitaleswohnheim.bot.welcomebot.WelcomeMessage;

public class Bot extends ListenerAdapter {
	private static final String TOKEN = System.getenv("discordBotSecretkey");
	private static final String ACTIVITY = System.getenv("currentActivity");
	
	public static void main(String[] args) throws LoginException, InterruptedException {
		JDABuilder.create(GatewayIntent.DIRECT_MESSAGES, GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_MEMBERS)
			.setToken(TOKEN)
			.addEventListeners(new DormAssigner(), new WelcomeMessage(), new SupportBot())
			.setActivity(Activity.watching(ACTIVITY))
			.build()
			.awaitReady();
		System.out.println("Connected to Discord");
	}
}
