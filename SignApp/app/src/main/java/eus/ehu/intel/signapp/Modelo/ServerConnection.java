package eus.ehu.intel.signapp.Modelo;

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
    public boolean recibirPreguntasUsuario(String user) {
        return true;
    }

    @Override
    public boolean recibirPreguntasOtrosUsuarios(String user) {
        return true;
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
