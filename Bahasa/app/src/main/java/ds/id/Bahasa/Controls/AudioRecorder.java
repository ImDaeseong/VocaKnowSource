package ds.id.Bahasa.Controls;

import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;

public class AudioRecorder {

    private static final String TAG = AudioRecorder.class.getName();

    private static MediaRecorder mediaRecorder = null;

    private final String AudioFilePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).getPath();

    private File recordFile;
    private boolean isRecording = false;

    private static AudioRecorder instance;
    public static AudioRecorder getInstance(){

        if( instance == null){
            instance = new AudioRecorder();
        }
        return instance;
    }

    public String getFilePath(String Filename){
        String sPath = AudioFilePath + "/" + Filename + ".amr";
        return sPath;
    }

    public void StartRecord(String Filename) throws IOException {

        if(!isRecording) {

            try {

                if (mediaRecorder != null) {
                    mediaRecorder.reset();
                    mediaRecorder.release();
                    mediaRecorder = null;
                }

                mediaRecorder = new MediaRecorder();
                mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                mediaRecorder.setAudioSamplingRate(16000);

                String sPath = AudioFilePath + "/" + Filename + ".amr";
                recordFile = new File(sPath);
                mediaRecorder.setOutputFile(sPath);

                if (mediaRecorder != null) {
                    mediaRecorder.prepare();
                    mediaRecorder.start();
                }
                isRecording = true;

            } catch (IOException ignore) {
                Log.e(TAG, ignore.getMessage().toString());
                isRecording = false;
            }
        }
    }

    public void StopRecord(){

        try {

            if(isRecording) {

                if (mediaRecorder != null) {
                    mediaRecorder.stop();
                    mediaRecorder.reset();
                    mediaRecorder.release();
                    mediaRecorder = null;
                }
                isRecording = false;
            }
        }catch (Exception ex){
            ex.getMessage().toString();
        }
    }

    public void deleteFile(){

        try{

            if (recordFile != null) {
                if (recordFile.exists()) {
                    recordFile.delete();
                }
                isRecording = false;
                recordFile = null;
            }
        }catch (Exception ex){
            ex.getMessage().toString();
        }
    }

    public boolean isFileExist() {

        boolean bFile = false;

        try {

            if (recordFile != null) {
                if (recordFile.exists()) {
                    bFile = true;
                }
            }
        }catch (Exception ex){
            ex.getMessage().toString();
        }

        return bFile;
    }

    public boolean isRecording() {
        return isRecording;
    }
}
