using System;

namespace VocaKnow.Interfaces
{
    public interface AudioRecorder
    {
        void StartRecord();
        void StopRecord();
        string AudioFileName { get; set; }
        string AudioFilePath { get; set; }
    }
}
