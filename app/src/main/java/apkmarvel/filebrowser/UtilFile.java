package apkmarvel.filebrowser;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;


/*
*  permission
*  <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
*  <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
*/
public class UtilFile {

//    down vote
//    I had been having the exact same problem!
//
//    To get the internal SD card you can use
//
//    String extStore = System.getenv("EXTERNAL_STORAGE");
//    File f_exts = new File(extStore);
//    To get the external SD card you can use
//
//    String secStore = System.getenv("SECONDARY_STORAGE");
//    File f_secs = new File(secStore);
//    On running the code
//
//    extStore = "/storage/emulated/legacy"
//    secStore = "/storage/extSdCarcd"
//    works perfectly!
    public static String TAG = UtilFile.class.getSimpleName();

    public static String getFileNameFromUrl(String url) {
        return url.substring(url.lastIndexOf("/"));
    }
    public static File makeDir(String path) {
        File dir = new File(path);
        if (!isExist(dir)) {
            dir.mkdirs();
        }
        return dir;
    }
    public static boolean isExist(File file) {
        return file.exists();
    }
    public static String sdCardRoot() {
        return Environment.getExternalStorageDirectory().getAbsolutePath()+ File.separator;
    }
    public static File sdCardRootFile() {
        return  new File(Environment.getExternalStorageDirectory().getAbsolutePath());
    }
    public static String internalRoot() {
        return Environment.getDataDirectory().getAbsolutePath()+ File.separator;
    }
    public static File internalRootFile() {
        return  new File(Environment.getRootDirectory().getAbsolutePath());
    }
    public static File createFile(String dir,String fileName)throws IOException {
        File file = new File(dir + File.separator + fileName);
        file.createNewFile();
        return file;
    }
    public static File[] getListFileFromFolder(File dir){
        File listFile[] = dir.listFiles();
        if (listFile == null && listFile.length < 1) {
            Log.e(TAG,"getListFileFromFolder: null");
        }
        return listFile;
    }
    public static ArrayList<File> getAllFileFromFolder(File dir){
        File listFile[] = getListFileFromFolder(dir);
        ArrayList<File> fileArrayList = new ArrayList<>();
        for (int i = 0; i < listFile.length; i++) {
                fileArrayList.add(listFile[i]);
        }
        return fileArrayList;
    }
    public static boolean isFileExtension(File file,String fileExtension){
        return file.getName().endsWith(fileExtension);
    }
    public static File writeFileFromInput(String path, String fileName,InputStream input) {
        File file = null;
        OutputStream output = null;
        try {
            file = createFile(fileName, path);
            output = new FileOutputStream(file);
            byte buffer[] = new byte[4 * 1024];
            int temp;
            while ((temp = input.read(buffer)) != -1) {
                output.write(buffer, 0, temp);
            }
            output.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                output.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return file;
    }
}
