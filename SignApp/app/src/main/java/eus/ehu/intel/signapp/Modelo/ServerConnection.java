package eus.ehu.intel.signapp.Modelo;

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

    public ServerConnection(String baseUrl){
        restClient=new RestClient(baseUrl);
    }

    @Override
    public boolean verificaLogin(String user, String password) {
        boolean verification=false;
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("nick", user);
            jsonObject.put("password", password);
            String resultCode = restClient.postJson(jsonObject, "login");
           if(resultCode.equals("OK"))
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
            /*puenteando registro a server*/
        boolean verification=false;
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("nick", user);
            jsonObject.put("password", password);
            String resultCode = restClient.postJson(jsonObject, "addUser");
            if(resultCode.equals("OK"))
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
        List<Forum> listForum= new ArrayList<Forum>();
        //parseando respuesta del servidor respecto a preguntas de otros usuarios
        List<Forum> listForumAux= new ArrayList<Forum>();
        Forum forumAux;
        int dateAux=20171211;
        for (int i=0; i<10;i++) {
            forumAux = new Forum();
            forumAux.setDate(dateAux++);
            forumAux.setQuestion("????"+Integer.toString(i));
            forumAux.setResp("trying"+Integer.toString(i));
            listForumAux.add(forumAux);
        }

        return listForumAux;
    }

    @Override
    public List<Forum> recibirPreguntasOtrosUsuarios(String user) {
        List<Forum> listForum= new ArrayList<Forum>();
        //parseando respuesta del servidor respecto a preguntas de otros usuarios
        List<Forum> listForumAux= new ArrayList<Forum>();
        Forum forumAux;
        int auxId=1;
        //parseando respuesta del servidor respecto a preguntas de otros usuarios
        for (int i=0; i<10;i++) {
            forumAux = new Forum();
            forumAux.setQuestion("????Others"+Integer.toString(i));
            forumAux.setId(auxId++);
            listForumAux.add(forumAux);
        }
        return listForumAux;
    }

    @Override
    public boolean subirPregunta(String user, String password, String question) {
        return true;
    }

    @Override
    public boolean enviarUrlRespuesta(String user, String password, int id_pregunta, String urlRespuesta) {
        String[] auxURL = urlRespuesta.split("/");
        String url="";
        if(auxURL.length>=5)
            url= Integer.toString(R.string.dropboxContentURL)+auxURL[4]+"/"+auxURL[5];

        return true;
    }




}