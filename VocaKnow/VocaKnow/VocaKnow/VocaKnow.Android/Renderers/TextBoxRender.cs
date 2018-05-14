using Xamarin.Forms;
using Xamarin.Forms.Platform.Android;
using VocaKnow.Renderers;
using VocaKnow.Droid.Renderers;

[assembly: ExportRenderer(typeof(TextBox), typeof(TextBoxRender))]
namespace VocaKnow.Droid.Renderers
{
    public class TextBoxRender : EntryRenderer
    {
        protected override void OnElementChanged(ElementChangedEventArgs<Entry> e)
        {
            base.OnElementChanged(e);
            Control.SetTextColor(global::Android.Graphics.Color.Black);
            Control.SetBackgroundColor(global::Android.Graphics.Color.Beige);//.White);
        }
    }
}