import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.apache.commons.text.WordUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.mortbay.util.ajax.JSON;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.MalformedURLException;
import java.net.URL;

public class Currency {
    //gets the data from the message
    public static void getcurrency(MessageReceivedEvent event) throws Exception{

        String value = event.getMessage().getContentRaw().split(" ")[1].replace(",",".");
        float valueFloat;
        BigDecimal convertedValue; //BigDecimal is returned from the currencyURl method
        String curr1 = "";
        String curr2 = "";


        //checks if the message is well built, if not it assumes 1 is the value for the first currency
        if (event.getMessage().getContentRaw().split(" ").length != 4){

            Boolean converting1 = true;

             valueFloat = 1;
             curr1 = event.getMessage().getContentRaw().split(" ")[1].toUpperCase();
             curr2 = event.getMessage().getContentRaw().split(" ")[2].toUpperCase();
            System.out.println("value: " + valueFloat + "\ncurr1: " + curr1 + "\ncurr2: " + curr2);
             convertedValue = currencyURL(valueFloat, curr1,curr2);
             embedBuilder(event,curr1,curr2,convertedValue,converting1,value);

        }
        else{
              Boolean converting1 = false;
              valueFloat = Float.parseFloat(value);
              curr1 = event.getMessage().getContentRaw().split(" ")[2].toUpperCase();
              curr2 = event.getMessage().getContentRaw().split(" ")[3].toUpperCase();
              System.out.println("value: " + valueFloat + "\ncurr1: " + curr1 + "\ncurr2: " + curr2);
              convertedValue = currencyURL(valueFloat, curr1,curr2);
              embedBuilder(event,curr1,curr2,convertedValue,converting1, value);

        }

    }


    //parses the JSON stuff from the link
    public static BigDecimal currencyURL( Float value, String curr1, String curr2) throws MalformedURLException,
            IOException, ParseException {

        URL url = new URL("https://api.exchangeratesapi.io/latest");
        JSONParser parser = new JSONParser();
        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
        String currencyApiString = in.readLine();

        //gets the rates "list" and then gets the initials.
        JSONObject currencyApi = (JSONObject) parser.parse(currencyApiString);
        JSONObject currencyApi2 = (JSONObject) currencyApi.get("rates");
        String value1;
        String value2;
        if(curr1.equals("EUR")){

             value1 = "1";
             value2 = currencyApi2.get(curr2).toString();


        }
        else if(curr2.equals("EUR")){
             value1 = currencyApi2.get(curr1).toString();
             value2 = "1";
        }
        else{
            value1 = currencyApi2.get(curr1).toString();
            value2 = currencyApi2.get(curr2).toString();
        }

        //use BigDecimals for money calculations!!!!

        BigDecimal valueBig = new BigDecimal(value);
        BigDecimal value1Big = new BigDecimal(value1);
        BigDecimal value2Big = new BigDecimal(value2);

        System.out.println(valueBig);
        System.out.println(value1Big);
        System.out.println(value2Big);
        BigDecimal convertedValue = valueBig.multiply(value2Big).divide(value1Big,2, RoundingMode.HALF_EVEN);

        return convertedValue;
    }

    //makes the embed while setting the value in the message received to 1 if the messaged wasn't made properly

    public static void embedBuilder(MessageReceivedEvent event,
             String curr1, String curr2,
             BigDecimal convertedValue, Boolean converting1, String value){

        if(converting1){
            value = "1";
        }


        event.getChannel().sendMessage(
                new EmbedBuilder().setTitle("I'm converting this for myself you know?")
                        .setDescription( value + " " + curr1 + " is " + convertedValue + " " + curr2 +
                                " \n \n :moneybag: :money_with_wings:  :moneybag: :money_with_wings: " +
                                " :moneybag: :money_with_wings:  :moneybag:")
                        .setColor(Color.yellow)
                        .build()).queue();


    }

    public static void currencyList(MessageReceivedEvent event) {


        event.getChannel().sendMessage(new EmbedBuilder().setTitle("These are the currency codes for the bot: ")
                .setDescription("BGN - Bulgarian Lev \n" +
                        "CAD - Canadian Dollar \n" +
                        "BRL - Brazillian Real \n" +
                        "HUF - Hungarian Forint \n" +
                        "DKK - Danish Krone \n" +
                        "JPY - Japanese Yen \n" +
                        "ILS - Israeli Shekel \n" +
                        "TRY - Turkish Lira \n" +
                        "RON - Romanian Leu \n" +
                        "GBP - British Pound / Pound Sterling \n" +
                        "PHP - Philippine Piso \n" +
                        "HRK - Croatian Kuna \n" +
                        "NOK - Norwegian Krone \n" +
                        "USD - United States Dollars \n" +
                        "MXN - Mexican Peso \n" +
                        "AUD - Australian Dollar \n" +
                        "IDR - Indonesian Rupiah \n" +
                        "KRW - South Korean Won \n" +
                        "HKD - Hong Kong Dollar \n" +
                        "ZAR - South African Rand \n" +
                        "ISK - Icelandic Króna \n" +
                        "CZK - Czech Koruna \n" +
                        "THB - Thai Baht \n" +
                        "MYR - Malaysian Ringgit \n" +
                        "NZD - New Zealand Dollar \n" +
                        "PLN - Poland złoty \n" +
                        "SEK - Swedish krona \n" +
                        "RUB - Russian Ruble \n" +
                        "CNY - Chinese Yuan \n" +
                        "SGD - Singapore Dollar \n" +
                        "CHF - Swiss Franc \n" +
                        "INR - Indian Rupee \n").setColor(Color.ORANGE).build()).queue();


    }


}
