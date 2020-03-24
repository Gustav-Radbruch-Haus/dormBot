package support.digitaleswohnheim.bot;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import javax.security.auth.login.LoginException;

public class Bot extends ListenerAdapter{
    public static void main(String[] args) throws LoginException {
        String token = System.getenv("discordBotSecretkey");
        JDABuilder builder = new JDABuilder(token);
        builder.addEventListeners(new Bot());
        builder.setActivity(Activity.watching("PornHub Premium 4K"));
        builder.build();
        System.out.println("Connected to Discord\n\t");
    }

    @Override
    public void onPrivateMessageReceived(@Nonnull PrivateMessageReceivedEvent event) {
        System.out.println("Received a message:\n\t" + event.getMessage().getContentDisplay());
        if (event.getAuthor().isBot()) return;
        // We don't want to respond to other bot accounts, including ourself
        Message message = event.getMessage();
        String content = message.getContentRaw();
        // getContentRaw() is an atomic getter
        // getContentDisplay() is a lazy getter which modifies the content for e.g. console view (strip discord formatting)
        if (content.equals("!ping"))
        {
            MessageChannel channel = event.getChannel();
            channel.sendMessage("Du hast mir private geschrieben! Ich habe aber keine Antwort für dich :shrug: !").queue(); // Important to call .queue() on the RestAction returned by sendMessage(...)
        }
    }

    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;
        // We don't want to respond to other bot accounts, including ourself
        Message message = event.getMessage();
        String content = message.getContentRaw();
        // getContentRaw() is an atomic getter
        // getContentDisplay() is a lazy getter which modifies the content for e.g. console view (strip discord formatting)
        if (content.equals("!ping"))
        {
            MessageChannel channel = event.getChannel();
            channel.sendMessage("Pong!").queue(); // Important to call .queue() on the RestAction returned by sendMessage(...)
        }
        else if (content.equals("!peters"))
        {
            MessageChannel channel = event.getChannel();
            channel.sendMessage("Soll er mal arbeiten!").queue(); // Important to call .queue() on the RestAction returned by sendMessage(...)
        }
        else if (content.equals("!pascal"))
        {
            MessageChannel channel = event.getChannel();
            channel.sendMessage("Du kannst kein Java!").queue(); // Important to call .queue() on the RestAction returned by sendMessage(...)
        }
        System.out.println("Received a guild message:\n\t" + event.getMessage().getContentDisplay());
    }
}
