package utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;



public class Displayer {
    public static String display(Object obj) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonString = gson.toJson(obj);

        return jsonString;
    }

    public static String colorizeText(String text, String color, boolean isBold) {
        String result = "";

        if (isBold) result = result.concat(ColorCode.BOLD);

        result = result.concat(color);
        result = result.concat(text);
        result = result.concat(ColorCode.RESET);

        return result;
    }

}
