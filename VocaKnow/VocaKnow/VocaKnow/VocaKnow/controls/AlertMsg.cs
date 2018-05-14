using Xamarin.Forms;
using VocaKnow.Interfaces;

namespace VocaKnow.controls
{
    public class AlertMsg
    {
        private static AlertMsg selfInstance = null;
        public static AlertMsg getInstance
        {
            get
            {
                if (selfInstance == null) selfInstance = new AlertMsg();
                return selfInstance;
            }
        }

        static IMessageShow Msg
        {
            get
            {
                return DependencyService.Get<IMessageShow>();
            }
        }

        public void ShortAlert(string sMessage)
        {
            Msg.ShortAlert(sMessage);
        }

        public void LongAlert(string sMessage)
        {
            Msg.LongAlert(sMessage);
        }

    }
}
