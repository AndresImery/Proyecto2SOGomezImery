/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Classes;

import com.google.gson.*;
import java.io.*;
/**
 *
 * @author andresimery
 */
class JSONManager { // Manager para guardar todo en la comp
    private static final String FILE_PATH = "filesystem.json";
    
    public static void saveSystem(FileSystem system) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (Writer writer = new FileWriter(FILE_PATH)) {
            gson.toJson(system, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static FileSystem loadSystem() {
        Gson gson = new Gson();
        try (Reader reader = new FileReader(FILE_PATH)) {
            return gson.fromJson(reader, FileSystem.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
