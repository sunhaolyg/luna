package com.example.luna.music;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.example.luna.R;
import com.example.luna.base.BaseActivity;

public class MusicActivity extends BaseActivity {

    private Button mStartMusic;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);


        IntentFilter intentfilter = new IntentFilter();
        intentfilter.addAction(Intent.ACTION_MEDIA_SCANNER_FINISHED);

        intentfilter.addAction(Intent.ACTION_MEDIA_SCANNER_STARTED);
        intentfilter.addAction(Intent.ACTION_MEDIA_UNMOUNTED);
        /// M: add action for Scanning sdcard @{
        intentfilter.addAction(Intent.ACTION_MEDIA_MOUNTED);
        /// @}
        intentfilter.addDataScheme("file");

        ScanSdReceiver scanSdReceiver = new ScanSdReceiver();
        registerReceiver(scanSdReceiver, intentfilter);

        mStartMusic = findViewById(R.id.music);
        mStartMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + Environment.getExternalStorageDirectory())));
                MediaScannerConnection.scanFile(mContext, new String[]{Environment.getExternalStorageDirectory()+""},
                        null, new MyOnScanCompletedListener(0, 0));
            }
        });
    }

    private class MyOnScanCompletedListener implements MediaScannerConnection.OnScanCompletedListener {

        private int index;
        private int fileNum;

        public MyOnScanCompletedListener(int index, int fileName) {
            // TODO Auto-generated constructor stub
            this.index = index;
            this.fileNum = fileName;
        }


        @Override
        public void onScanCompleted(String path, Uri uri) {
//            Log.i("GetNativeMusicThread", "Scanned " + path + ":");
            Log.i("GetNativeMusicThread", "-> uri=" + uri+",path = "+path);

            Log.i("GetNativeMusicThread", "file num is : " + fileNum + " ,index:" + index);

//
//			String[] proj = { MediaStore.Images.Media.DATA };
//			Cursor actualimagecursor = context.managedQuery(uri,proj,null,null,null);
//			int actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//			actualimagecursor.moveToFirst();
//			String img_path = actualimagecursor.getString(actual_image_column_index);
//			File file = new File(img_path);
//			Uri fileUri = Uri.fromFile(file);


            Cursor cursor = getContentResolver().query(
                    uri, null, null, null,
                    MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
//			List<MusicInfo> musicInfos = new ArrayList<MusicInfo>();
//	        Log.e("GetNativeMusicThread", "getmusicinfos+ "+cursor.getCount()+",,,uri :"+MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
            if (cursor != null) {
//				for (int i = 0; i < cursor.getCount(); i++) {
                while (cursor.moveToNext()) {
//                    MusicInfo musicInfo = new MusicInfo();
////					cursor.moveToNext();
//                    long id = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media._ID)); //音乐id
//                    String title = cursor.getString((cursor.getColumnIndex(MediaStore.Audio.Media.TITLE))); //音乐标题
//                    String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));  //艺术家
//                    long duration = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));  //时长
//                    long size = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE));  //文件大小
//                    String url = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));   //文件路径
//                    int isMusic = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.IS_MUSIC));  //是否为音乐
//                    long albumId = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)); //albumid
//                    System.out.println("in all url: " + url);
//                    Log.i("GetNativeMusicThread", "Scanned  title " + "，getmusicinfos： " + cursor.getCount() + title + ",artist :" + artist + ",,size:" + size + ",,isMusic:" + isMusic);
//                    if (isMusic != 0) {//只把音乐添加到集合当中
//                        musicInfo.setId(id);
//                        musicInfo.setTitle(title);
//                        musicInfo.setArtist(artist);
//                        musicInfo.setDuration(duration);
//                        musicInfo.setUrl(url);
//                        musicInfo.setSize(size);
//                        musicInfo.setAlbumId(albumId);
//                        musicInfos.add(musicInfo);
//
//                    }
                }
                cursor.close();
                //扫描到10个文件或者到文件末尾就sendmessage
                if ((index + 1) % 10 == 0 || (index + 1) == fileNum) {

                    //将音乐文件的信息对象发送出去
//		          	musicInfos = getMusicInfos(context);
//	    			handler.obtainMessage(CONSTANTS.MSG_GET_NATIVE_MUSIC_SUCCESS, musicInfos).sendToTarget();//这个没想到怎么延时
//                    Message msg = handler.obtainMessage(CONSTANTS.MSG_GET_NATIVE_MUSIC_SUCCESS, musicInfos);
//                    handler.sendMessageDelayed(msg, 1000);//延时5秒再发送消息
                }
            }
//			System.out.println("---------"+musicInfos.size());

        }
    }

    public class ScanSdReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i("GetNativeMusicThread", "file num is : "  + " ,intent:" + intent);
            String action = intent.getAction();
            if (Intent.ACTION_MEDIA_SCANNER_STARTED.equals(action)) {
                //开始扫描，把你的代码放这里
            } else if (Intent.ACTION_MEDIA_SCANNER_FINISHED.equals(action)) {
                //扫描结束，把你的代码放这里
            }
        }
    }

}
