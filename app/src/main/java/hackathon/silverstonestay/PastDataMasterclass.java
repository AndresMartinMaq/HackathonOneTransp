package hackathon.silverstonestay;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import net.uk.onetransport.android.modules.bitcarriersilverstone.data.travelsummary.Stat;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.*;

import static android.R.attr.data;
import static android.R.attr.path;

/**
 * Created by Andres on 29/10/2016.
 */

public class PastDataMasterclass{

    //Upon changing the database schema, you must increment the database version manually here.
    private static final int DATABASE_VERSION = 1;
    public static String DATABASE_NAME = "pastData.db";
    private static PastDataMasterclass instance;

    private Integer[] sensorNumbers = {1159, 1160, 1161, 1162, 1163, 1164, 1165, 1166, 1167, 1168, 1169, 1170, 1171, 1172};
    private String path = "C:/Users/Andres/Documents/University Work/4YGDP/Hack/Data/Raw BitCarrier data/";
    private String[] fileNames = {
            "20160600_1159. csv", "20160600_1171. csv", "20160800_1169. csv", "20160900_1167. csv",
            "20160600_1160. csv", "20160600_1172. csv", "20160800_1170. csv", "20160900_1168. csv",
            "20160600_1161. csv", "20160800_1159. csv", "20160800_1171. csv", "20160900_1169. csv",
            "20160600_1162. csv", "20160800_1160. csv", "20160800_1172. csv", "20160900_1170. csv",
            "20160600_1163. csv", "20160800_1161. csv", "20160900_1159. csv", "20160900_1171. csv",
            "20160600_1164. csv", "20160800_1162. csv", "20160900_1160. csv", "20160900_1172. csv",
            "20160600_1165. csv", "20160800_1163. csv", "20160900_1161. csv",
            "20160600_1166. csv", "20160800_1164. csv", "20160900_1162. csv",
            "20160600_1167. csv", "20160800_1165. csv", "20160900_1163. csv",
            "20160600_1168. csv", "20160800_1166. csv", "20160900_1164. csv",
            "20160600_1169. csv", "20160800_1167. csv", "20160900_1165. csv",
            "20160600_1170. csv", "20160800_1168. csv", "20160900_1166. csv"};

    public static void main( String args[] ) throws Exception {
        Connection c = null;
        Statement stmt = null;
        PastDataMasterclass thisClass = new PastDataMasterclass();

        Class.forName("org.sqlite.JDBC");
        c = DriverManager.getConnection("jdbc:sqlite:" + thisClass.path+DATABASE_NAME);
        c.setAutoCommit(false);
        Log.d("Hey", "Opened database successfully");
        stmt = c.createStatement();

        thisClass.createTables(c, stmt);
        Log.d("Hey", "Created tables");
        thisClass.populate(c, stmt);
        Log.d("Hey", "populated");

        ResultSet result = stmt.executeQuery("select * from sensor1170;");
        Log.d("Hey", "Got stuff?");

        stmt.close();
        c.close();
    }

    private void createTables(Connection db, Statement smt) throws SQLException {
        for (Integer i = 0; i < sensorNumbers.length; i++) {

            String tableName = "sensor" + i.toString();
            //Create a table for each sensor.
            final String sqlStatement = "CREATE TABLE " + tableName + " (" +
                    " MAC_hash " + " INTEGER PRIMARY KEY," +
                    " day " + " TEXT, " +
                    " time " + " TEXT,);";

            smt.execute(sqlStatement);
        }
    }

    private void populate(Connection db, Statement smt) throws SQLException {
        BufferedReader br = null;
        //For each csv file, populate its table.
        for ( String file : fileNames) {

            String tableName = "sensor"+file.split("_")[1].split(".")[0];

            try {
                br = new BufferedReader(new FileReader(path));
                String line;
                float count = 0f;
                while ((line = br.readLine()) != null) {

                    String[] arr = line.split(",");
                    String device = arr[1];
                    String tstamp = arr[5];
                    String day = tstamp.split(" ")[0];
                    String time = tstamp.split(" ")[1];

                    smt.execute("INSERT INTO " + tableName + " values( "+
                            device+" , "+
                            day+" , "+
                            time+" );");
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
