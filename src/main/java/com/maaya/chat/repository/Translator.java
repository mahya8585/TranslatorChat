package com.maaya.chat.repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.maaya.chat.data.Translations;
import com.squareup.okhttp.*;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.maaya.chat.Configuration.LOCATION;
import static com.maaya.chat.Configuration.SUBSCRIPTION_KEY;

public class Translator {
    //TODO user1が来たら日本語 -> 英語, user2が来たら英語->日本語の翻訳を行う

    // This function performs a POST request.
    public String post(String message, String fromLanguage, String toLanguage) throws IOException {
        HttpUrl url = new HttpUrl.Builder()
                .scheme("https")
                .host("api.cognitive.microsofttranslator.com")
                .addPathSegment("/translate")
                .addQueryParameter("api-version", "3.0")
                .addQueryParameter("from", fromLanguage)
                .addQueryParameter("to", toLanguage)
                .build();

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType,
                "[{\"Text\": \"" + message + "\"}]");
        Request request = new Request.Builder().url(url).post(body)
                .addHeader("Ocp-Apim-Subscription-Key", SUBSCRIPTION_KEY)
                .addHeader("Ocp-Apim-Subscription-Region", LOCATION)
                .addHeader("Content-type", "application/json")
                .build();
        Response response = new OkHttpClient().newCall(request).execute();
        return response.body().string();
    }

    // This function prettifies the json response.
    public String prettify(String json_text) {
        JsonParser parser = new JsonParser();
        JsonElement json = parser.parse(json_text);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        Type listType = new TypeToken<List<Translations>>(){}.getType();
        List<Translations> response = gson.fromJson(json, listType);

        List<String> message = new ArrayList<>();
        response.stream().map(res ->
                res.getTranslations().stream().map(r -> r.getText()).collect(Collectors.toList())
        ).forEach(m -> message.addAll(m));

        return String.join(" ", message);
    }

    public String createTranslatedMessage(String message, String from, String to) throws IOException {
        return prettify(post(message, from, to));
    }
}
