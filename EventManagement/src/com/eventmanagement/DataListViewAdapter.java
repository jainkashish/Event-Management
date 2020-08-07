package com.eventmanagement;


import java.util.ArrayList;

import com.eventmanagement.entity.DataEntity;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

public class DataListViewAdapter extends BaseAdapter 
{
	private final Activity context;
	public  ArrayList<DataEntity> content;
	public boolean isDelete=false;

	static class ViewHolder {
		public TextView txtName, txtDepatment, txtSubject;
	}

	public DataListViewAdapter(Activity context, ArrayList<DataEntity> content) 
	{
		this.context = context;
		this.content = content;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return content.size();
	}


	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return content.get(arg0);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return content.indexOf(getItem(position));
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) 
	{
		View rowView=null;
		DataEntity object = content.get(position);

		LayoutInflater inflater = context.getLayoutInflater();
		rowView = inflater.inflate(R.layout.data_row_container, null);
		ImageView btnDelete=(ImageView)rowView.findViewById(R.id.delete);
		TextView txtName = (TextView) rowView.findViewById(R.id.txtName);
		TextView txtContact = (TextView) rowView.findViewById(R.id.txtContact);
		txtName.setText(object.getName());
		txtContact.setText(object.getContact());

		btnDelete.setVisibility(View.GONE);
		btnDelete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
//				new DBHelper(context).deleteCategory(content.get(position));
//				content.remove(content.get(position));
//				notifyDataSetChanged();
			}
		});
		return rowView;
	}


}
