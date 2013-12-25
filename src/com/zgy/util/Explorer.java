package com.zgy.util;

/**
 * 文件浏览器
 */
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.zgy.mainutil.R;

public class Explorer extends Activity {
	private ArrayList<String> allselectedPaths = null;
	private ArrayList<String> allselectedfiles = null;
	private String rootPath = Environment.getExternalStorageDirectory().toString();// 将根目录设置为sd卡
	private String parentPath = Environment.getExternalStorageDirectory().toString();
	private String nowPath = Environment.getExternalStorageDirectory().toString();

	private Button btn_return = null;// 返回到根目录
	private Button btn_back = null;// 返回上一级目录
	private Button btn_finish = null;// 完成，并返回主页面
	private ListView listview_file = null;
	private TextView text_showNowPath = null;
	private TextView text_hintTop = null;

	@Override
	protected void onCreate(Bundle save) {
		super.onCreate(save);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.file_explorer);

		btn_return = (Button) findViewById(R.id.btn_return);
		btn_back = (Button) findViewById(R.id.btn_back);
		btn_finish = (Button) findViewById(R.id.btn_finish);
		text_showNowPath = (TextView) findViewById(R.id.text_nowpath);
		text_hintTop = (TextView) findViewById(R.id.textview_top);
		listview_file = (ListView) findViewById(R.id.file_list);

		if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
			getFileDir(nowPath);
		} else {
			Toast.makeText(Explorer.this, "存储卡未挂载", Toast.LENGTH_LONG).show();
			finish();
		}

		listview_file.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				/* 单击此条目，判断文件还是目录并进入 */
				if (arg2 < allselectedPaths.size()) {
					// 是目录
					String prePath = "";
					// Log.e("getFileDir", nowPath + "/" + allselectedPaths.get(arg2));
					try {
						prePath = nowPath;
						getFileDir(nowPath + "/" + allselectedPaths.get(arg2));
					} catch (Exception e) {
						nowPath = prePath;
						getFileDir(nowPath);
						Toast.makeText(Explorer.this, "无法进入此目录", Toast.LENGTH_LONG).show();
					}

				} else {
					// 否
					final String ttfname = nowPath + "/" + allselectedfiles.get(arg2 - allselectedPaths.size());
					new AlertDialog.Builder(Explorer.this).setTitle("提示").setMessage("确定使用此字体作为系统字体吗？").setNegativeButton("取消", new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {

						}
					}).setPositiveButton("确定", new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							Intent i = new Intent();
							i.putExtra("path", ttfname);
							setResult(RESULT_OK, i);
							finish();
						}
					}).show();
				}
			}
		});

		btn_return.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				getFileDir(rootPath);
			}
		});
		btn_back.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				getFileDir(parentPath);
			}
		});
		btn_finish.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});

	}

	/**
	 * 进入目录
	 * 
	 * @param filePath
	 */
	private void getFileDir(String filePath) {
		setProgressBarIndeterminate(true);
		/* 设置目前所在路径 */
		nowPath = filePath;
		text_showNowPath.setText(nowPath);
		File f = new File(filePath);
		File[] files = f.listFiles();

		if (!filePath.equals(rootPath)) {
			btn_back.setEnabled(true);
			btn_return.setEnabled(true);
			parentPath = f.getParent();
		} else {
			btn_return.setEnabled(false);
			btn_back.setEnabled(false);
		}
		if (files == null || files.length == 0) {
			text_hintTop.setText("空目录");
			SimpleAdapter listItemAdapter = new SimpleAdapter(this, new ArrayList<HashMap<String, Object>>(), R.layout.file_row, new String[] { "FileTitle", "FileIcon" }, new int[] { R.id.file_text,
					R.id.file_icon });
			listview_file.setAdapter(listItemAdapter);
		} else {
			text_hintTop.setText("");
			allselectedfiles = new ArrayList<String>();
			allselectedPaths = new ArrayList<String>();
			ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();

			/* 将所有文件添加ArrayList中 */
			for (int i = 0; i < files.length; i++) {
				File file = files[i];
				// if (alreadyExcite(file.getPath())) {
				// /* 如果目录已经在要删除的列表中，则不必显示了。 */
				// } else {
				if (file.isDirectory()) {
					allselectedPaths.add(file.getName());
				} else if (file.getName().endsWith(".ttf")) {
					allselectedfiles.add(file.getName());
				}
				// }
			}

			/* 排序 + 显示 */
			String[] aa = new String[allselectedPaths.size()];
			allselectedPaths.toArray(aa);
			Arrays.sort(aa);
			allselectedPaths = new ArrayList<String>();
			for (String ee : aa) {
				allselectedPaths.add(ee);
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("FileTitle", ee);
				/* 判断是否为空目录， 并添加相应图标 */
				File a = new File(nowPath + "/" + ee);
				File[] childfiles = a.listFiles();
				if (childfiles == null || childfiles.length == 0) {
					map.put("FileIcon", R.drawable.folder_empty);
				} else {
					map.put("FileIcon", R.drawable.folder_full);
				}

				listItem.add(map);
			}
			String[] mm = new String[allselectedfiles.size()];
			allselectedfiles.toArray(mm);
			Arrays.sort(mm);
			allselectedfiles = new ArrayList<String>();
			for (String ff : mm) {
				allselectedfiles.add(ff);
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("FileTitle", ff);
				map.put("FileIcon", R.drawable.file);
				listItem.add(map);
			}
			/* 生成适配器的Item和动态数组对应的元素 */
			SimpleAdapter listItemAdapter = new SimpleAdapter(this, listItem, R.layout.file_row, new String[] { "FileTitle", "FileIcon" }, new int[] { R.id.file_text, R.id.file_icon });
			listview_file.setAdapter(listItemAdapter);
		}
		setProgressBarIndeterminate(false);
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			if (nowPath.equals(rootPath)) {
				finish();
			} else {
				getFileDir(parentPath);
			}
			return false;
		}
		return false;
	}
}
