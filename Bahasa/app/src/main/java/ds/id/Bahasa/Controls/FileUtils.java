package ds.id.Bahasa.Controls;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

public class FileUtils {

    public static void copy(File src, File outPut) {
        try {
            BufferedInputStream fis = null;
            BufferedOutputStream fos = null;
            try {
                fis = new BufferedInputStream(new FileInputStream(src));
                fos = new BufferedOutputStream(new FileOutputStream(outPut));
                int len;
                byte[] buffer = new byte[8 * 1024];
                while ((len = fis.read()) != -1) {
                    fos.write(buffer, 0, len);
                }
            } finally {
                if (fis != null) {
                    try {
                        fis.close();
                    } finally {
                        if (fos != null) {
                            fos.close();
                        }
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void copy(InputStream inputStream, File outPutFile) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(outPutFile);
            byte[] buffer = new byte[1024 * 8];
            int len;
            while ((len = inputStream.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    inputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static boolean FileExists(String sPath){

        boolean bFind = false;

        try{

            File file = new File(sPath);
            if(file.exists()){
                bFind = true;
            }
        }catch (Exception ex){
            ex.getMessage().toString();
        }
        return bFind;
    }

    public static void FileDelete(String sPath){
        try{

            File file = new File(sPath);
            if(file.exists()){
                file.delete();
            }
        }catch (Exception ex){
            ex.getMessage().toString();
        }
    }
}
