import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.lang.StringBuilder;
import java.util.Arrays;
import java.util.List;

public class RiotApi{
    //this whole page was a mistake
    //when adding something new from a new page you need to create:
    //1- get a new something/something URl Method
    //2- make a case specific getsomething/somethingJsonObject Method
    //If the url returns an array, it might also return it empty. You have to fill it with an object and as many K/V pairs
    //as you call for eventually.
    //Yes I'm sure there's better ways to do this but it's a goddamn fucking miracle that this shit works at all


    //update all of the comments above are useless ok ty

    static String apiKey = JsonIO.JSONReader("riotApiKey","tokens.json");

        //calls the page with the specific URL(given in the method, each URL will vary) and  returns a JSONObject
    private static JSONObject getSummonerByNameJsonObject(URL lolApiSite, MessageReceivedEvent event) throws ParseException{
        JSONParser parser = new JSONParser();
        JSONObject lolApi = null;
        try{
            BufferedReader in = new BufferedReader(new InputStreamReader(lolApiSite.openStream()));
            String lolApiJson = in.readLine();
            lolApi = (JSONObject) parser.parse(lolApiJson);

        }
        catch(FileNotFoundException e){
            event.getChannel().sendMessage("Check your name and server!").queue();
        }
        catch(IOException e){
            //event.getChannel().sendMessage("Either you got a null pointer, your parameters are wrong" +
                   // " or you need to reset your API key." +
                   // " Thanks rito if it's the last one.").queue();
        }

        return lolApi;
    }

    //gets the JsonObject from an array
    @SuppressWarnings("unchecked")
    private static JSONObject getPositionsByIdJsonObjectArray(URL lolApiSite, MessageReceivedEvent event) throws IOException, ParseException{
        JSONParser parser = new JSONParser();
        JSONObject lolApi= null;
        try{
            JSONArray lolApiArr;
            BufferedReader in = new BufferedReader(new InputStreamReader(lolApiSite.openStream()));
            String lolApiJson = in.readLine();
            lolApiArr = (JSONArray) parser.parse(lolApiJson);
            //creating object to be added to an empty array
            JSONObject noRank = new JSONObject();
            noRank.put("tier","");
            noRank.put("rank","");
            noRank.put("leagueName","Has no rank.");
            //no rank players return an empty array, so I have to give it an object
            if(lolApiArr.isEmpty()){
                lolApiArr.add(0,noRank);
            }
            lolApi = (JSONObject) lolApiArr.get(0);

        }
        catch(FileNotFoundException e){
            event.getChannel().sendMessage("Check your name and server!").queue();
        }
        catch(NullPointerException e){

        }
        return lolApi;
    }
    //Makes all human input server codes become the required server codes. E.g : "euw" becomes "euw1"
    //inb4 all this could have been checked with the given exception and I'm a retard
    private static String serverbuilder(String server) {

        StringBuilder sb = new StringBuilder(server);


        switch (server) {
            case "euw":
            case "na":
            case "br":
            case "jp":
            case "tr":
            case "pbe":
                sb.append("1");
                break;
            case "eune":
                sb.deleteCharAt(3).append("1");
                break;
            case "lan":
                sb.deleteCharAt(2).append("1");
                break;
            case "las":
                sb.deleteCharAt(2).append("2");
                break;
            case "oce":
                sb.deleteCharAt(2).append("1");
                break;
        }
        return sb.toString();
    }

    //checks if server provided exists
    private static Boolean serverExists(String server){
        String serverListArr[] = {"euw","na","br","jp","tr","tr","pbe","eune","lan","las","oce"};
        List<String> serverList = Arrays.asList(serverListArr);
        Boolean serverExistsBool = serverList.contains(server);
        return serverExistsBool;
    }




//gets the url and makes a jsonobject out of that url by calling the respective method
    private static JSONObject getSummonerNameUrl(String server, String name,MessageReceivedEvent event) throws IOException,ParseException{
        URL lolApiSiteSummonersByName = new URL("https://"+server+".api.riotgames.com/lol/summoner/v3/summoners/by-name/"+
                name+apiKey); // makes url just for the summoner search by name and their IDs(useful for a lot of stats)
        JSONObject lolApiSummonersByName = new JSONObject(getSummonerByNameJsonObject(lolApiSiteSummonersByName,event));//calls the object get method with the url just for summoner search

        return lolApiSummonersByName;
    }

    //gets the url and makes a jsonobject out of that url by calling the respective method.
    // This one is extra fucked because it can return an empty array(which is somewhat treated in getPositionsByIdObjectasArray
    private static JSONObject getSummonerIDUrl(String server, String summonerid,MessageReceivedEvent event) throws IOException,ParseException{


        URL lolApiSitePositionsById = new URL("https://"+server+".api.riotgames.com/lol/league/v3/positions/by-summoner/"
                               +summonerid+apiKey);//makes url for Positions/ById API page
        JSONObject lolApiPositionsById = new JSONObject(getPositionsByIdJsonObjectArray(lolApiSitePositionsById,event));
        return lolApiPositionsById;
    }


    //Random player info search by both ID and Name
    public static void playersearch(MessageReceivedEvent event) throws IOException, ParseException {

        if(event.getMessage().getContentRaw().split(" ").length == 3) {
            //variables to read the message search parameters and the specific URL for this method
            String server = event.getMessage().getContentRaw().split(" ")[1].toLowerCase();
            String name = event.getMessage().getContentDisplay().split(" ")[2];

        if(serverExists(server)){
            server = serverbuilder(server);
            try{
            JSONObject lolApiSummonersByName = getSummonerNameUrl(server,name,event);//SummonersByName Object
            String summonerid = lolApiSummonersByName.get("id").toString();
            JSONObject lolApiPositionsById = getSummonerIDUrl(server,summonerid,event);//PositionsByID Object

            //actually doing stuff and printing the message also idk exception handling lmao

                String summonersName = lolApiSummonersByName.get("name").toString();
                String summonerLevel = lolApiSummonersByName.get("summonerLevel").toString();
                String accountid = lolApiSummonersByName.get("accountId").toString();

                String tier = lolApiPositionsById.get("tier").toString();
                String rank = lolApiPositionsById.get("rank").toString();
                String leagueName = lolApiPositionsById.get("leagueName").toString();

                event.getChannel().sendMessage(summonersName + " is currently level: " + summonerLevel + " \n" +
                        "Summoner ID: "+ summonerid +"\nAccount ID: "+ accountid +
                        "\nSolo Queue Rank : "+ tier + " "+ rank + " - "+leagueName).queue();
            }
            catch(NullPointerException e){
                event.getChannel().sendMessage("Either you got a null pointer, your parameters are wrong" +
                        " or you need to reset your API key." +
                    " Thanks rito if it's the last one.").queue();
            }
        }
        else{
            event.getChannel().sendMessage("That server doesn't exist!").queue();
        } }
        else{
            event.getChannel().sendMessage("Invalid Arguments!").queue();
        }
    }
}
