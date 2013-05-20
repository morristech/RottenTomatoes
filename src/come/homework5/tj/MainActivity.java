/*
 	Assignment #5
 	Author: Josh Leonard & Tyler Shay
 	Filename: MainActivty.java
 
 */
package come.homework5.tj;

import java.lang.reflect.Field;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {

	final static private String boxURL = "http://api.rottentomatoes.com/api/public/v1.0/lists/movies/box_office.json?apikey=mf69tpgbd56r9fjvg4ktrvud&limit=50";
	final static private String theaterURL = "http://api.rottentomatoes.com/api/public/v1.0/lists/movies/in_theaters.json?apikey=mf69tpgbd56r9fjvg4ktrvud&page_limit=50";
	final static private String openingURL = "http://api.rottentomatoes.com/api/public/v1.0/lists/movies/opening.json?apikey=mf69tpgbd56r9fjvg4ktrvud&limit=50";
	final static private String upcomingURL = "http://api.rottentomatoes.com/api/public/v1.0/lists/movies/upcoming.json?apikey=mf69tpgbd56r9fjvg4ktrvud&page_limit=50";
	final static  String favorites = "favorites";
	final static String SERIALIZABLE_KEY = "Serializable";
	final static String SERIALIZABLE_KEY3 = "Serializable3";
	
	String temp;
	ListView myListView;
	Intent i;
	

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        ////Allows Overflow Menu on all devices
		try {
			ViewConfiguration config = ViewConfiguration.get(this);
			Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
			if(menuKeyField != null) {
				menuKeyField.setAccessible(true);
				menuKeyField.setBoolean(config, false);
			}
		} catch (Exception ex) {
			// Ignore
		}
	 
        ListView myListView = (ListView) findViewById(R.id.mylist);
        ArrayList<String> values = new ArrayList<String>();
        values.add("My Favorite Movies");
        values.add("Box Office Movies");
        values.add("In Theaters Movies");
        values.add("Opening Movies");
        values.add("Upcoming Movies");
        
        
        MainActivityAdapter adapter = new MainActivityAdapter(this, values);
        
        myListView.setAdapter(adapter);  
        
        myListView.setOnItemClickListener(new OnItemClickListener() {
        	public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
        		
        		Log.v("poistion = ", ((Integer)position).toString());
				
				
				if(position == 1){
					i=new Intent(getBaseContext(), MoviesActiviy.class);
					i.putExtra(SERIALIZABLE_KEY, boxURL);
					startActivity(i);
				}
				
				else if(position == 2){
					i=new Intent(getBaseContext(), MoviesActiviy.class);
					i.putExtra(SERIALIZABLE_KEY, theaterURL);
					startActivity(i);
				}
				
				else if(position == 3){
					i=new Intent(getBaseContext(), MoviesActiviy.class);
					i.putExtra(SERIALIZABLE_KEY, openingURL);
					startActivity(i);
				}
				
				else if(position == 4){
					i=new Intent(getBaseContext(), MoviesActiviy.class);
					i.putExtra(SERIALIZABLE_KEY, upcomingURL);
					startActivity(i);
				}
				
				else if(position == 0){
					i=new Intent(getBaseContext(), MoviesActiviy.class);
					i.putExtra(SERIALIZABLE_KEY3, favorites);
					startActivity(i);
				}
								
			}
       });
        
        
    }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	MenuInflater inflater = getMenuInflater();
	inflater.inflate(R.layout.mainactionbar, menu);
	return true;
}

@Override
public boolean onOptionsItemSelected(MenuItem item) {
	Intent intent = new Intent(Intent.ACTION_MAIN);
	intent.addCategory(Intent.CATEGORY_HOME);
	intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	startActivity(intent);
	return true;
}


public class MainActivityAdapter extends ArrayAdapter<String>{
	Context context;
	String id,title,mpaa,year,runtime,ratings,critic,audience,criticsScore,audienceScore,relDate,posterURL; 
	ArrayList<String> mu=new ArrayList<String>();
	ImageView critics, auidenceView; 


	public MainActivityAdapter(Context context, ArrayList<String> ar) {
			super(context, R.layout.simple_list_item_1, ar);
			this.context = context;
			this.mu=ar;
		}
		 @Override
		 public View getView(int position, View convertView, ViewGroup parent) {
			 if (convertView == null) {
	 	 	 	 LayoutInflater inflater  =  (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	 	 	 	 convertView  =  inflater.inflate(R.layout.simple_list_item_1, parent, false);
	 	 	 }
				
				TextView title = (TextView) convertView.findViewById(R.id.option);
				title.setText(mu.get(position));
				 	
			return convertView;	 	 	
		}
	}

}