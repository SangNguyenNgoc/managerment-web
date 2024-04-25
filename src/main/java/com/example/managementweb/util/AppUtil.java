package com.example.managementweb.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class AppUtil {
    public String toJson(Object o) {
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        return gson.toJson(o);
    }

    public static Long parseId(String idString) {
        try {
            return Long.parseLong(idString);
        } catch (Exception ex) {
            return null;
        }
    }

    public static String dateToString(LocalDateTime dateTime) {
        if(dateTime == null){
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return dateTime.format(formatter);
    }
}
