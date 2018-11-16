import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.apache.commons.text.WordUtils;
import java.awt.*;
import  java.util.*;

public class EightBall{


    public static void eightball(MessageReceivedEvent event) {
        //array with answers
        String[] eightBallArray = {"Yes.", "No.","It is certain.","Very Doubtful.","Only if you believe.","Maybe.",
                "Absolutely.","Absolutely Not.","I don't think that's how it works.","Sure.","Trust and Believe.",
                "Without a doubt.","This is so sad, alexa play Despacito.","It is small.","It is big.","Can not predict now."
        ,"True.","False","99% Correct","Correct","Incorrect","I can confidently say yes.","yeeee","I don't think you want the answer."};
        //picks a random number for the array
        int max = eightBallArray.length - 1; int min = 0;
        Random rand = new Random();
        int n = rand.nextInt((max - min) + 1) + min;
        //gets the text from the user
        String messageText = event.getMessage().getContentDisplay();
        messageText = messageText.substring(messageText.indexOf(' ')+1);
        //makes the embed
        event.getChannel().sendMessage(
                new EmbedBuilder().setTitle(WordUtils.capitalize(messageText,' ')).setDescription( " \n**"
                        + eightBallArray[n]+"**" + "\n \n :8ball:")
                        .setColor(Color.BLACK)
                .build()).queue();

    }
}