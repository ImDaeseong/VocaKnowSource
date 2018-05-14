using System;
using AVFoundation;
using Foundation;
using System.IO;
using Xamarin.Forms;
using VocaKnow.iOS;
using VocaKnow.Interfaces;

[assembly: Dependency(typeof(CAudioRecorder))]
namespace VocaKnow.iOS
{
    public class CAudioRecorder : AudioRecorder
    {
        public AVAudioRecorder Recorder { get; set; }
        public NSError Error;
        public NSUrl Url { get; set; }
        public NSDictionary Settings { get; set; }

        public void PrepareRecorder()
        {
            if (Recorder != null)
            {
                Recorder.Dispose();
            }
            var audioSession = AVAudioSession.SharedInstance();
            var err = audioSession.SetCategory(AVAudioSessionCategory.PlayAndRecord);
            if (err != null)
            {
                Console.WriteLine("audioSession: {0}", err);
            }
            err = audioSession.SetActive(true);
            if (err != null)
            {
                Console.WriteLine("audioSession: {0}", err);
            }

            Console.WriteLine("Audio File Path: " + AudioFilePath + AudioFileName);
            Url = NSUrl.FromFilename(AudioFilePath + AudioFileName + ".mp4");

            if (File.Exists(AudioFilePath + "/" + AudioFileName + ".mp4"))
            {
                File.Delete(AudioFilePath + "/" + AudioFileName + ".mp4");
            }

            //set up the NSObject Array of values that will be combined with the keys to make the NSDictionary
            NSObject[] values = new NSObject[] {
                NSNumber.FromFloat (44100.0f),
				//Sample Rate
				NSNumber.FromInt32 ((int)AudioToolbox.AudioFormatType.LinearPCM),
				//AVFormat
				NSNumber.FromInt32 (2),
				//Channels
				NSNumber.FromInt32 (16),
				//PCMBitDepth
				NSNumber.FromBoolean (false),
				//IsBigEndianKey
				NSNumber.FromBoolean (false)
				//IsFloatKey
			};
            //Set up the NSObject Array of keys that will be combined with the values to make the NSDictionary
            NSObject[] keys = new NSObject[] {
                AVAudioSettings.AVSampleRateKey,
                AVAudioSettings.AVFormatIDKey,
                AVAudioSettings.AVNumberOfChannelsKey,
                AVAudioSettings.AVLinearPCMBitDepthKey,
                AVAudioSettings.AVLinearPCMIsBigEndianKey,
                AVAudioSettings.AVLinearPCMIsFloatKey
            };

            Settings = NSDictionary.FromObjectsAndKeys(values, keys);
            Recorder = AVAudioRecorder.Create(Url, new AudioSettings(Settings), out Error);
            Recorder.PrepareToRecord();
        }

        public CAudioRecorder()
        {            
            AudioFilePath = System.Environment.GetFolderPath(System.Environment.SpecialFolder.Personal);
            //AudioFilePath = Path.GetTempPath();
        }

        public void StartRecord()
        {
            try
            {
                PrepareRecorder();
                Recorder.Record();
            }
            catch { }
        }
                
        public void StopRecord()
        {
            try
            {
                if (Recorder != null)
                    Recorder.Stop();
            }
            catch { }
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