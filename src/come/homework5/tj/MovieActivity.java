/*
 	Assignment #5
 	Author: Josh Leonard & Tyler Shay
 	Filename: MovieActivity.java
 
 */
package come.homework5.tj;

import java.io.InputStream;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MovieActivity extends Activity{
	ImageView back,interwebs,favsies,poster,audienceIV,criticIV,auidenceRating, criticsRating;
	TextView title,relDate,runTime,mpaa,audRate,critRate;
	Note mut;
	Note note;
	
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.layout.mainactionbar, menu);
		MenuItem item1 = (MenuItem) menu.findItem(R.id.dropExit);
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
		setContentView(R.layout.movie_activity);
		back=(ImageView)findViewById(R.id.imageView1);
		interwebs=(ImageView)findViewById(R.id.imageView2);
		favsies=(ImageView)findViewById(R.id.imageView4);
		poster=(ImageView)findViewById(R.id.poster);
		auidenceRating=(ImageView)findViewById(R.id.audienceRating);
		criticsRating =(ImageView)findViewById(R.id.criticsRating);

		title=(TextView)findViewById(R.id.movieTitle);
		relDate=(TextView)findViewById(R.id.releaseDate);
		runTime=(TextView)findViewById(R.id.runtime);
		mpaa=(TextView)findViewById(R.id.mpaa);
		critRate=(TextView)findViewById(R.id.criticsPercent);
		audRate=(TextView)findViewById(R.id.audiencePerecent);
		Bundle extras = getIntent().getExtras();

		if(extras.containsKey(MoviesActiviy.SERIALIZABLE_KEY2)){
			mut = (Note) extras.getSerializable(MoviesActiviy.SERIALIZABLE_KEY2);
		}

		note = new Note();
		Log.v("note created", "true");
		note = MoviesActiviy.dm.getNote(mut.getId());

		setPage();
		clicks();
	}

	private void clicks(){

		back.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				finish();
			}
		});

		interwebs.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				String url = mut.getMovieLink();
				Intent i = new Intent(Intent.ACTION_VIEW);
				i.setData(Uri.parse(url));
				startActivity(i);
			}
		});

		favsies.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				if(note.isFavorite()){
					note.setFavorite(false);
					MoviesActiviy.dm.deleteNote(note);	
					favsies.setImageResource(R.drawable.not_favorite);
				}
				else{
					note.setFavorite(true);
					note.setId(mut.getId());
					long f = MoviesActiviy.dm.saveNote(note);
					favsies.setImageResource(R.drawable.light_favorite);
				}

			}});
	}

	protected void setPage(){
		Log.v("mut",mut.getPosterURL());
		new ImageDownloader().execute(mut.getPosterURL());
		title.setText(mut.getTitle());
		audRate.setText(mut.getAudienceScore()+"%");
		critRate.setText(mut.getCriticsScore()+"%");
		relDate.setText(mut.getRelDate());
		auidenceRating.setVisibility(View.INVISIBLE);
		criticsRating.setVisibility(View.INVISIBLE);

		if(mut.getAudience().equals("Upright")){
			auidenceRating.setImageResource(R.drawable.upright);
		}
		else if(mut.getAudience().equals("Spilled")){
			auidenceRating.setImageResource(R.drawable.spilled);
		}
		else
			auidenceRating.setImageResource(R.drawable.notranked);

		if(mut.getCritic().equals("Certified Fresh")){
			criticsRating.setImageResource(R.drawable.certified_fresh);
		}
		else if(mut.getCritic().equals("Fresh")){
			criticsRating.setImageResource(R.drawable.fresh);
		}
		else if(mut.getCritic().equals("Rotten")){
			criticsRating.setImageResource(R.drawable.rotten);
		}
		else
			criticsRating.setImageResource(R.drawable.notranked);
		
		auidenceRating.setVisibility(View.VISIBLE);
		criticsRating.setVisibility(View.VISIBLE);

		if(mut.getRuntime().isEmpty())
			runTime.setText("N/A");
		else{
			int rt=Integer.parseInt(mut.getRuntime());
			int hour = rt / 60;
			int min = rt % 60;
			runTime.setText("H: "+hour+" M: "+min);
		}

		if(mut.getMovieLink().isEmpty())
			mpaa.setText("MPPA: Unkown");
		else
			mpaa.setText("MPAA: "+mut.getMpaa());		

		if(note.isFavorite())
		{
			Log.v("note.isFavorite() in set page = ", "true");
			favsies.setImageResource(R.drawable.light_favorite);}
		else{
			Log.v("note.isFavorite() in set page = ", "false");
			favsies.setImageResource(R.drawable.not_favorite);
		}

	}

	private class ImageDownloader extends AsyncTask<String, Void, Bitmap>{

		@Override
		protected Bitmap doInBackground(String... params) {

			return downloadBitmap(params[0]);
		}

		@Override
		protected void onPreExecute(){

		}

		@Override
		protected void onPostExecute(Bitmap result){
			
			if(result.getByteCount()!=0)
				poster.setImageBitmap(result);
			else
				poster.setImageResource(R.drawable.poster_not_found);
			poster.setVisibility(View.VISIBLE);
			Log.v("PE","PE");
		}

		private Bitmap downloadBitmap(String url){
			Log.v("downloading..","dl..");
			final DefaultHttpClient client = new DefaultHttpClient();
			final HttpGet getRequest = new HttpGet(url);

			try {
				HttpResponse response = client.execute(getRequest);
				final int statusCode = response.getStatusLine().getStatusCode();
				if (statusCode != HttpStatus.SC_OK) { 
					Log.w("ImageDownloader", "Error " + statusCode + " while retrieving bitmap from " + url); 
					return null;
				}

				final HttpEntity entity = response.getEntity();
				if (entity != null) {
					InputStream inputStream = null;
					try {
						inputStream = entity.getContent(); 
						final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
						//Log.v("fin..","dl..");
						return bitmap;
					} finally {
						if (inputStream != null) {
							inputStream.close();  
						}
						entity.consumeContent();
					}
				}
			} catch (Exception e) {
			
				getRequest.abort();
				Log.e("ImageDownloader", "Error while retrieving bitmap from " + url+ e.toString());
			} finally {
				if (client != null) {

				}
			}
			return null;
		}

	}
	
}