import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class JsonIO {

    //writes to the configs file with any key or value pair
    @SuppressWarnings("unchecked")
    public static void JSONWriter(String key, String value){

        JSONObject obj = new JSONObject();
        obj.put(key, value);


        try(FileWriter file = new FileWriter("config.json")){
            file.write(obj.toString());
            file.flush();

        }
        catch(IOException e){
            e.printStackTrace();

        }
}

    //read a key from a given path and returns a value
    public static String JSONReader(String key,String path) {

        JSONParser parser = new JSONParser();
        try
        {
            Object object = parser
                    .parse(new FileReader(path));

            //convert Object to JSONObject
            JSONObject jsonObject = (JSONObject)object;

            //Reading the String
            String value = (String) jsonObject.get(key);

            return value;

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
