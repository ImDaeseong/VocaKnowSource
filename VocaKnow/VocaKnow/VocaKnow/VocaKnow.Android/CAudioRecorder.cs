using Android.Media;
using System.IO;
using VocaKnow.Droid;
using VocaKnow.Interfaces;
using Xamarin.Forms;

[assembly: Dependency(typeof(CAudioRecorder))]
namespace VocaKnow.Droid
{
    public class CAudioRecorder : AudioRecorder
    {
        public MediaRecorder Recorder { get; set; }

        public CAudioRecorder()
        {
            Recorder = new MediaRecorder();

            AudioFilePath = System.Environment.GetFolderPath(System.Environment.SpecialFolder.Personal);
            //AudioFilePath = Path.GetTempPath();
        }

        public void StartRecord()
        {
            try
            {
                string sFilePath = AudioFilePath + "/" + AudioFileName + ".mp4";

                if (File.Exists(sFilePath))
                {
                    File.Delete(sFilePath);
                }

                if (Recorder == null)
                    Recorder = new MediaRecorder();

                Recorder.Reset();
                Recorder.SetAudioSource(AudioSource.Mic);
                Recorder.SetOutputFormat(OutputFormat.Mpeg4);
                Recorder.SetAudioEncoder(AudioEncoder.AmrNb);
                Recorder.SetOutputFile(sFilePath);
                Recorder.Prepare();
                Recorder.Start();
            }
            catch { }

            /*
            Recorder.SetAudioSource(AudioSource.Mic);
            Recorder.SetOutputFormat(OutputFormat.ThreeGpp);
            Recorder.SetAudioEncoder(AudioEncoder.AmrNb);

            Recorder.SetOutputFile(AudioFilePath + "/" + AudioFileName + ".wav");
            Recorder.Prepare();
            Recorder.Start();
            */
        }

        public void StopRecord()
        {
            try
            {
                if (Recorder != null)
                {
                    Recorder.Stop();

                    Recorder.Reset();
                    Recorder.Release();
                    Recorder = null;
                }
            }
            catch { }

            /*
            Recorder.Stop();
            Recorder.Reset();
            */
        }

        private string audioFileName;
        public string AudioFileName
        {
            get
            {
                return audioFileName;
            }
            set
            {
                audioFileName = value;
            }
        }

        private string audioFilePath;
        public string AudioFilePath
        {
            get
            {
                return audioFilePath;
            }
            set
            {
                audioFilePath = value;
            }
        }

    }
}