using Android.Widget;
using Xamarin.Forms;
using VocaKnow.Droid;
using VocaKnow.Interfaces;

[assembly: Dependency(typeof(CMessageShow))]
namespace VocaKnow.Droid
{
    public class CMessageShow : IMessageShow
    {
        public void LongAlert(string message)
        {
            Toast.MakeText(Android.App.Application.Context, message, ToastLength.Long).Show();
        }

        public void ShortAlert(string message)
        {
            Toast.MakeText(Android.App.Application.Context, message, ToastLength.Short).Show();
        }
    }
}