package eus.ehu.intel.signapp.Interfaces;

/**
 * Created by iubuntu on 23/12/17.
 */

public interface ServerInterface {
    public abstract boolean verificaLogin(String user, String password);
    public abstract boolean registro(String user, String password);
    public abstract boolean subirPregunta(String user, String password,String question);
    public abstract boolean enviarUrlRespuesta(String user, String password,int id_pregunta,String urlRespuesta);
    public abstract boolean recibirPreguntasUsuario(String user);
    public abstract boolean recibirPreguntasOtrosUsuarios(String user);
}
