package eus.ehu.intel.signapp.Modelo;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;

import java.util.Locale;

/**
 * Created by iubuntu on 31/12/17.
 */

public class LanguageGestor {

    public static void changeLanguage(Context context, String lang){
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Resources resources = context.getResources();
        Configuration config = resources.getConfiguration();
        config.locale=locale;
        resources.updateConfiguration(config,resources.getDisplayMetrics());
    }
}
