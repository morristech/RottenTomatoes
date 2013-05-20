/*
 	Assignment #5
 	Author: Josh Leonard & Tyler Shay
 	Filename: Movies.java
 
 */
package come.homework5.tj;

import java.io.Serializable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class Movies {
	String id,title, mpaa_rating, year, runtime;
	final public static String unkown = "Unkown";
	JSONObject ratings,relDate,pURL, URLlink;
	URLlink movieLink;
	Ratings r;
	ReleaseDate rd;
	Posters pos;
	
	public Movies(JSONObject p) {
		try {
			id = p.getString("id");
			Log.v(id,id);
			title = p.getString("title");
			if(p.getString("year")!=null)
				year=p.getString("year");
			else
				year = new String(unkown);
			Log.v("year",year);
			if(p.getString("mpaa_rating")!=null)
				mpaa_rating = p.getString("mpaa_rating");
			else
				mpaa_rating = new String(unkown);
			Log.v("mpaa",mpaa_rating);
			
			if(p.getString("runtime")!=null)
				runtime=p.getString("runtime");
			else
				runtime = new String(unkown);
			Log.v("runtime",runtime);
			URLlink = p.getJSONObject("links");
			ratings=p.getJSONObject("ratings");
			movieLink = new URLlink(URLlink);
			r=new Ratings(ratings);
			//Log.v("r",r.)
			relDate=p.getJSONObject("release_dates");
			rd=new ReleaseDate(relDate);
			pURL=p.getJSONObject("posters");
			pos=new Posters(pURL);
		} catch (JSONException e) {
			Log.v("exceptionINMoviews",e.toString());
		}
	}
	@Override
	public String toString() {
		return "Movies [id=" + id + ", title=" + title + ", mpaa_rating="
				+ mpaa_rating + ", year=" + year + ", runtime=" + runtime
				+ ", r=" + r.toString() + rd.toString()+pos.toString() + movieLink.toString() + "]";
	}



}

class Ratings {
	String critics,audience, criticsScore,audienceScore;
	public Ratings(JSONObject p) throws JSONException{
	if(!(p.optString ("critics_rating").isEmpty()))
		critics=p.getString("critics_rating");
		//Log.v("critics = ", critics);
	else
		critics = "Unknown";//new String(Movies.unkown);
	if(!(p.getString("critics_score").equals("-1")))
		criticsScore=p.getString("critics_score");
	else
		criticsScore = new String(Movies.unkown);
	if(!(p.optString ("audience_rating").isEmpty()))
		audience=p.getString("audience_rating");
	else
		audience = new String(Movies.unkown);
	if(!(p.getString("audience_score").equals("-1")))
		audienceScore=p.getString("audience_score");
	else
		audienceScore = new String(Movies.unkown);
	}
	
	@Override
	public String toString() {
		return "Ratings [critics=" + critics + ", audience=" + audience
				+ ", criticsScore=" + criticsScore + ", audienceScore="
				+ audienceScore + "]";
	}
}

class ReleaseDate{
	String theater,dvd;
	
	public ReleaseDate(JSONObject p) throws JSONException{ //may need to add catch
		if(!(p.optString ("theater").isEmpty()))
			theater=p.getString("theater");
		else
			theater = new String(Movies.unkown);
	}
	
	public String toString(){
		return "Release Date [theater=" + theater;
	}
	
}

class Posters {
	String url;
	String originalUrl;
	
	public Posters(JSONObject p) throws JSONException{
		if(p.getString("detailed")!=null)
			url=p.getString("detailed");
		else
			url=new String(Movies.unkown);
	}
	
	public String toString(){
		return "URL [url=" + url;
		
	}
}

class URLlink {
	String movieLink;
	
	public URLlink(JSONObject p) throws JSONException{
		if(p.getString("alternate")!=null)
			movieLink=p.getString("alternate");
		else
			movieLink=new String(Movies.unkown);
	}
	
	public String toString(){
		return "URL [url=" + movieLink;
		
	}
}

