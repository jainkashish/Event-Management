package com.eventmanagement;


import java.util.ArrayList;

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

public class ListViewAdapter2 extends BaseAdapter implements Filterable
{
	private ArrayList<String> mStringFilterList;
	private final Activity context;
	private  ArrayList<String> content;
	public boolean isDelete=false;
	private ValueFilter valueFilter;
	
	static class ViewHolder {
		public TextView txtName, txtDepatment, txtSubject;
	}

	public ListViewAdapter2(Activity context, ArrayList<String> content) 
	{
		this.context = context;
		this.content = content;
		this.mStringFilterList=content;
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
		String object = content.get(position);

		LayoutInflater inflater = context.getLayoutInflater();
		rowView = inflater.inflate(R.layout.row_container, null);
		ImageView btnDelete=(ImageView)rowView.findViewById(R.id.delete);
		TextView txtName = (TextView) rowView.findViewById(R.id.txtName);
		txtName.setText(object);

		btnDelete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				new DBHelper(context).deleteCategory(content.get(position));
				content.remove(content.get(position));
				notifyDataSetChanged();
			}
		});
		return rowView;
	}
	
	@Override
	public Filter getFilter() {
	    if(valueFilter==null) {

	        valueFilter=new ValueFilter();
	    }

	    return valueFilter;
	}
	private class ValueFilter extends Filter {

	    //Invoked in a worker thread to filter the data according to the constraint.
	    @Override
	    protected FilterResults performFiltering(CharSequence constraint) {
	        FilterResults results=new FilterResults();
	        if(constraint!=null && constraint.length()>0){
	            ArrayList<String> filterList=new ArrayList<String>();
	            for(int i=0;i<mStringFilterList.size();i++){
	                if((mStringFilterList.get(i)).contains(constraint.toString())) {
	                    filterList.add(mStringFilterList.get(i));
	                }
	            }
	            results.count=filterList.size();
	            results.values=filterList;
	        }else{
	            results.count=mStringFilterList.size();
	            results.values=mStringFilterList;
	        }
	        return results;
	    }


	    //Invoked in the UI thread to publish the filtering results in the user interface.
	    @SuppressWarnings("unchecked")
	    @Override
	    protected void publishResults(CharSequence constraint,
	            FilterResults results) {
	        content=(ArrayList<String>) results.values;
	        notifyDataSetChanged();
	    }
	}
}
