package com.itconnect.inc.adapters;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.TimeZone;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ytc.R;
import com.example.ytc.clazz.Video;

public class YouTubeAdapter extends BaseAdapter {

	Context mContext;
	Intent intent;
	LayoutInflater mInflater;
	ArrayList<Video> videolist;

	public YouTubeAdapter(Context context, ArrayList<Video> v) {
		mContext = context;
		videolist = v;
		mInflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	public void removeDuplicated(){
		HashSet hs = new HashSet();
		hs.addAll(videolist);
		videolist.clear();
		videolist.addAll(hs);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return videolist.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	
	@Override
	public View getView(final int position, View arg1, ViewGroup arg2) {
		ViewHolder vh;

		if (arg1 == null) {
			arg1 = mInflater.inflate(R.layout.video, null);
			vh = new ViewHolder();
			vh.tv = (TextView) arg1.findViewById(R.id.tvv);
			vh.description= (TextView) arg1.findViewById(R.id.show_description);
			vh.iv = (ImageView) arg1.findViewById(R.id.ivv);
			vh.duration = (TextView) arg1.findViewById(R.id.duration);
			arg1.setTag(vh);
		} else {
			vh = (ViewHolder) arg1.getTag();
		}
		vh.tv.setText(videolist.get(position).getTitel());
		vh.description.setText(videolist.get(position).getTitel());
		vh.selectedvideo=videolist.get(position);
		vh.duration.setText(ConvertSecondToHHMMString(videolist.get(position)
				.getDuration()));
		vh.iv.setImageBitmap(videolist.get(position).getImage());
		return arg1;
	}

	private String ConvertSecondToHHMMString(int secondtTime) {
		TimeZone tz = TimeZone.getTimeZone("UTC");
		SimpleDateFormat df = new SimpleDateFormat("mm:ss");
		df.setTimeZone(tz);
		String time = df.format(new Date(secondtTime * 1000L));

		return time;

	}

	static class ViewHolder {
		TextView tv;
		ImageView iv;
		TextView description;
		TextView duration;
		Video selectedvideo;
	}
}