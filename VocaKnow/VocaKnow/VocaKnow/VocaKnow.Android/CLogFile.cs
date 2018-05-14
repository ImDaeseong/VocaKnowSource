using Xamarin.Forms;
using System;
using System.IO;
using System.Threading;
using System.Threading.Tasks;
using VocaKnow.Droid;
using VocaKnow.Interfaces;

[assembly: Dependency(typeof(CLogFile))]
namespace VocaKnow.Droid
{
    public class CLogFile : ILogFile
    {
        public async Task WriteString(string sText)
        {
            /*
            string spath = Android.OS.Environment.ExternalStorageDirectory.AbsolutePath + "/Music/DaeseongLog.txt";
            using (var writer = File.CreateText(spath))
            {
                await writer.WriteAsync(sText);
            }
            */
            var sDoc = System.Environment.GetFolderPath(System.Environment.SpecialFolder.MyDocuments);
            var spath = Path.Combine(sDoc, "daeseong.txt");
            using (var writer = File.CreateText(spath))
            {
                await writer.WriteAsync(sText);
            }            
        }
    }
}