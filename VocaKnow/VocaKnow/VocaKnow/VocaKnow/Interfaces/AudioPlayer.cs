using System;

namespace VocaKnow.Interfaces
{
    public interface AudioPlayer
    {
        void PlayAudio();
        void StopAudio();
        void PauseAudio();
        string AudioFileName { get; set; }
        string AudioFilePath { get; set; }
        double Duration { get; }
        double CurrentPosition { get; }
        bool IsPlaying { get; }
    }
    
}
