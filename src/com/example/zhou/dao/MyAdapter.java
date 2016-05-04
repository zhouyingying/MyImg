package com.example.zhou.dao;

import java.util.List;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import com.example.zhy_handler_imageloader.R;
import com.zhou.sql.dao.MyDatabaseHelper;
import com.zhy.utils.ImageLoader;
import com.zhy.utils.ImageLoader.Type;

public class MyAdapter extends BaseAdapter
{
	private Context mContext;
	private List<String> mData;
	private String mDirPath;
	private LayoutInflater mInflater;
	private ImageLoader mImageLoader;

	public MyAdapter(Context context, List<String> mData, String dirPath)
	{
		this.mContext = context;
		this.mData = mData;
		this.mDirPath = dirPath;
		mInflater = LayoutInflater.from(mContext);
		mImageLoader = ImageLoader.getInstance(3 , Type.LIFO);
	}

	@Override
	public int getCount()
	{
		return mData.size();
	}

	@Override
	public Object getItem(int position)
	{
		return mData.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		return position;
	}

	@Override
	public View getView(int position, View convertView, final ViewGroup parent)
	{
		ViewHolder holder = null;
		final String pat=mDirPath + "/" + mData.get(position);//这是照片的路径
		if (convertView == null)
		{
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.grid_item, parent,
					false);
			holder.mImageView = (ImageView) convertView
					.findViewById(R.id.id_item_image);
			convertView.setTag(holder);
		} else
		{
			holder = (ViewHolder) convertView.getTag();
		}
		
		convertView.setOnClickListener(new View.OnClickListener() {  
			@Override
			public void onClick(View v) {
				String data = pat;
				Intent intent = new Intent();
				intent.putExtra("path_data", data);//取出照片路径
				intent.setClass(MyAdapter.this.mContext, ShowView.class);
				intent.setFlags(IGNORE_ITEM_VIEW_TYPE);
				MyAdapter.this.mContext.startActivity(intent);
			}
		});

		holder.mImageView
		.setImageResource(R.drawable.friends_sends_pictures_no);
		//使用Imageloader去加载图片
		mImageLoader.loadImage(mDirPath + "/" + mData.get(position),
				holder.mImageView);
		return convertView;
	}

	private final class ViewHolder
	{
		ImageView mImageView;
	}
	
}
