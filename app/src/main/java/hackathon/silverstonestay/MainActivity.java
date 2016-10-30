package hackathon.silverstonestay;

import android.nfc.Tag;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import net.uk.onetransport.android.modules.bitcarriersilverstone.authentication.CredentialHelper;
import net.uk.onetransport.android.modules.bitcarriersilverstone.config.node.Node;
import net.uk.onetransport.android.modules.bitcarriersilverstone.config.node.NodeRetriever;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CredentialHelper.initialiseCredentials(this, getString(R.string.CLIENT_AE_ID), getString(R.string.TOKEN), getString(R.string.INSTALLATION_ID));
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        ArrayList<Node> nodes = new ArrayList<Node>();

        try {
            nodes = new NodeRetriever(this).retrieve();

        } catch (Exception e) {
            e.printStackTrace();
            Log.d("Hello", "Hello");
        }
        Log.e("node",""+nodes.size());
//
//        for(Node n : nodes){
//           String from =  n.getCustomerName().replaceFirst("-.*","");
//            if(from.equals("3")||from.equals("13")||from.equals("22")){
//                Log.d("Latitude", from + " "+n.getLatitude());
//
//                Log.d("Longitude", from + " " +n.getLongitude());
//            }
//        }
//
      RefresherThread refresher = new RefresherThread(this);
        refresher.run();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
