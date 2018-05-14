using UIKit;
using Xamarin.Forms;
using VocaKnow.iOS;
using VocaKnow.Interfaces;

[assembly: Dependency(typeof(CScreenLock))]
namespace VocaKnow.iOS
{
    public class CScreenLock : IScreenLock
    {
        public CScreenLock()
        {
        }

        public void Lock()
        {
            UIApplication.SharedApplication.IdleTimerDisabled = true;
        }

        public void Unlock()
        {
            UIApplication.SharedApplication.IdleTimerDisabled = false;
        }
    }
}