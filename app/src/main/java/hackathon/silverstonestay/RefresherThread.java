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
    private ArrayList<Double> speeds = new ArrayList<>();

    public RefresherThread(Context context){
        this.context = context;
    }

    public void run() {
        while(true){
            try {
                travelSummaries = new TravelSummaryRetriever(context).retrieve();
                findMean();
                Thread.sleep(20000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<TravelSummary> getTravelSummaries(){
        return travelSummaries;
    }

    public Double findMean(){
        speeds.clear();
        for(TravelSummary ts : travelSummaries){
            try {
                Double spe = ts.getDetails().getCalculated().getSpeed();
                //speeds.add(spe);
                //Log.d("Speed",""+ts.getDetails().getCalculated().getSpeed());

//                Log.d("RID", "" + ts.getrId());
//              TravelTime[] travelTimes = ts.getTravelTimes();
//                Log.d("FROM:" , "" + travelTimes[0].getFrom());
//                Log.d("TO:" , "" + travelTimes[0].getTo());
                Double scr;

                if(spe < 70 ){
                    scr = spe * (0.5/70);
                }
                else
                    scr = 0.5 +  ((spe-70) * (0.5/42));
                speeds.add(scr);

            } catch (Exception e){
                // Hello
            }
        }
        Double total = 0.0;
        int count = 0;
        for(Double s : speeds){
            total += s;
            count++;
        }
        Log.d("mean:" , "" + total/count);
        return total/count;
    }

}


