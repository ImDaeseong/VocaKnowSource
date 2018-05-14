using System;

namespace VocaKnow.Interfaces
{
    public interface ITextToSpeech
    {
        void Speak(string sText);
        void SetLanguage(string sLanguage);
        bool IsSpeaking { get; }
    }
}
