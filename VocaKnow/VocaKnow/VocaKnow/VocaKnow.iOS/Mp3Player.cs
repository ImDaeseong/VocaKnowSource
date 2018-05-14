using System;
using Foundation;
using AVFoundation;
using Xamarin.Forms;
using System.IO;
using VocaKnow.iOS;
using VocaKnow.Interfaces;
using Debug = System.Diagnostics.Debug;

//테스트 못함
[assembly: Dependency(typeof(Mp3Player))]
namespace VocaKnow.iOS
{
    public class Mp3Player : IMp3Player
    {       
        private double dVolume = 0.5;
        private double dBalance = 0;
       
        private PlayStatus mStatus;
        private enum PlayStatus
        {
            Stopped,
            Error,
            Buffering,
            Playback,
            Paused,
            Completed
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


        private AVAudioPlayer Player = null;

        public Mp3Player()
        {            
            AudioFilePath = Environment.GetFolderPath(Environment.SpecialFolder.Personal);
            //AudioFilePath = Path.GetTempPath();
        }

        private void InitMediaPlayer()
        {
            try
            {                
                var url = NSUrl.FromFilename(Path.Combine(AudioFilePath, AudioFileName + ".mp4"));//var url = NSUrl.FromFilename(Path.Combine(AudioFilePath, AudioFileName + ".wav"));
                Player = AVAudioPlayer.FromUrl(url);
                /*
                var url = NSUrl.FromString(MP3Value);
                Player = AVAudioPlayer.FromUrl(url);
                */
                Player.BeginInterruption += Player_BeginInterruption;
                Player.FinishedPlaying += Player_FinishedPlaying;
            }
            catch (Exception ex)
            {
                Debug.WriteLine(ex.Message.ToString());

                mStatus = PlayStatus.Error;
                DestroyMediaPlayer();
            }
        }

        private void Player_FinishedPlaying(object sender, AVStatusEventArgs e)
        {
            Player.Stop();
            Player.Dispose();
            mStatus = PlayStatus.Completed;
        }

        private void Player_BeginInterruption(object sender, EventArgs e)
        {
            Player.Play();
            mStatus = PlayStatus.Playback;
        }

        private void DestroyMediaPlayer()
        {
            try
            {
                Player.BeginInterruption -= Player_BeginInterruption;
                Player.FinishedPlaying -= Player_FinishedPlaying;

                Player.Stop();
                Player.Dispose();
                Player = null;
            }
            catch (Exception ex)
            {
                Debug.WriteLine(ex.Message.ToString());
            }
        }

        public void Play()
        {
            try
            {
                InitMediaPlayer();

                Player.PrepareToPlay();
                mStatus = PlayStatus.Buffering;
            }
            catch (Exception ex)
            {
                Debug.WriteLine(ex.Message.ToString());

                mStatus = PlayStatus.Error;
                DestroyMediaPlayer();
            }
        }

        public void Pause()
        {
            if (Player.Playing)
            {
                Player.Pause();
                mStatus = PlayStatus.Paused;
            }
            else
            {
                Player.Play();
                mStatus = PlayStatus.Playback;
            }
        }

        public void Stop()
        {
            Player.Stop();
            Seek(0);
            mStatus = PlayStatus.Stopped;
        }

        public void Seek(double position)
        {
            if (Player != null)
                Player.CurrentTime = position;
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

        public bool CanSeek
        {
            get { return Player == null ? false : true; }
        }

        public double Volume
        {
            get { return dVolume; }
            set { SetVolume(dVolume = value, Balance); }
        }

        public double Balance
        {
            get { return dBalance; }
            set { SetVolume(Volume, dBalance = value); }
        }

        private void SetVolume(double dVolume, double dBalance)
        {
            if (Player == null)
                return;

            dVolume = Math.Max(0, dVolume);
            dVolume = Math.Min(1, dVolume);

            dBalance = Math.Max(0, dBalance);
            dBalance = Math.Min(1, dBalance);

            var right = (dBalance < 0) ? dVolume * -1 * dBalance : dVolume;
            var left = (dBalance > 0) ? dVolume * 1 * dBalance : dVolume;

            Player.SetVolume((float)left, (float)right);
        }
        

        #region IDisposable Support
        private bool disposedValue = false;

        protected virtual void Dispose(bool disposing)
        {
            if (!disposedValue)
            {
                if (disposing)
                {
                    DestroyMediaPlayer();
                }

                disposedValue = true;
            }
        }

        ~Mp3Player()
        {
            Dispose(false);
        }

        public void Dispose()
        {
            Dispose(true);

            GC.SuppressFinalize(this);
        }
        #endregion

    }
}