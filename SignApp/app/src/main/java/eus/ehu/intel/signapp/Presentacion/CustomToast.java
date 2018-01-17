package eus.ehu.intel.signapp.Presentacion;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import eus.ehu.intel.signapp.R;

/**
 * Created by iubuntu on 16/01/18.
 */

public class CustomToast {

    public static void createToast(String type, String msg, LayoutInflater inflater, Context context){
        View customToastBase=null;
        switch (type){
            case "error":
                customToastBase=inflater.inflate(R.layout.toast_error,null);
                break;
            case "success":
                customToastBase=inflater.inflate(R.layout.toast_info,null);

                break;
            case "warning":
                customToastBase=inflater.inflate(R.layout.toast_warning,null);
                break;
        }
        if(customToastBase!=null){
            TextView textView=(TextView) customToastBase.findViewById(R.id.toastTextView);
            textView.setText(msg);
            Toast customToast = new Toast(context);
            customToast.setView(customToastBase);
            customToast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.BOTTOM,0,0);
            customToast.setDuration(Toast.LENGTH_SHORT);
            customToast.show();
        }

    }
}
