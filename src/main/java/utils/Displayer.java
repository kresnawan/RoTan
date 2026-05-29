package utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Displayer {
    public static String display(Object obj) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonString = gson.toJson(obj);

        return jsonString;
    }

}
