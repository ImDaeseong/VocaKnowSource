using Foundation;
using UIKit;
using Xamarin.Forms;
using VocaKnow.iOS;
using VocaKnow.Interfaces;

[assembly: Dependency(typeof(CMessageShow))]
namespace VocaKnow.iOS
{
    public class CMessageShow : IMessageShow
    {
        const double LONG_DELAY = 2.0;
        const double SHORT_DELAY = 1.0;

        NSTimer alertDelay;
        UIAlertController alert;

        public void LongAlert(string message)
        {
            ShowAlert(message, LONG_DELAY);
        }
        public void ShortAlert(string message)
        {
            ShowAlert(message, SHORT_DELAY);
        }

        void ShowAlert(string message, double seconds)
        {
            alertDelay = NSTimer.CreateScheduledTimer(seconds, (obj) =>
            {
                dismissMessage();
            });
            alert = UIAlertController.Create(null, message, UIAlertControllerStyle.Alert);
            UIApplication.SharedApplication.KeyWindow.RootViewController.PresentViewController(alert, true, null);
        }

        void dismissMessage()
        {
            if (alert != null)
            {
                alert.DismissViewController(true, null);
            }
            if (alertDelay != null)
            {
                alertDelay.Dispose();
            }
        }

    }

}