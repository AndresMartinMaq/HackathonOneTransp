package hackathon.silverstonestay;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.uk.onetransport.android.modules.bitcarriersilverstone.data.travelsummary.TravelSummary;
import net.uk.onetransport.android.modules.bitcarriersilverstone.data.travelsummary.TravelSummaryRetrieverLoader;
import net.uk.onetransport.android.modules.bitcarriersilverstone.generic.RetrieverResult;


import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class RealTimeRetriever extends Fragment implements LoaderManager.LoaderCallbacks<RetrieverResult<TravelSummary>>{

    ArrayList<TravelSummary> travelSummaries;

    public RealTimeRetriever() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_real_time_retriever, container, false);
    }

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
