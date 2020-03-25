package support.digitaleswohnheim.bot.supportbot;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.util.regex.Pattern;

public class SupportBot extends ListenerAdapter {

    public final static String PREFIX = System.getenv("commandChar");

    @Override
    public void onPrivateMessageReceived(@Nonnull PrivateMessageReceivedEvent event) {
        // We don't want to respond to other bot accounts, including ourself
        Message message = event.getMessage();
        String content = message.getContentRaw();
        MessageChannel channel = event.getChannel();
        // getContentRaw() is an atomic getter
        // getContentDisplay() is a lazy getter which modifies the content for e.g. console view (strip discord formatting)
        if (content.equals(PREFIX+"help")) {
            channel.sendMessage("Ich kann dir helfen! Wähle zwischen den folgenden möglichkeiten.").queue(); // Important to call .queue() on the RestAction returned by sendMessage(...)
        } else if (content.equals(PREFIX+"dau")) {
            channel.sendMessage("Du hast dich als DAU (Dümmster anzunehmender User) geoutet! Kontaktiere einen der @Admins oder @Mods auf dem Server. Vielleicht können die dir helfen.").queue();
        } else if (content.equals(PREFIX+"formatted")) {
            channel.sendMessage(":shrug:").queue();
        } else if (Pattern.matches(PREFIX+"setactivity.*", content)) {
            String activity = content.replace(PREFIX+"setactivity ", "");
            channel.sendMessage("new activty:" + activity).queue();
            //super.setActivity
        }
    }
}
