package ds.id.Bahasa.Database;

import android.content.Context;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class CopyDBfile {

    private static final String TAG = CopyDBfile.class.getSimpleName();

    static final String DATABASE_NAME = "IndonesiaDB.db";

    public CopyDBfile(Context context){

        final String PATH_DATABASE = "/data/data/" + context.getApplicationContext().getPackageName() + "/databases/";

        try {
            InputStream myinput = context.getAssets().open(DATABASE_NAME);
            File file = new File(PATH_DATABASE + DATABASE_NAME);

            if(!file.exists()) {
                File dir  = new File(PATH_DATABASE);
                dir.mkdirs();

                OutputStream myoutput =  new FileOutputStream(PATH_DATABASE + DATABASE_NAME);
                byte[] buffer = new byte[1024];
                int length;
                while ((length = myinput.read(buffer)) > 0) {
                    myoutput.write(buffer, 0, length);
                }

                myoutput.flush();
                myoutput.close();
                myinput.close();
            }

        } catch(IOException e) {
        }

    }
}