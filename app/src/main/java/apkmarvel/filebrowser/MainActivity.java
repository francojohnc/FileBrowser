package apkmarvel.filebrowser;

import android.content.Intent;
import android.os.Bundle;
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


    }
    public void OpenFile(View v){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        startActivityForResult(intent, OPEN_FILE_REQUEST_CODE);
    }
    public void getFileList(View v){
        File sdCardRoot = UtilFile.sdCardRootFile();
        ArrayList<File> fileList = UtilFile.getAllFileFromFolder(sdCardRoot);
        for(int i = 0;i<fileList.size();i++){
            Log.e(TAG, "file: "+fileList.get(i).getName());
        }

    }
    public void getFolderList(View v){
        File sdCardRoot = UtilFile.sdCardRootFile();
        ArrayList<File> fileList = UtilFile.getAllFileFromFolder(sdCardRoot);
        for(int i = 0;i<fileList.size();i++){
            if (fileList.get(i).isDirectory()) {
                Log.e(TAG, "file: "+fileList.get(i).getName());
            }
        }
    }
    public void getSpecificFileList(View v){
        getFile(UtilFile.sdCardRootFile());
        for (int i = 0; i < fileList.size(); i++) {
            Log.e(TAG, "file: "+fileList.get(i).getPath());
            Log.e(TAG, "fileName: "+fileList.get(i).getName());
        }
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
    public void getFile(File dir) {
        File listFile[] = UtilFile.getListFileFromFolder(dir);
            for (int i = 0; i < listFile.length; i++) {
                if (listFile[i].isDirectory()) {
                    getFile(listFile[i]);
                } else {
                    boolean validFile = UtilFile.isFileExtension(listFile[i],".jpg");
                    if (validFile){
                        fileList.add(listFile[i]);
                    }
                }
            }
    }
}
