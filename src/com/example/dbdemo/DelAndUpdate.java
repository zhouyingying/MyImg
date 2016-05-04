package com.example.dbdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.zhy_handler_imageloader.R;
import com.zhou.bean.label;
import com.zhou.sql.dao.DatabaseUtil;

public class DelAndUpdate extends Activity {
	
	private EditText label_data;
	private Button save_update;
	private Button delete;
	private int id;
	private String path;
	private String label;
	private DatabaseUtil mUtil;	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.del_update);
		//初始化
		initview();
	}
	
	private void initview(){
		label_data = (EditText)findViewById(R.id.edt_update);//标签内容对象
		save_update = (Button)findViewById(R.id.update_btn);//修改按钮
		delete = (Button)findViewById(R.id.delete_btn); //删除按钮
		
		//获得从ListView传过来的id
		Intent intent = getIntent();
		id = Integer.valueOf(intent.getStringExtra("id_s"));
		path = intent.getStringExtra("path_s");
		label = intent.getStringExtra("label_s");
		
		//获取数据库操作对象
		mUtil = new DatabaseUtil(DelAndUpdate.this);
	
		//把对象信息显示
		label_data.setText(label);
		
		//修改保存
		save_update.setOnClickListener(new myOnClick());
		//删除信息
		delete.setOnClickListener(new myOnClick());
	}
	
		//按钮监听
	private class myOnClick implements OnClickListener{
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.update_btn:  //修改并保存
				label person = new label();
				person.setId(id);
				person.setLabel(label_data.getText().toString());
				person.setPath(path);
				mUtil.Update(person, id);
				Toast.makeText(getApplicationContext(), "修改标签成功", Toast.LENGTH_SHORT).show();
				break;
			case R.id.delete_btn:  //删除
				mUtil.Delete(id);
				Toast.makeText(getApplicationContext(), "删除标签成功", Toast.LENGTH_SHORT).show();
				break;
			default:
				break;
			}
		}
	}
}
