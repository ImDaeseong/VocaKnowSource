using Android.Media;
using Xamarin.Forms;
using VocaKnow.Droid;
using VocaKnow.Interfaces;

[assembly: Dependency(typeof(CAudioPlayer))]
namespace VocaKnow.Droid
{    
    public class CAudioPlayer : AudioPlayer
    {
        public MediaPlayer Player { get; set; }
        public CAudioPlayer()
        {
            Player = new MediaPlayer();

            AudioFilePath = System.Environment.GetFolderPath(System.Environment.SpecialFolder.Personal);
            //AudioFilePath = Path.GetTempPath();
        }

        public void PlayAudio()
        {
            Player.Completion += (sender, e) => {
                Player.Reset();
            };
            Player.SetDataSource(AudioFilePath + "/" + AudioFileName + ".mp4");//Player.SetDataSource(AudioFilePath + "/" + AudioFileName + ".wav");
            Player.Prepare();
            Player.Start();
        }

        public void StopAudio()
        {            
            Player.Stop();
        }

        public void PauseAudio()
        {
            Player.Pause();
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

        public double Duration
        {
            get { return Player == null ? 0 : ((double)Player.Duration) / 1000.0; }
        }

        public double CurrentPosition
        {
            get { return Player == null ? 0 : ((double)Player.CurrentPosition) / 1000.0; }
        }

        public bool IsPlaying
        {
            get { return Player == null ? false : Player.IsPlaying; }
        }

    }
    
}