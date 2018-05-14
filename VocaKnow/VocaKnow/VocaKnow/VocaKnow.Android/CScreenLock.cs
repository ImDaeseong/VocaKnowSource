using Android.Views;
using Xamarin.Forms;
using VocaKnow.Droid;
using VocaKnow.Interfaces;
using Plugin.CurrentActivity;

[assembly: Dependency(typeof(CScreenLock))]
namespace VocaKnow.Droid
{
    public class CScreenLock : IScreenLock
    {
        public CScreenLock()
        {
        }

        public void Lock()
        {
            MainActivity main = ((MainActivity)CrossCurrentActivity.Current.Activity);
            main.Window.SetFlags(WindowManagerFlags.KeepScreenOn, WindowManagerFlags.KeepScreenOn);
        }

        public void Unlock()
        {
            MainActivity main = ((MainActivity)CrossCurrentActivity.Current.Activity);
            main.Window.ClearFlags(WindowManagerFlags.KeepScreenOn);
        }
    }
}