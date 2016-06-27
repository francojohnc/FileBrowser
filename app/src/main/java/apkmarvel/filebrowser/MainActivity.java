package apkmarvel.filebrowser;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;

public class MainActivity extends AppCompatActivity {
    private static final int OPEN_FILE_REQUEST_CODE = 1;
    public static final String TAG = MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void open(View v){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        startActivityForResult(intent, OPEN_FILE_REQUEST_CODE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e(TAG, "requestCode: "+requestCode);
        Log.e(TAG, "resultCode: "+resultCode);
        Log.e(TAG, "Intent: "+data.toString());
        Log.e(TAG, "getData Uri: "+data.getData().toString());
        if(requestCode == OPEN_FILE_REQUEST_CODE && resultCode == RESULT_OK) {
            String extension = MimeTypeMap.getSingleton().getExtensionFromMimeType(getContentResolver().getType(data.getData()));
            if (extension == null) {
                Log.e(TAG, "File has no extension or invalid extension");
                return;
            } else {
                Log.e(TAG, "File extension: " + extension);
            }
        }
    }
}
