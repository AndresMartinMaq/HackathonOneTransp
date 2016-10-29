package hackathon.silverstonestay;

/**
 * Created by tangd on 29/10/2016.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.content.Context;
import android.support.v4.content.Loader;
import android.os.Bundle;
import android.util.Log;

import com.interdigital.android.dougal.resource.callback.DougalCallback;

import net.uk.onetransport.android.modules.bitcarriersilverstone.data.travelsummary.TravelSummary;
import net.uk.onetransport.android.modules.bitcarriersilverstone.data.travelsummary.TravelSummaryRetrieverLoader;
import net.uk.onetransport.android.modules.bitcarriersilverstone.generic.RetrieverResult;

import java.util.ArrayList;

public class SilverstoneRealTimeRetriever extends Fragment implements LoaderManager.LoaderCallbacks<RetrieverResult<TravelSummary>>{

    ArrayList<TravelSummary> travelSummaries;

    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(0, null, this); //This will call onCreateLoader appropriately.
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public Loader<RetrieverResult<TravelSummary>> onCreateLoader(int id, Bundle args) {
        return new TravelSummaryRetrieverLoader(getContext());
    }

    @Override
    public void onLoadFinished(Loader<RetrieverResult<TravelSummary>> loader, RetrieverResult<TravelSummary> data) {
        travelSummaries = data.getTs();
    }

    @Override
    public void onLoaderReset(Loader<RetrieverResult<TravelSummary>> loader) {

    }

}
