using System.IO;
using Xamarin.Forms;
using VocaKnow.Droid;
using VocaKnow.Interfaces;

[assembly: Dependency(typeof(CIFile))]
namespace VocaKnow.Droid
{
    public class CIFile : IFile
    {
        public bool FileExists(string path)
        {
            return File.Exists(path);
        }

        public void FileDelete(string path)
        {
            File.Delete(path);
        }

    }
}