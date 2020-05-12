package fr.leroideskiwis.bot;

import fr.leroideskiwis.bot.util.Util;
import okhttp3.*;
import org.json.JSONObject;

import java.io.IOException;

public class DiscordAPI {

    private final String BASEURL = "https://discordapp.com/api";
    private final String token;
    private final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private final OkHttpClient client;

    public DiscordAPI(String token, OkHttpClient okHttpClient){
        this.token = token;
        this.client = okHttpClient;
    }

    /**
     * Send a request to {@code path} with the data {@code json} and using the method {@code method}
     * @param path the request will be send there
     * @param json the data of the request
     * @param method the method of the request (POST, GET, DELETE etc...)
     * @return the final request
     */
    public Request request(String path, JSONObject json, RequestMethod method) {
        RequestBody requestBody = RequestBody.create(method == RequestMethod.GET ? "{}" : json.toString(), JSON);
        Request.Builder request = new Request.Builder().url(path);
        request.addHeader("Authorization", "Bot " + token);
        switch (method) {
            case GET:
                request.get();
                break;
            case POST:
                request.post(requestBody);
                break;
            case PUT:
                request.put(requestBody);
                break;
            case PATCH:
                request.patch(requestBody);
                break;
            case DELETE:
                request.delete(requestBody);
                break;
        }
        return request.build();
    }

    /**
     * Same as {@link DiscordAPI#request(String, JSONObject, RequestMethod)} but here we execute it
     * @param path the request will be send there
     * @param json the data of the request
     * @param method the method of the request (POST, GET, DELETE etc...)
     * @return the response
     */
    public JSONObject executeRequest(String path, JSONObject json, RequestMethod method){
        try(Response response = client.newCall(request(path, json, method)).execute()){
            ResponseBody body = response.body();
            JSONObject jsonResponse = body == null ? new JSONObject() : new JSONObject(body.string());
            Util.log("Réponse :" +jsonResponse);
            return jsonResponse;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new JSONObject();
    }

    /**
     * Same as {@link DiscordAPI#requestApi(String, JSONObject, RequestMethod)} but here we execute it
     * @param path the request will be send at "https://discordapp.com/api"+path
     * @param json the data of the request
     * @param method the method of the request (POST, GET, DELETE etc...)
     * @return the response
     */
    public JSONObject executeApiRequest(String path, JSONObject json, RequestMethod method){
        return executeRequest(BASEURL+path, json, method);
    }
    /**
     * Send a request to the api discord using {@code path} with the data {@code json} and using the method {@code method}
     * @param path the request will be send at "https://discordapp.com/api"+path
     * @param json the data of the request
     * @param method the method of the request (POST, GET, DELETE etc...)
     * @return the response
     */
    public JSONObject requestApi(String path, JSONObject json, RequestMethod method){
        return executeRequest(BASEURL+path, json, method);
    }

    /**
     * append the token in the jsonObject
     * @param key the token will be put in the format {key, token} in the jsonObject
     * @param jsonObject the jsonObject who will be append
     * @return the jsonObject
     */
    public JSONObject appendToken(String key, JSONObject jsonObject){
        jsonObject.put(key, token);
        return jsonObject;
    }

}
