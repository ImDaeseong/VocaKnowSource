using VocaKnow.Interfaces;
using Xamarin.Forms;

namespace VocaKnow.controls
{
    public class KataLog
    {
        private static KataLog selfInstance = null;
        public static KataLog getInstance
        {
            get
            {
                if (selfInstance == null) selfInstance = new KataLog();
                return selfInstance;
            }
        }

        static ILogFile LogFile
        {
            get
            {
                return DependencyService.Get<ILogFile>();
            }
        }

        public void WriteString(string sText)
        {
            LogFile.WriteString(sText);
        }
    }
}
