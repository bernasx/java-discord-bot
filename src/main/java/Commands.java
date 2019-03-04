import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.events.channel.voice.update.VoiceChannelUpdateParentEvent;
import net.dv8tion.jda.core.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.stream.Collectors;


public class Commands {


    static String prefix = Main.JSONReader();


    public static void commandList(MessageReceivedEvent event){
        //makes Utils class a reflectable class
        Class reflectUtilsClass = Utils.class;
        //checks if a user is mentioning the bot alone, if yes it will send a message with the current prefix
        String content = event.getMessage().getMentionedUsers().stream().limit(1)
                .map(u->u.getId()).collect(Collectors.joining());
        if(event.getJDA().getSelfUser().getId().equals(content)){

            if(event.getMessage().getContentRaw().split(" ").length == 1) {
                event.getChannel().sendMessage("The current prefix is: " + "**" + prefix + "**" +
                        "\nI'm not telling you" +
                        " just because you asked for it, baka.").queue();
            }
        }

        //uses java reflection to get methodnames for commands in Utils class
        Method[] classMethods = reflectUtilsClass.getMethods();
            for (Method method : classMethods){

                if( (prefix.toLowerCase() + method.getName()).equals(event.getMessage().getContentRaw().split(" ")[0].toLowerCase())){
                    try {
                        method.invoke(reflectUtilsClass,event);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
    }

}


