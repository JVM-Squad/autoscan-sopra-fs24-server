package ch.uzh.ifi.hase.soprafs24.service;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class EnvReader {
    public void loadEnv(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("=", 2);
                if (parts.length >= 2) {
                    String key = parts[0].trim();
                    String value = parts[1].trim();
                    System.setProperty(key, value);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
