import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import java.io.*;


public class Configs {

    //changes bot prefix and checks for escape/regex characters. Configs are stored in config.json
    public static void changeprefix(MessageReceivedEvent event) throws IOException {

        String[] prefixMessage = event.getMessage().getContentRaw().split(" ");


        if (prefixMessage.length != 2 || event.getMessage().getContentRaw().contains("\\\\")){
            event.getChannel().sendMessage("That's not a valid prefix!").queue();
            return;
        }
        else if(event.getMember().isOwner() || event.getMember().hasPermission(Permission.ADMINISTRATOR) ){
            String newprefix =event.getMessage().getContentRaw().split(" ")[1];
            JsonIO.JSONWriter("prefix",newprefix); //call the JsonIO class which contains a reader and writer for the configs file
            event.getChannel().sendMessage("Success! The prefix is now: " + newprefix).queue();
            Commands.prefix = newprefix; //sets the new prefix since the bot only reads the prefix at boot
        }
        else{
            event.getChannel().sendMessage("You don't have enough permissions to do that!").queue();
            return;
        }

    }

}
