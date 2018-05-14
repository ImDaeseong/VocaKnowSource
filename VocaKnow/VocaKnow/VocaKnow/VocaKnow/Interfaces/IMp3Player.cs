using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;

namespace VocaKnow.Interfaces
{
    public interface IMp3Player : IDisposable
    {
        void Play();

        void Pause();

        void Stop();

        void Seek(double position);

        double Duration { get; }

        double CurrentPosition { get; }

        bool IsPlaying { get; }

        bool CanSeek { get; }

        double Volume { get; set; }

        double Balance { get; set; }
    
        string AudioFileName { get; set; }
        string AudioFilePath { get; set; }
    }
}
