package hackathon.silverstonestay;

/**
 * Created by tangd on 29/10/2016.
 */


import android.content.Context;
import android.util.Log;

import net.uk.onetransport.android.modules.bitcarriersilverstone.data.travelsummary.TravelSummary;
import net.uk.onetransport.android.modules.bitcarriersilverstone.data.travelsummary.TravelSummaryRetriever;
import net.uk.onetransport.android.modules.bitcarriersilverstone.data.travelsummary.TravelTime;

import java.util.ArrayList;

public class RefresherThread implements Runnable{

    private ArrayList<TravelSummary> travelSummaries;
    private Context context;

    public RefresherThread(Context context){
        this.context = context;
    }

    public void run() {
        while(true){
            try {
                travelSummaries = new TravelSummaryRetriever(context).retrieve();
                printTravelSummaries();
                Thread.sleep(20000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<TravelSummary> getTravelSummaries(){
        return travelSummaries;
    }

    public void printTravelSummaries(){
        for(TravelSummary ts : travelSummaries){
            try {
                Log.d("Speed",""+ts.getDetails().getCalculated().getSpeed());
                Log.d("RID", "" + ts.getrId());
                TravelTime[] travelTimes = ts.getTravelTimes();
                Log.d("FROM:" , "" + travelTimes[0].getFrom());
                Log.d("TO:" , "" + travelTimes[0].getTo());

            } catch (Exception e){
                // Hello
            }
        }
    }

}


