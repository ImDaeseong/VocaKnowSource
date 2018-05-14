using VocaKnow.Interfaces;
using Xamarin.Forms;

namespace VocaKnow.controls
{
    public class MicRecording
    {
        private static MicRecording selfInstance = null;
        public static MicRecording getInstance
        {
            get
            {
                if (selfInstance == null) selfInstance = new MicRecording();
                return selfInstance;
            }
        }

        /*
        static AudioPlayer AudioPlayer
        {
            get
            {
                return DependencyService.Get<AudioPlayer>();
            }
        }
        */

        static AudioRecorder AudioRecorder
        {
            get
            {
                return DependencyService.Get<AudioRecorder>();
            }
        }

        static IMp3Player IMp3Player
        {
            get
            {
                return DependencyService.Get<IMp3Player>();
            }
        }


        public string AudioFilePath
        {
            get
            {
                return IMp3Player.AudioFilePath;//return AudioPlayer.AudioFilePath;
            }            
        }

        public string AudioFileName
        {
            get
            {
                return IMp3Player.AudioFileName;//return AudioPlayer.AudioFileName;
            }
        }

        public void SetAudioFilePath(string sFileName)
        {
            IMp3Player.AudioFileName = sFileName;//AudioPlayer.AudioFileName = sFileName;
            AudioRecorder.AudioFileName = sFileName;
        }

        public void SetRecording(bool bIsRecording)
        {
            if (bIsRecording)
                AudioRecorder.StartRecord();
            else
                AudioRecorder.StopRecord();
        }

        public void PlayAudio()
        {
            IMp3Player.Play(); //AudioPlayer.PlayAudio();
        }

        public void PauseAudio()
        {
            IMp3Player.Pause();
        }

        public void StopAudio()
        {
            IMp3Player.Stop(); //AudioPlayer.StopAudio();
        }

        public bool IsPlaying()
        {
            return IMp3Player.IsPlaying; //return AudioPlayer.IsPlaying;
        }

        public double GetPosition()
        {
            return IMp3Player.Duration; //return AudioPlayer.Duration;
        }
        
        public double GetCurrentPosition()
        {
            return IMp3Player.CurrentPosition; //return AudioPlayer.CurrentPosition;
        }

        public string GetCurrentTimeDisplay()
        {
            string strCurTime = "";
            strCurTime = string.Format("{0:D2}:{1:D2}", (int)GetCurrentPosition() / 60, (int)GetCurrentPosition() % 60);
            return strCurTime;
        }

        public string GetTotalTimeDisplay()
        {
            string strTotalTime = "";
            strTotalTime = string.Format("{0:D2}:{1:D2}", ((int)GetPosition() / 60), (int)GetPosition() % 60);
            return strTotalTime;
        }

    }
}
