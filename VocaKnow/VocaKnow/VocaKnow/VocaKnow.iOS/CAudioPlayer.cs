using System;
using AVFoundation;
using Foundation;
using System.IO;
using Xamarin.Forms;
using VocaKnow.iOS;
using VocaKnow.Interfaces;

[assembly: Dependency(typeof(CAudioPlayer))]
namespace VocaKnow.iOS
{
    public class CAudioPlayer : AudioPlayer
    {
        public AVAudioPlayer Player { get; set; }
        public NSError Error;
        public CAudioPlayer()
        {
            //AudioFilePath = Path.GetTempPath();
            AudioFilePath = Environment.GetFolderPath(Environment.SpecialFolder.Personal);
        }

        void AudioPlayer.PlayAudio()
        {
            Player = new AVAudioPlayer(NSUrl.FromFilename(Path.Combine(AudioFilePath, AudioFileName + ".mp4")), ".mp4", out Error);//Player = new AVAudioPlayer(NSUrl.FromFilename(Path.Combine(AudioFilePath, AudioFileName + ".wav")), ".wav", out Error);
            Player.Play();
        }

        void AudioPlayer.StopAudio()
        {
            Player.Stop();
        }

        void AudioPlayer.PauseAudio()
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
            get { return Player == null ? 0 : Player.Duration; }
        }

        public double CurrentPosition
        {
            get { return Player == null ? 0 : Player.CurrentTime; }
        }

        public bool IsPlaying
        {
            get { return Player == null ? false : Player.Playing; }
        }

    }

}