using VocaKnow.Interfaces;
using Xamarin.Forms;

namespace VocaKnow.controls
{
    public static class FilesManager
    {
        private static readonly IFile FileDependency = DependencyService.Get<IFile>();

        public static bool FileExists(string path)
        {
            return FileDependency.FileExists(path);
        }

        public static void FileDelete(string path)
        {
            FileDependency.FileDelete(path);
        }

    }
}
