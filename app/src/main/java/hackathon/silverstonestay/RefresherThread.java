package hackathon.silverstonestay;

/**
 * Created by tangd on 29/10/2016.
 */


import android.content.Context;
import android.util.Log;

import net.uk.onetransport.android.modules.bitcarriersilverstone.config.node.Node;
import net.uk.onetransport.android.modules.bitcarriersilverstone.config.node.NodeRetriever;
import net.uk.onetransport.android.modules.bitcarriersilverstone.data.travelsummary.TravelSummary;
import net.uk.onetransport.android.modules.bitcarriersilverstone.data.travelsummary.TravelSummaryRetriever;
import net.uk.onetransport.android.modules.bitcarriersilverstone.data.travelsummary.TravelTime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
        ArrayList<Node> nodes = new ArrayList<Node>();

        try {
            nodes = new NodeRetriever(context).retrieve();

        } catch (Exception e) {
            e.printStackTrace();
        }
        for(TravelSummary ts : travelSummaries){
            try {
                Double spe = ts.getDetails().getCalculated().getSpeed();

                //speeds.add(spe);
                Log.d("Speed",""+ts.getDetails().getCalculated().getSpeed());

                Log.d("RID", "" + ts.getrId());
                TravelTime[] travelTimes = ts.getTravelTimes();
                Log.d("FROM:" , "" + travelTimes[0].getFrom());
                Log.d("TO:" , "" + travelTimes[0].getTo());




                for(Node n : nodes){
                    String from =  n.getCustomerName().replaceFirst("-.*","");
                    String from2 = travelTimes[0].getFrom().replaceFirst("Link_","");
                    if(from.equals(from2)) {
                        Log.d("Latitude", from + " " + n.getLatitude());
                        Log.d("Longitude", from + " " + n.getLongitude());
                    }
                }
                Log.d("esed", "*******************************");
                Log.d("esed","*******************************");
                Map<Integer, Double> map = new HashMap<Integer, Double>();
                map.put(175,24.0);
                map.put(142,24.0);
                map.put(143,24.0);
                map.put(144,24.0);
                map.put(221,48.0);
                map.put(228,48.0);
                map.put(218,48.0);
                map.put(204,48.0);


                Double baseline;
                try {
                    baseline = map.get(ts.getrId());
                    Double scr;

                    if(spe < baseline ){
                        scr = spe * (0.5/baseline);
                    }
                    else {
                        if (baseline.equals(24.0)) {
                            scr = 0.5 + ((spe - baseline) * (0.5 / 16));
                        } else
                            scr = 0.5 + ((spe - baseline) * (0.5 / 48));

                    }
                    Log.d("scr:" , ""+scr);

                    speeds.add(scr);
                }
                catch (Exception e){

                }



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


