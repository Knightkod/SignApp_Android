package eus.ehu.intel.signapp.Modelo;

import java.util.ArrayList;
import java.util.List;

import eus.ehu.intel.signapp.Interfaces.ServerInterface;

/**
 * Created by iubuntu on 23/12/17.
 */

public class ServerConnection implements ServerInterface {


    @Override
    public boolean verificaLogin(String user, String password) {
        /*puenteando consulta a server*/
        boolean verification=false;
        if(user.equals("prueba")){
            if(password.equals("prueba"))
                verification=true;
        }
        return verification;
    }

    @Override
    public boolean registro(String user, String password) {
            /*puenteando registro a server*/
        boolean verification=false;
        if(!user.equals(null)&&!user.equals("")){
            if(!password.equals(null)&&!password.equals(""))
                verification=true;
        }
        return verification;
    }

    @Override
    public List<Forum> recibirPreguntasUsuario(String user) {
        List<Forum> listForum= new ArrayList<Forum>();
        //parseando respuesta del servidor respecto a preguntas de otros usuarios
        List<Forum> listForumAux= new ArrayList<Forum>();
        Forum forumAux = new Forum();
        int dateAux=20171211;
        for (int i=0; i<10;i++) {
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
        Forum forumAux = new Forum();
        int auxId=1;
        //parseando respuesta del servidor respecto a preguntas de otros usuarios
        for (int i=0; i<10;i++) {

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

        return true;
    }




}
