/*
 	Assignment #5
 	Author: Josh Leonard & Tyler Shay
 	Filename: Note.java
 
 */
package come.homework5.tj;

import java.io.Serializable;


public class Note implements Serializable{
	String id,title,mpaa,year,runtime,ratings,critic,audience,criticsScore,audienceScore,relDate,posterURL, movieLink; 
	boolean favorite;
	long _id;
	
	Note(){favorite = false;};
	
	Note(Movies m){
		 this.id=m.id;
		 this.title=m.title;
		 this.mpaa=m.mpaa_rating;
		 this.year=m.year;
		 this.runtime=m.runtime;
		 this.critic=m.r.critics;
		 this.audience=m.r.audience;
		 this.criticsScore=m.r.criticsScore;
		 this.audienceScore=m.r.audienceScore;
		 this.relDate=m.rd.theater;
		 this.posterURL=m.pos.url;
		 this.movieLink = m.movieLink.movieLink;
		 favorite = false;
		 
	}
	public boolean isFavorite() {
		return favorite;
	}
	public void setFavorite(boolean favorite) {
		this.favorite = favorite;
	}
	public String getMovieLink() {
		return movieLink;
	}
	public void setMovieLink(String movieLink) {
		this.movieLink = movieLink;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getMpaa() {
		return mpaa;
	}
	public void setMpaa(String mpaa) {
		this.mpaa = mpaa;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getRuntime() {
		return runtime;
	}
	public void setRuntime(String runtime) {
		this.runtime = runtime;
	}
	public String getRatings() {
		return ratings;
	}
	public void setRatings(String ratings) {
		this.ratings = ratings;
	}
	public String getCritic() {
		return critic;
	}
	public void setCritic(String critic) {
		this.critic = critic;
	}
	public String getAudience() {
		return audience;
	}
	public void setAudience(String audience) {
		this.audience = audience;
	}
	public String getCriticsScore() {
		return criticsScore;
	}
	public void setCriticsScore(String criticsScore) {
		this.criticsScore = criticsScore;
	}
	public String getAudienceScore() {
		return audienceScore;
	}
	public void setAudienceScore(String audienceScore) {
		this.audienceScore = audienceScore;
	}
	public String getRelDate() {
		return relDate;
	}
	public void setRelDate(String relDate) {
		this.relDate = relDate;
	}
	public String getPosterURL() {
		return posterURL;
	}
	public void setPosterURL(String posterURL) {
		this.posterURL = posterURL;
	}

	@Override
	public String toString() {
		return "Note [id=" + id + ", title=" + title + ", mpaa=" + mpaa
				+ ", year=" + year + ", runtime=" + runtime + ", ratings="
				+ ratings + ", critic=" + critic + ", audience=" + audience
				+ ", criticsScore=" + criticsScore + ", audienceScore="
				+ audienceScore + ", relDate=" + relDate + ", posterURL="
				+ posterURL + ", movieLink=" + movieLink + ", favorite="
				+ favorite + "]";
	}

	public long get_id() {
		return _id;
	}

	public void set_id(long _id) {
		this._id = _id;
	}

	
	
	
	
}
