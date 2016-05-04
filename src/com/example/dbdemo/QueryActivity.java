package com.example.dbdemo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import com.example.zhou.dao.ShowView;
import com.example.zhy_handler_imageloader.R;
import com.zhou.bean.label;
import com.zhou.sql.dao.DatabaseUtil;

public class QueryActivity extends Activity {

	private ListView mList; //显示查询结果
	private DatabaseUtil mUtil;
	private List<label> list = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.upadel);
		//初始化
		initview();
	}

	private void initview() {
		//获取数据库
		mUtil = new DatabaseUtil(QueryActivity.this);
		mList = (ListView)findViewById(R.id.mlist3);  //输出
		Intent intent = getIntent();
		String labelname=intent.getStringExtra("labelname");//取出标签		
		list = mUtil.queryByname(labelname);
		List<Map<String, Object>> templist = new ArrayList<Map<String,Object>>();
		for(label person:list){
				Map<String,Object> map = new HashMap<String, Object>();
				map.put("id", person.getId());
				map.put("path", person.getPath());
				map.put("label", person.getLabel());
				templist.add(map);
				}
		//添加到ListView
		mList.setAdapter(new SimpleAdapter(QueryActivity.this, 
				templist, R.layout.item, 
				new String[]{"id","path","label"}, 
				new int[]{R.id.id_item,R.id.path_item,R.id.label_item}));
		mList.setOnItemClickListener(new myOnItemClickListener());
				
	}

	//监听ListView，进入修改和删除界面
	private class myOnItemClickListener implements OnItemClickListener{
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Map<String ,Object> map = (Map<String, Object>)parent.getItemAtPosition(position);
			String path = (String)map.get("path");
			Intent intent = new Intent();
			intent.putExtra("path_data", path);
			intent.setClass(QueryActivity.this, ShowView.class);
			QueryActivity.this.startActivity(intent);
		}
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
}
