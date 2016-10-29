package hackathon.silverstonestay;

/**
 * Created by tangd on 29/10/2016.
 */

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.os.Bundle;
import android.util.Log;

import net.uk.onetransport.android.modules.bitcarriersilverstone.generic.RetrieverResult;
import net.uk.onetransport.android.modules.bitcarriersilverstone.generic.RetrieverLoader;
import net.uk.onetransport.android.modules.bitcarriersilverstone.data.travelsummary.TravelTime;

import java.util.ArrayList;

import static android.R.attr.fragment;

public class SilverstoneRealTimeRetriever extends Fragment implements LoaderManager.LoaderCallbacks<RetrieverResult<TravelTime>>{

    ArrayList<TravelTime> travelTimes;

    @Override
    public Loader<RetrieverResult<TravelTime>> onCreateLoader(int id, Bundle args) {
        return getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public void onLoadFinished(Loader<RetrieverResult<TravelTime>> loader, RetrieverResult<TravelTime> data) {
        travelTimes = data.getTs();
    }

    @Override
    public void onLoaderReset(Loader<RetrieverResult<TravelTime>> loader) {
        //LeaveBlank
    }

}
