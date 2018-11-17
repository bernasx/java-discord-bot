import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.mortbay.util.ajax.JSON;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class currency {
    //gets the data from the message
    public static void getcurrency(MessageReceivedEvent event) throws Exception{

        String value = event.getMessage().getContentRaw().split(" ")[1];
        float valueFloat;
        float convertedValue;
        if (event.getMessage().getContentRaw().split(" ").length != 4){

            valueFloat = 1;

            String curr1 = event.getMessage().getContentRaw().split(" ")[1].toUpperCase();
            String curr2 = event.getMessage().getContentRaw().split(" ")[2].toUpperCase();
            System.out.println("value: " + valueFloat + "\ncurr1: " + curr1 + "\ncurr2: " + curr2);
             convertedValue = currencyURL(valueFloat, curr1,curr2);
        }
        else{
             valueFloat = Float.parseFloat(value);
             String curr1 = event.getMessage().getContentRaw().split(" ")[2].toUpperCase();
             String curr2 = event.getMessage().getContentRaw().split(" ")[3].toUpperCase();
             System.out.println("value: " + valueFloat + "\ncurr1: " + curr1 + "\ncurr2: " + curr2);
              convertedValue = currencyURL(valueFloat, curr1,curr2);
        }

        event.getChannel().sendMessage("The value is: " + convertedValue).queue();


    }


    //parses the JSON stuff from the link
    public static Float currencyURL( Float value, String curr1, String curr2) throws MalformedURLException,
            IOException, ParseException {

        URL url = new URL("https://api.exchangeratesapi.io/latest");
        JSONParser parser = new JSONParser();
        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
        String currencyApiString = in.readLine();

        JSONObject currencyApi = (JSONObject) parser.parse(currencyApiString);
        JSONObject currencyApi2 = (JSONObject) currencyApi.get("rates");
        String value1;
        String value2;
        if(curr1.equals("EUR")){

             value1 = "1";
             value2 = currencyApi2.get(curr2).toString();


        }
        else{
             value1 = currencyApi2.get(curr1).toString();
             value2 = "1";
        }


        Float value1F = Float.parseFloat(value1);
        Float value2F = Float.parseFloat(value2);

        Float convertedValue = value * value2F / value1F;

        return convertedValue;

        //IT'S PRINTING THE VALUE OF THE FIRST COIN INTRODUCED

    }

    //CURRENTLY EURO IS BUGGED AND IDK WHY
    //OK SO IT COMPARES EVERY PRICE TO AN EURO
    //WHICH MEANS THAT IF SOMEONE PICKS AN EURO, THAT EURO IS GOIN TO BE 1 AND NOT WHATEVER
    //NOT CONVERTING CORRECTLY???

}
