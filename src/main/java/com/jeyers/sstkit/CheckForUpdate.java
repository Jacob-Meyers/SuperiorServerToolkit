package com.jeyers.sstkit;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import com.google.gson.Gson;
import org.bukkit.Bukkit;

import java.util.List;

import static com.jeyers.sstkit.SupToolkit.MODRINTH_PROJECT_ID;

///
/// Created by Jacob Meyers (TeamJEM)
/// File Created 3/19/2026
/// Last Edit    3/19/2026
///

public class CheckForUpdate {
    public static class modrinthGETinfo {
        public String version_number;
        public String version_type;
        public List<modrinthGETFileInfo> files;
    }

    public static class modrinthGETFileInfo {
        public String url;
        public boolean primary;
    }

    public static modrinthGETinfo checkForUpdate() throws Exception {
        String minecraftVersion = Bukkit.getServer().getMinecraftVersion();

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.modrinth.com/v2/project/"+MODRINTH_PROJECT_ID+"/version?game_versions=[%22"+minecraftVersion+"%22]"))
                .header("User-Agent", "sstkit") // IMPORTANT (Modrinth requires this)
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            Gson gson = new Gson();

            Type listType = new TypeToken<List<modrinthGETinfo>>() {}.getType();
            List<modrinthGETinfo> versions = gson.fromJson(response.body(), listType);

            for (modrinthGETinfo mgInfo : versions) {
                return mgInfo;
            }
        }

        return null;
    }
}
