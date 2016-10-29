package hackathon.silverstonestay;

import android.content.Context;
import android.support.annotation.StringRes;
import android.util.Log;

import net.uk.onetransport.android.modules.bitcarriersilverstone.authentication.CredentialHelper;
import net.uk.onetransport.android.modules.bitcarriersilverstone.config.sketch.Sketch;
import net.uk.onetransport.android.modules.bitcarriersilverstone.config.sketch.SketchRetriever;

import java.lang.reflect.Array;
import java.util.ArrayList;


/**
 * Created by tangd on 29/10/2016.
 */

public class DataController {

    private ArrayList<Sketch> sketches;

    public DataController(Context context){
        CredentialHelper.initialiseCredentials(context, context.getString(R.string.CLIENT_AE_ID), context.getString(R.string.TOKEN), context.getString(R.string.INSTALLATION_ID));
        try{
            setSketches(context);
        } catch(Exception e){
            Log.d("Failure", "Sketches Failed");
        }
    }

    private void setSketches(Context context) throws Exception {
        sketches = new SketchRetriever(context).retrieve();
    }

    public ArrayList<Sketch> getSketches(){
        return sketches;
    }
}
