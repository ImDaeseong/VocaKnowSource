using AVFoundation;
using Xamarin.Forms;
using VocaKnow.iOS;
using VocaKnow.Interfaces;

[assembly: Dependency(typeof(CTextToSpeech))]
namespace VocaKnow.iOS
{
    public class CTextToSpeech : ITextToSpeech
    {
        private readonly AVSpeechSynthesizer speechSynthesizer = new AVSpeechSynthesizer();
        private AVSpeechSynthesisVoice language = AVSpeechSynthesisVoice.FromLanguage("ko-KR");
                
        public CTextToSpeech()
        {           
        }

        public bool IsSpeaking
        {
            get { return this.speechSynthesizer.Speaking; }
        }
      
        public void Speak(string text)
        {           
            var speechUtterance = new AVSpeechUtterance(text)
            {
                Rate = AVSpeechUtterance.MaximumSpeechRate / 4,
                Voice = language,//AVSpeechSynthesisVoice.FromLanguage("ko-KR"),
                Volume = 0.5f,
                PitchMultiplier = 1.0f
            };           
            speechSynthesizer.SpeakUtterance(speechUtterance);
        }

        public void Stop()
        {
            this.speechSynthesizer.StopSpeaking(AVSpeechBoundary.Immediate);
        }

        public void SetLanguage(string sLanguage)
        {
            switch (sLanguage)
            {
                case "Japanese":
                    this.language = AVSpeechSynthesisVoice.FromLanguage("ja-JP");
                    break;

                case "Korean":
                    this.language = AVSpeechSynthesisVoice.FromLanguage("ko-KR");
                    break;

                case "Indonesian":
                    this.language = AVSpeechSynthesisVoice.FromLanguage("id-ID");
                    break;

                default:
                    this.language = AVSpeechSynthesisVoice.FromLanguage("en-US");
                    break;
            }
        }

    }

}