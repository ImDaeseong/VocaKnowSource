using Java.Util;
using Android.Speech.Tts;
using Android.OS;
using Xamarin.Forms;
using VocaKnow.Droid;
using VocaKnow.Interfaces;

[assembly: Dependency(typeof(CTextToSpeech))]
namespace VocaKnow.Droid
{
    public class CTextToSpeech : Java.Lang.Object, ITextToSpeech, TextToSpeech.IOnInitListener
    {
        private TextToSpeech speaker;
        private Locale language = Locale.Korean;

        public bool IsInitialized { get; private set; }
        public bool IsSpeaking { get; private set; }

        public CTextToSpeech()
        {
            speaker = new TextToSpeech(Forms.Context.ApplicationContext, this);
        }

       
        public void Speak(string sText)
        {
            if (this.IsSpeaking || !this.IsInitialized)
                return;

            this.IsSpeaking = true;
            try
            {
                this.speaker.SetLanguage(language);
                this.speaker.Speak(sText, QueueMode.Flush, null, null);
            }
            finally
            {
                this.IsSpeaking = false;
            }
        }

        public void Stop()
        {
            if (!this.IsSpeaking) return;

            try
            {
                this.speaker.Stop();
            }
            finally
            {
                this.IsSpeaking = false;
            }
        }

        public void SetLanguage(string sLanguage)
        {
            switch (sLanguage)
            {
                case "Japanese":
                    this.language = Locale.Japanese;
                    break;

                case "Korean":
                    this.language = Locale.Korean;
                    break;

                case "Indonesian":
                    this.language = Locale.Us; //this.language = Locale.Indonesian;
                    break;

                default:
                    this.language = Locale.Us; 
                    break;
            }
        }

        public void OnInit(OperationResult status)
        {
            this.IsInitialized = (status == OperationResult.Success);
        }                
    }
}