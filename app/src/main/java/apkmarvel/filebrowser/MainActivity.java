package apkmarvel.filebrowser;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final int OPEN_FILE_REQUEST_CODE = 1;
    private static final int OPEN_FOLDER_REQUEST_CODE = 2;
    public static final String TAG = MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        File root = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
        getfile(root);
        for (int i = 0; i < fileList.size(); i++) {
            Log.e(TAG, "file: "+fileList.get(i).getName());
            if (fileList.get(i).isDirectory()) {
            }
        }
    }
    public void OpenFile(View v){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        startActivityForResult(intent, OPEN_FILE_REQUEST_CODE);
    }
    public void OpenFolder(View v){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        Uri uri = Uri.parse(Environment.getExternalStorageDirectory().getPath()
                + "/myFolder/");
        intent.setDataAndType(uri, "text/csv");
        startActivity(Intent.createChooser(intent, "Open folder"));

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == OPEN_FILE_REQUEST_CODE && resultCode == RESULT_OK) {
            Log.e(TAG, "requestCode: "+requestCode);
            Log.e(TAG, "resultCode: "+resultCode);
            Log.e(TAG, "Intent: "+data.toString());
            Log.e(TAG, "getData Uri: "+data.getData().toString());
            Log.e(TAG, "getPath : "+data.getData().getPath());
            String extension = MimeTypeMap.getSingleton().getExtensionFromMimeType(getContentResolver().getType(data.getData()));
            if (extension == null) {
                Log.e(TAG, "File has no extension or invalid extension");
                return;
            } else {
                Log.e(TAG, "File extension: " + extension);
            }
        }
    }
    private ArrayList<File> fileList = new ArrayList<>();
    public ArrayList<File> getfile(File dir) {
        File listFile[] = dir.listFiles();
        if (listFile != null && listFile.length > 0) {
            for (int i = 0; i < listFile.length; i++) {
                if (listFile[i].isDirectory()) {
                    fileList.add(listFile[i]);
                    getfile(listFile[i]);
                } else {
                    if (listFile[i].getName().endsWith(".png")
                            || listFile[i].getName().endsWith(".jpg")
                            || listFile[i].getName().endsWith(".jpeg")
                            || listFile[i].getName().endsWith(".gif"))
                    {
                        fileList.add(listFile[i]);
                    }
                }

            }
        }
        return fileList;
    }
}
