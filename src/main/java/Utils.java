import java.awt.*;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.Collator;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.*;
import net.dv8tion.jda.core.managers.RoleManager;
import net.dv8tion.jda.core.requests.Route;
import org.apache.commons.text.AlphabetConverter;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Utils {
 //avatar command
    public static void avatar(MessageReceivedEvent event){
        String content = event.getMessage().getMentionedUsers()
                .stream()
                .limit(10)
                .map(u -> u.getAvatarUrl())
                .collect(Collectors.joining(" , "));

        if (content.isEmpty()){
            event.getChannel().sendMessage("Ping someone to show their avatar b-baka!!").queue();
        }
        else{
            event.getChannel().sendMessage(
                    new EmbedBuilder().setImage(content).build()).queue();
        }
    }
    //ping and pong commands
    public static void ping(MessageReceivedEvent event){
        event.getChannel().sendMessage("pong!").queue();
    }

    public static void pong(MessageReceivedEvent event){
        event.getChannel().sendMessage("ping!").queue();
    }
    //rolls a 6 sided die
    public static void rolldice(MessageReceivedEvent event){
        int max= 6; int min= 1;
        Random rand = new Random();
        int n = rand.nextInt((max - min) + 1) + min;
        event.getChannel().sendMessage(
            new EmbedBuilder().setDescription(":game_die: You rolled a " + n + "!").setColor(Color.red).build()).queue();

    }

    //calls the changeprefix command
    public static void changeprefix(MessageReceivedEvent event) throws IOException{
        Configs.changeprefix(event);
    }

    //random commands
    public static void about(MessageReceivedEvent event) {
        AboutHelpList.about(event);
    }
    public static void help(MessageReceivedEvent event) {
        AboutHelpList.help(event);
    }
    public static void list(MessageReceivedEvent event) {
        AboutHelpList.list(event);
    }
    public static void eightball(MessageReceivedEvent event) { EightBall.eightball(event);}


    //repeats what the user says
    public static void say(MessageReceivedEvent event) {
        if(event.getMessage().getContentRaw().split(" ").length >= 2) {
            String whattosay = event.getMessage().getContentRaw().split("say")[1];
            event.getChannel().sendMessage(whattosay).queue();
            event.getMessage().delete().queue();
        }

    }


    //gives userinfo about the self user
    public static void userinfo(MessageReceivedEvent event){
        UserInfo.userinfo(event);
    }


    //decides between the given parameters by the user
    public static void decide(MessageReceivedEvent event){

        String decideBetween[] = event.getMessage().getContentRaw().split(" ");
        int max = decideBetween.length - 1; int min = 0;
        Random rand = new Random();
        int n = rand.nextInt(((max-min)+ 1) + min);

        event.getChannel().sendMessage("I pick "+decideBetween[n]).queue();

    }
    //says a joke based on a free API
    public static void joke(MessageReceivedEvent event)throws MalformedURLException, IOException, ParseException {

        JSONParser parser = new JSONParser();
        URL url = new URL("https://08ad1pao69.execute-api.us-east-1.amazonaws.com/dev/random_joke");
        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
        String jokeApi = in.readLine();
        JSONObject jokes = (JSONObject) parser.parse(jokeApi);
        StringBuilder sb = new StringBuilder();
        sb.append(jokes.get("setup")).append("\n" + jokes.get("punchline"));

        event.getChannel().sendMessage(sb).queue();
    }




}

