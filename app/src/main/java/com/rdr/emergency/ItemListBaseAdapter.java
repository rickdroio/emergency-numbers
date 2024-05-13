package com.rdr.emergency;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;


public class ItemListBaseAdapter extends BaseAdapter {
	private static ArrayList<Numeros> itemDetailsrrayList;
	
	private LayoutInflater l_Inflater;

	public ItemListBaseAdapter(Context context, ArrayList<Numeros> results) {
		itemDetailsrrayList = results;
		l_Inflater = LayoutInflater.from(context);
	}

	public int getCount() {
		return itemDetailsrrayList.size();
	}

	public Object getItem(int position) {
		return itemDetailsrrayList.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = l_Inflater.inflate(R.layout.item_details_view, null);
			holder = new ViewHolder();
			holder.txt_itemName = (TextView) convertView.findViewById(R.id.name);
			holder.txt_desc = (TextView) convertView.findViewById(R.id.descricao);
			holder.itemImage = (ImageView) convertView.findViewById(R.id.photo);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.txt_itemName.setText(itemDetailsrrayList.get(position).getTelefone() + " - " + itemDetailsrrayList.get(position).getNome());
		holder.txt_desc.setText(itemDetailsrrayList.get(position).getDescricao());
		//holder.itemImage.setImageResource(R.drawable.telefone);

		return convertView;
	}

	static class ViewHolder {
		TextView txt_itemName;
		TextView txt_desc;
		TextView txt_itemDescription;
		TextView txt_itemPrice;
		ImageView itemImage;
	}
}
