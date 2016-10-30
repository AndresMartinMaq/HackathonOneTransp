package hackathon.silverstonestay;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import net.uk.onetransport.android.modules.bitcarriersilverstone.authentication.CredentialHelper;
import net.uk.onetransport.android.modules.bitcarriersilverstone.config.node.Node;
import net.uk.onetransport.android.modules.bitcarriersilverstone.config.node.NodeRetriever;

import java.util.ArrayList;
import java.util.Random;

import static android.graphics.Color.rgb;

public class MainActivity extends AppCompatActivity {

    Random r;

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

        r = new Random();

//        ArrayList<Node> nodes = new ArrayList<Node>();
//
//        try {
//            Log.e("HELLO","HELLO");
//            nodes = new NodeRetriever(this).retrieve();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            Log.d("Hello", "Hello");
//        }
//
//        for(Node n : nodes){
//            if(n.getId()==3||n.getId()==13||n.getId()==22){
//                Log.d("Latitude", ""+n.getLatitude());
//                Log.d("Longitude", ""+n.getLongitude());
//            }
//        }

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
            //changeColor(0.73);

            //TODO delete this testing dialog.
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Preview Traffic Score");
            builder.setMessage("Enter number between 0 and 1.");

            final EditText input = new EditText(this);
            input.setInputType(InputType.TYPE_CLASS_NUMBER);
            input.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
            builder.setView(input);

            // Set up the buttons with listeners
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String value = input.getText().toString();
                    if (value.equals( "")){ return; }
                    updateUI(Double.parseDouble(value));
                }
            });
            builder.create();
            builder.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void updateUI(double score){
        View view = findViewById(R.id.content_main);
        //Indicate how is the road state with color
        changeColor(view, score);
        //Suggest staying in silverstone and doing something else if roads are not too great.
        makeSuggestion(view, score);
    }

    //Change colour of something according to traffic congestion score 0-1, where 1 is optimal and 0 highly congested.
    private void changeColor(View view, double score){
        double b = 0*(1-score)*0.45;
        double r = 255*(1-score);
        double g = 255*score;

        int color = Color.argb(180,(int) r,(int) g, (int) b);
        view.setBackgroundColor(color);
    }

    private void makeSuggestion(View view, double score){
        String[] suggestions = getResources().getStringArray(R.array.suggestions_array);

        TextView roadStateMsg = (TextView) view.findViewById(R.id.textView1);
        TextView suggestionMsg = (TextView) view.findViewById(R.id.textView2);

        if (score < 0.6){
            //Make a random suggestion.
            suggestionMsg.setText(suggestions[r.nextInt(suggestions.length)]);
        } else {
            suggestionMsg.setText("You /could/ leave now but... "+suggestions[r.nextInt(suggestions.length)]);
        }

        String roadStateStr;
        if (score < 0.2){
            roadStateStr = "Traffic absolutely terrible right now";
        }else if (score < 0.4){
            roadStateStr = "Roads are congested right now";
        }else if (score < 0.5){
            roadStateStr = "Quite congested right now";
        }else if (score < 0.6){
            roadStateStr = "Traffic is not too bad, but not too great";
        }else if (score < 0.7){
            roadStateStr = "It's an okay time to leave by car";
        }else if (score < 0.8){
            roadStateStr = "It's a good time to leave by car";
        }else if (score < 0.9){
            roadStateStr = "Roads are clear, great time to leave";
        }else if (score <= 1){
            roadStateStr = "Roads are clear, best time to leave";
        }else{
            roadStateStr = "Score out of bounds (0-1), validation of stuff like this is not implemented";
        }

        roadStateMsg.setText(roadStateStr);
    }
}
