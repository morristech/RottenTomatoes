/*
 	Assignment #5
 	Author: Josh Leonard & Tyler Shay
 	Filename: MovieAdapter.java
 
 */
package come.homework5.tj;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MovieAdapter extends ArrayAdapter<Note>{
	Context context;
	String id,title,mpaa,year,runtime,ratings,critic,audience,criticsScore,audienceScore,relDate,posterURL; 
	ArrayList<Note> mu = new ArrayList<Note>();
	ImageView critics, auidenceView; 


	public MovieAdapter(Context context, ArrayList<Note> ar) {
		super(context, R.layout.simple_list_item_2, ar);
		this.context = context;
		this.mu=ar;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater inflater  =  (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView  =  inflater.inflate(R.layout.simple_list_item_2, parent, false);
		}
		TextView title = (TextView) convertView.findViewById(R.id.MovieTitle);
		title.setText(mu.get(position).title);
		TextView year = (TextView) convertView.findViewById(R.id.year); 		 	
		year.setText(mu.get(position).year);
		TextView mpaa = (TextView) convertView.findViewById(R.id.mpaa); 
		mpaa.setText(mu.get(position).mpaa);

		critics = (ImageView) convertView.findViewById(R.id.criticsImage);
		auidenceView = (ImageView) convertView.findViewById(R.id.audienceImage);

		critics.setVisibility(View.VISIBLE);
		auidenceView.setVisibility(View.VISIBLE);

		if(mu.get(position).critic.equals("Certified Fresh"))
			critics.setImageResource(R.drawable.certified_fresh); 
		else if (mu.get(position).critic.equals("Fresh"))
			critics.setImageResource(R.drawable.fresh); 
		else if (mu.get(position).critic.equals("Rotten"))
			critics.setImageResource(R.drawable.rotten); 
		else
			critics.setImageResource(R.drawable.notranked);

		if(mu.get(position).audience.equals("Upright"))
			auidenceView.setImageResource(R.drawable.upright);
		else if (mu.get(position).audience.equals("Spilled"))
			auidenceView.setImageResource(R.drawable.spilled);
		else
			auidenceView.setImageResource(R.drawable.notranked);

		return convertView;	 	 	
	}
}