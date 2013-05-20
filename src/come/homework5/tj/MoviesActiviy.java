/*
 	Assignment #5
 	Author: Josh Leonard & Tyler Shay
 	Filename: MoviesActvity.java
 
 */
package come.homework5.tj;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.ClipData.Item;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MoviesActiviy extends Activity {
	final static String SERIALIZABLE_KEY2 = "Serializable2";
	boolean favoriteBool = false;
	String urlString;
	protected Object mActionMode;
	parseJson pj=new parseJson();
	Context context;
	Movies mov;
	String temp;
	Intent i;
	Boolean api;
	ProgressDialog progressBar;
	public static DataManager dm;
	ArrayList<Note> NoteListTemp,NoteList;
	Note mut;
	int count=0;

	@Override
	protected void onResume(){
		super.onResume();
		NoteList=new ArrayList<Note>();
		
		///favriteBool = true if use chose the "My Favorite Movies" option from the MainActivity
		if(favoriteBool){

			
			NoteListTemp = new ArrayList<Note>();
			//NoteListTemp will have a list of the id's of the favorite movies
			NoteListTemp = MoviesActiviy.dm.getAllNotes();
			//if the NoteListTemp == 0 I do not want to sent it through the aysnctask and try to parseJson it
			if(NoteListTemp.size()!=0)
				new GetMovies(NoteListTemp).execute("");
			else
				constructListView();
		}
		else{
			//this will be called if favoriteBool = false, aka clicked an option that wasn't "My Favorite Movie"
			new GetMovies().execute("");
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.layout.mainactionbar, menu);
		MenuItem item1 = menu.findItem(R.id.dropExit);
		item1.setTitle("Back");
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		finish();
		return true;
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.movies);
		context=this;

		dm = new DataManager(this);
		NoteList=new ArrayList<Note>();
		
		Bundle extras = getIntent().getExtras();
		if(extras.containsKey(MainActivity.SERIALIZABLE_KEY)){
			urlString = (String) extras.getSerializable(MainActivity.SERIALIZABLE_KEY);
		}

		if(extras.containsKey(MainActivity.SERIALIZABLE_KEY3)){
			favoriteBool = true;
		}
		progressBar = new ProgressDialog(this);
	}

	public class GetMovies extends AsyncTask<String, String, String>{
		ArrayList<Note> NoteListTemp = null;
		
		//Other options than "My Favorite Movies" will use this option
		public GetMovies(){}

		//The "My Favorite Movies" Option will use this constructor"
		public GetMovies(ArrayList<Note> n){
			NoteListTemp = n;
		}


		@Override
		protected String doInBackground(String... params) {
			if(NoteListTemp == null){ //since the Other options use the empty constructor this will be true
				api = true; //will set what part of the parseJSON() method will use
				try {
					parseJSON(pj.getJSONFromUrl(urlString));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			else{ //This will be called if "My Favorite Movies" will use this option
				api = false; //will set what part of the parseJSON() method will use
				for(int i = 0; i < NoteListTemp.size(); i++){
					//Construct the "Movie Info" API all it needs is the movie id 
					urlString="http://api.rottentomatoes.com/api/public/v1.0/movies/"+NoteListTemp.get(i).getId()+".json?apikey=mf69tpgbd56r9fjvg4ktrvud";
					try {
						parseJSON(pj.getJSONFromUrl(urlString));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

			}

			publishProgress("Finished");	
			return null;

		}
		@Override
		protected void onProgressUpdate(String... values) {
			if (values[0].equals("Finished"))
				progressBar.dismiss();
		}

		@Override
		protected void onPreExecute() {
			progressBar.setCancelable(false);
			progressBar.setMessage("Loading Movies...");
			progressBar.show();

			super.onPreExecute();
		}

		@Override
		protected void onPostExecute(String result) {
			constructListView();
		}	
	}

	public void parseJSON(JSONObject j) throws IOException, JSONException{

		if(api){//What the Other options uses
			JSONArray movies;
			movies= j.getJSONArray("movies");
			for(int i=0; i<movies.length();i++){
				JSONObject o=movies.getJSONObject(i);
				mov=new Movies(o);
				Note note = new Note(mov);
				NoteList.add(note);
				count++;
			}
		}
		else{ //What "My Favorite Movies" uses
			Log.v("movies",j.toString());
			mov=new Movies(j);
			Note note = new Note(mov);
			NoteList.add(note);
			count++;
		}

	}	

	public void constructListView(){

		ListView myListView = (ListView) findViewById(R.id.myMovielist); 
		MovieAdapter adapter=new MovieAdapter(MoviesActiviy.this, NoteList);
		myListView.setAdapter(adapter);

		myListView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				mut = NoteList.get(position);
				i=new Intent(getBaseContext(), MovieActivity.class);
				i.putExtra(SERIALIZABLE_KEY2, mut);
				startActivity(i);
			}			

		});
		//The Long Click Listener should only be enable when the "My Favorite Movies" option was choosen
		if(favoriteBool){
			myListView.setOnItemLongClickListener(new OnItemLongClickListener() {
				public boolean onItemLongClick(AdapterView<?> parent, View view,
						int position, long arg3) {

					if (mActionMode != null) {
						return false;
					}

					mActionMode = MoviesActiviy.this.startActionMode(mActionModeCallback);
					mut=NoteList.get(position);
					return true;
				}
			});
		}
	}
	@Override
	protected void onDestroy() {
		Log.v("onDestroy", "true");
		dm.close();
		super.onDestroy();
	}
	
	private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

		// Called when the action mode is created; startActionMode() was called
		public boolean onCreateActionMode(ActionMode mode, Menu menu) {
			// Inflate a menu resource providing context menu items
			MenuInflater inflater = mode.getMenuInflater();
			inflater.inflate(R.layout.contextmenu, menu);
			return true;
		}

		// Called each time the action mode is shown. Always called after onCreateActionMode, but
		// may be called multiple times if the mode is invalidated.
		public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
			return false; // Return false if nothing is done
		}

		// Called when the user selects a contextual menu item
		public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
			switch (item.getItemId()) {
			case R.id.file:
				mut.setFavorite(false);
				MoviesActiviy.dm.deleteNote(mut);
				mode.finish();
				return true;
			default:
				return false;
			}
		}

		// Called when the user exits the action mode
		public void onDestroyActionMode(ActionMode mode) {
			Log.v("ONDESTROY",mode.toString());
			mActionMode=null;
			onResume();
		}
	};
}

