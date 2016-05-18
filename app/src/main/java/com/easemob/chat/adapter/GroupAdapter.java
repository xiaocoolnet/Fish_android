package com.easemob.chat.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.easemob.chat.EMGroup;
import cn.xiaocool.fish.R;

import java.util.List;

public class GroupAdapter extends ArrayAdapter<EMGroup> {

	private LayoutInflater inflater;
	private String newGroup;
	private String addPublicGroup;

	public GroupAdapter(Context context, int res, List<EMGroup> groups) {
		super(context, res, groups);
		this.inflater = LayoutInflater.from(context);
		newGroup = context.getResources().getString(R.string.The_new_group_chat);
		addPublicGroup = context.getResources().getString(R.string.add_public_group_chat);
	}

	@Override
	public int getViewTypeCount() {
		return 4;
	}

	@Override
	public int getItemViewType(int position) {
		if (position == 0) {
			return 0;
		} else if (position == 1) {
			return 1;
		} else if (position == 2) {
			return 2;
		} else {
			return 3;
		}
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (getItemViewType(position) == 0) {
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.ease_search_bar_with_padding, null);
			}
			final EditText query = (EditText) convertView.findViewById(R.id.query);
			final ImageButton clearSearch = (ImageButton) convertView.findViewById(R.id.search_clear);
			query.addTextChangedListener(new TextWatcher() {
				public void onTextChanged(CharSequence s, int start, int before, int count) {
					getFilter().filter(s);
					if (s.length() > 0) {
						clearSearch.setVisibility(View.VISIBLE);
					} else {
						clearSearch.setVisibility(View.INVISIBLE);
					}
				}

				public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				}

				public void afterTextChanged(Editable s) {
				}
			});
			clearSearch.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					query.getText().clear();
				}
			});
		} else if (getItemViewType(position) == 1) {
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.ease_row_add_group, null);
			}
			((ImageView) convertView.findViewById(R.id.avatar)).setImageResource(R.drawable.create_group);
			((TextView) convertView.findViewById(R.id.name)).setText(newGroup);
		} else if (getItemViewType(position) == 2) {
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.ease_row_add_group, null);
			}
			((ImageView) convertView.findViewById(R.id.avatar)).setImageResource(R.drawable.add_public_group);
			((TextView) convertView.findViewById(R.id.name)).setText(addPublicGroup);
			((TextView) convertView.findViewById(R.id.header)).setVisibility(View.VISIBLE);

		} else {
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.ease_row_group, null);
			}
			((TextView) convertView.findViewById(R.id.name)).setText(getItem(position - 3).getGroupName());

		}

		return convertView;
	}

	@Override
	public int getCount() {
		return super.getCount() + 3;
	}

}