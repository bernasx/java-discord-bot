import javax.security.auth.login.LoginException;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.dv8tion.jda.core.managers.GuildController;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.*;

import static net.dv8tion.jda.core.AccountType.BOT;


public class Main extends ListenerAdapter {


    public static void main(String[] args) throws LoginException, FileNotFoundException{
        String token = JsonIO.JSONReader("token","tokens.json");
        JDABuilder builder = new JDABuilder(BOT);
        builder.addEventListener(new Main());
        builder.addEventListener(new VoiceChat());
        builder.setToken(token);
        builder.setGame(Game.playing(" hard to get."));
        builder.buildAsync();
        JSONReader();
    }


//checks if user sending a message is a bot, if not it will evoke commandList reflection method in Commands.
    @Override
    public void onMessageReceived(MessageReceivedEvent event){

        if(event.getAuthor().isBot()){
         return;
        }
        else {
            System.out.println("We received a message from " + event.getAuthor().getName() + ": " +
                    event.getMessage().getContentDisplay()); // log message to console

            Commands.commandList(event);
        }
    }

    public void onVoiceChatJoin(GuildVoiceJoinEvent event){

            String voicetest = event.getChannelJoined().toString();
            System.out.println(voicetest);


    }

 //prefix loader JSONReader - will only work for prefixes.
    public static String JSONReader() {
        JSONParser parser = new JSONParser();
        try
        {
            Object object = parser
                    .parse(new FileReader("config.json"));

            //convert Object to JSONObject
            JSONObject jsonObject = (JSONObject)object;

            //Reading the String
            String prefix = (String) jsonObject.get("prefix");

            return prefix;
        }
        catch(FileNotFoundException fe)
        {
            fe.printStackTrace();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
    

}


