package com.example.zhou.dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import com.example.dbdemo.DelAndUpdate;
import com.example.dbdemo.QueryActivity;
import com.example.zhy_handler_imageloader.R;
import com.zhou.bean.label;
import com.zhou.sql.dao.DatabaseUtil;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class ShowView extends Activity {
	
	private DatabaseUtil mDBUtil;//数据库操作对象
	private List<label> list;//查询结果
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.showview);
		Intent intent=getIntent();
		String path_data=intent.getStringExtra("path_data");//取出图片路径
		ImageView image1 = (ImageView) findViewById(R.id.showview);
	    Bitmap bitmap = getLoacalBitmap(path_data); //从本地取图片 路径
	    image1 .setImageBitmap(bitmap);	//设置Bitmap
	    image1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				finish();//关闭之前的activity,防止内存溢出
				Intent i = new Intent(ShowView.this,MainActivity.class);
				startActivity(i);
				//Intent intent = new Intent();

				//intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

				//startActivity(intent);
			}
		});
	}
	/**
	* 加载本地图片
	* @param url
	* @return
	*/
	public static Bitmap getLoacalBitmap(String url) {
	     try {
	          FileInputStream fis = new FileInputStream(url);
	          return BitmapFactory.decodeStream(fis);
	     } catch (FileNotFoundException e) {
	          e.printStackTrace();
	          return null;
	     }
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		//给本图片增加标签
		case R.id.input_menu:
			mDBUtil = new DatabaseUtil(ShowView.this);
			Intent intent3=getIntent();
			final String path_data3=intent3.getStringExtra("path_data");//取出图片路径
			AlertDialog.Builder builder = new AlertDialog.Builder(ShowView.this);   
			LayoutInflater factory = LayoutInflater.from(this);  
			final View textEntryView = factory.inflate(R.layout.addlabel_dialog, null);      
			builder.setTitle("请输入标签");  
			builder.setView(textEntryView);
			builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {  
			     public void onClick(DialogInterface dialog, int whichButton) {  
			      	 label person = new label();  
			       	 EditText temp = (EditText) textEntryView.findViewById(R.id.label_edt);  
			       	 String label = temp.getText().toString().trim();
			       	 if(TextUtils.isEmpty(label)){
						Toast.makeText(getApplicationContext(), "输入为空", Toast.LENGTH_SHORT).show();
						return ;
					}
			        person.setPath(path_data3);
					person.setLabel(label);
				if(mDBUtil.Insert(person)){
					Toast.makeText(getApplicationContext(), "添加标签成功", Toast.LENGTH_SHORT).show();		
					}else{
						Toast.makeText(getApplicationContext(), "添加标签失败", Toast.LENGTH_SHORT).show();
					}
			     }  
	    });  
		     builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {  
		         public void onClick(DialogInterface dialog, int whichButton) { 	  
		        	 dialog.dismiss(); 
		         }  
			     }); 
			   builder.create().show();
			break;
			
			//查看本图片的标签
		case R.id.see_menu:	
			mDBUtil = new DatabaseUtil(ShowView.this);
			Intent intent2=getIntent();
			final String path_data2=intent2.getStringExtra("path_data");//取出图片路径
			
			//查询结果
			list = mDBUtil.queryByPath(path_data2);
			//显示结果。默认为5个
			int i = 0;
			final String[] lebel_list= new String[5];
			lebel_list[0]="";
			lebel_list[1]="";
			lebel_list[2]="";
			lebel_list[3]="";
			lebel_list[4]="";
			if(list.size() != 0){
					for(label person:list){
						lebel_list[i]=person.getLabel();
						i++;
						if(i==5)
							break;
					}
					AlertDialog.Builder builder2 = new AlertDialog.Builder(this); 
					builder2.setTitle("标签列表");
					builder2.setItems(lebel_list,new OnClickListener() {
						@Override
						public void onClick(DialogInterface arg0, int arg1) {
							// TODO Auto-generated method stub
							int id = list.get(arg1).getId();
							Intent intent2 = new Intent();
							intent2.putExtra("id_s", id+"");
							intent2.putExtra("path_s", path_data2);
							intent2.putExtra("label_s", lebel_list[arg1]);
							intent2.setClass(ShowView.this, DelAndUpdate.class);
							ShowView.this.startActivity(intent2);
						}
					});
					builder2.show();
		}else{
			Toast.makeText(getApplicationContext(), "本图片没有标签", Toast.LENGTH_SHORT).show();
		}
			break;
			//跳转到查询页面
		case R.id.query_menu:
			/*Intent Toqueryctivity = new Intent(ShowView.this,QueryActivity.class);
			startActivity(Toqueryctivity);*/	
			mDBUtil = new DatabaseUtil(ShowView.this);
			AlertDialog.Builder builder2 = new AlertDialog.Builder(ShowView.this);   
			LayoutInflater factory2 = LayoutInflater.from(this);  
			final View textEntryView2 = factory2.inflate(R.layout.addlabel_dialog, null);      
			builder2.setTitle("请输入标签");  
			builder2.setView(textEntryView2);
			builder2.setPositiveButton("确定", new DialogInterface.OnClickListener() {  
			     public void onClick(DialogInterface dialog, int whichButton) {  
			       	 EditText temp = (EditText) textEntryView2.findViewById(R.id.label_edt);  
			       	 String label = temp.getText().toString().trim();
			       	 if(TextUtils.isEmpty(label)){
						Toast.makeText(getApplicationContext(), "输入为空", Toast.LENGTH_SHORT).show();
						return ;
					}
			       	list = mDBUtil.queryByname(label);
				if(list.size()==0){
						Toast.makeText(getApplicationContext(), "没有找到该标签", Toast.LENGTH_SHORT).show();
					}
				else{
					Intent Toqueryctivity = new Intent(ShowView.this,QueryActivity.class);
					Toqueryctivity.putExtra("labelname",label+"");
					startActivity(Toqueryctivity);
				}}  
	    });  
		     builder2.setNegativeButton("取消", new DialogInterface.OnClickListener() {  
		         public void onClick(DialogInterface dialog, int whichButton) { 	  
		        	 dialog.dismiss(); 
		         }  
			     }); 
			   builder2.create().show();
		default:
		}
		return true;
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

}
