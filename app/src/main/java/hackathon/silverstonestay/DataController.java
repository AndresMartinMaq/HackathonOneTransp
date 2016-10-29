package hackathon.silverstonestay;

import android.content.Context;
import android.support.annotation.StringRes;

import net.uk.onetransport.android.modules.bitcarriersilverstone.authentication.CredentialHelper;

/**
 * Created by tangd on 29/10/2016.
 */

public class DataController {

    public DataController(Context context){
        CredentialHelper.initialiseCredentials(context, context.getString(R.string.CIENT_AE_ID), context.getString(R.string.TOKEN), context.getString(R.string.INSTALLATION_ID));
    }
}
