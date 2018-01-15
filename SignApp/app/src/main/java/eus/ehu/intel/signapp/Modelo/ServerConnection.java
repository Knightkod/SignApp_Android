package eus.ehu.intel.signapp.Modelo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import eus.ehu.intel.signapp.Interfaces.ServerInterface;
import eus.ehu.intel.signapp.R;

/**
 * Created by iubuntu on 23/12/17.
 */

public class ServerConnection implements ServerInterface {

    private RestClient restClient;

    public ServerConnection(String baseUrl) {
        restClient = new RestClient(baseUrl);
    }

    @Override
    public boolean verificaLogin(String user, String password) {
        boolean verification = false;
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("nick", user);
            jsonObject.put("password", password);
            String resultCode = restClient.postJson(jsonObject, "login");
            if (resultCode.equals("OK"))
                verification = true;

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return verification;
    }

    @Override
    public boolean registro(String user, String password) {
        boolean verification = false;
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("nick", user);
            jsonObject.put("password", password);
            String resultCode = restClient.postJson(jsonObject, "addUser");
            if (resultCode.equals("OK"))
                verification = true;

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return verification;
    }

    @Override
    public List<Forum> recibirPreguntasUsuario(String user) {
        List<Forum> listForumAux = new ArrayList<Forum>();
        Forum forumAux;
        try {
            JSONObject json = restClient.getJson(String.format("requestQuestions?login=%s", user));
            JSONArray jsonArray = json.getJSONArray("forum");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject item = jsonArray.getJSONObject(i);
                forumAux = new Forum();
                forumAux.setId(item.getInt("id"));
                forumAux.setQuestion(item.getString("question"));
                forumAux.setDate(item.getInt("date"));
                listForumAux.add(forumAux);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return listForumAux;
    }

    @Override
    public List<Forum> recibirPreguntasOtrosUsuarios(String user) {
        List<Forum> listForumAux = new ArrayList<Forum>();
        Forum forumAux;
        try {
            JSONObject json = restClient.getJson(String.format("requestOtherUserQuestion?login=%s", user));
            JSONArray jsonArray = json.getJSONArray("forum");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject item = jsonArray.getJSONObject(i);
                forumAux = new Forum();
                forumAux.setId(item.getInt("id"));
                forumAux.setNick(item.getString("nick"));
                forumAux.setQuestion(item.getString("question"));
                forumAux.setDate(item.getInt("date"));
                listForumAux.add(forumAux);
                System.out.println(i);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listForumAux;
    }

    @Override
    public boolean subirPregunta(String user, String password, String question) {
        boolean verification=false;
        JSONObject json = new JSONObject();
        try {
            json.put("id",JSONObject.NULL);
            json.put("nick",user);
            json.put("password",password);
            json.put("resp",JSONObject.NULL);
            json.put("question",question);
            json.put("date",JSONObject.NULL);
            String resultCode = restClient.postJson(json, "forumQuestion");
            if (resultCode.equals("OK"))
                verification = true;

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return verification;

    }

    @Override
    public boolean enviarUrlRespuesta(String user, String password, int id_pregunta, String
            urlRespuesta) {
        boolean verification=false;
        JSONObject json = new JSONObject();
        String[] auxURL = urlRespuesta.split("/");
        String url = "";
        if (auxURL.length >= 5) {
            url = Integer.toString(R.string.dropboxContentURL) + auxURL[4] + "/" + auxURL[5];
            try {
                json.put("id",id_pregunta);
                json.put("nick",user);
                json.put("password",password);
                json.put("resp",url);
                json.put("question",JSONObject.NULL);
                json.put("date",JSONObject.NULL);
                String resultCode = restClient.postJson(json, "forumResponse");
                if (resultCode.equals("OK"))
                    verification = true;

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return verification;
    }


}