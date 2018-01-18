package eus.ehu.intel.signapp.Interfaces;

import android.content.Context;

import java.util.List;

import eus.ehu.intel.signapp.Modelo.Forum;

/**
 * Created by iubuntu on 23/12/17.
 */

public interface ServerInterface {
    public abstract boolean verificaLogin(String user, String password);
    public abstract boolean registro(String user, String password);
    public abstract boolean subirPregunta(String user, String password,String question);
    public abstract boolean enviarUrlRespuesta(String user, String password, int id_pregunta, String urlRespuesta, Context context);
    public abstract List<Forum> recibirPreguntasUsuario(String user);
    public abstract List<Forum> recibirPreguntasOtrosUsuarios(String user);
}
