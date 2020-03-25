package support.digitaleswohnheim.bot;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import support.digitaleswohnheim.bot.supportbot.SupportBot;
import support.digitaleswohnheim.bot.welcomebot.DormAssigner;
import support.digitaleswohnheim.bot.welcomebot.WelcomeMessage;

public class Bot extends ListenerAdapter {
	private static final String TOKEN = System.getenv("discordBotSecretkey");
	private static final String ACTIVITY = System.getenv("currentActivity");
	
	public static void main(String[] args) throws LoginException, InterruptedException {
		JDABuilder builder = new JDABuilder(TOKEN);
		builder.addEventListeners(new DormAssigner());
		builder.addEventListeners(new WelcomeMessage());
		builder.addEventListeners(new SupportBot());
		builder.setActivity(Activity.watching(ACTIVITY));
		builder.build().awaitReady();
		System.out.println("Connected to Discord");
	}
}
