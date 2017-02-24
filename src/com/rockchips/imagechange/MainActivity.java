package com.rockchips.imagechange;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.os.Message;;
public class MainActivity extends Activity {

	private ProgressDialog mProgressDialog;
	private Handler mHandler = new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				mProgressDialog.show();
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						String path = ImageUtils.saveBmpImage("/mnt/sdcard/test.png");
						Message message = new Message();
						message.what = 2;
						message.obj = path;
						sendMessage(message);
					}
				}).start();
				break;
			case 2:
				String savePath = (String)msg.obj;
				if(savePath == null || "".equals(savePath)){
					Toast.makeText(MainActivity.this, "文件转换失败" , Toast.LENGTH_SHORT).show();
				}else{
					Toast.makeText(MainActivity.this, "文件保存在:" + savePath , Toast.LENGTH_SHORT).show();
				}
				mProgressDialog.dismiss();
				break;
			default:
				break;
			}
		};
	};
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setCancelable(false);
        mHandler.sendEmptyMessage(1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
