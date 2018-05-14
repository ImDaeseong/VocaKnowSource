using System;

namespace VocaKnow.Interfaces
{
    public interface IFile
    {
        bool FileExists(string path);

        void FileDelete(string path);
    }
}
