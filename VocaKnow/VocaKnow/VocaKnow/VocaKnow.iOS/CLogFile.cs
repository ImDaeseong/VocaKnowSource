using System.IO;
using Xamarin.Forms;
using System.Threading.Tasks;
using VocaKnow.iOS;
using VocaKnow.Interfaces;
using System;
using Foundation;

[assembly: Dependency(typeof(CLogFile))]
namespace VocaKnow.iOS
{
    public class CLogFile : ILogFile
    {
        public async Task WriteString(string sText)
        {
            var sDoc = NSFileManager.DefaultManager.GetUrls(NSSearchPathDirectory.DocumentDirectory, NSSearchPathDomain.User)[0].Path;//var sDoc = Environment.GetFolderPath(Environment.SpecialFolder.MyDocuments);
            var spath = Path.Combine(sDoc, "daeseong.txt");
            using (var writer = File.CreateText(spath))
            {
                await writer.WriteAsync(sText);
            }
        }

    }
}

