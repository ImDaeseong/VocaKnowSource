using UIKit;
using Xamarin.Forms;
using Xamarin.Forms.Platform.iOS;
using VocaKnow.Renderers;
using VocaKnow.iOS.Renderers;

[assembly: ExportRenderer(typeof(TextBox), typeof(TextBoxRender))]
namespace VocaKnow.iOS.Renderers
{
    public class TextBoxRender : EntryRenderer
    {
        protected override void OnElementChanged(ElementChangedEventArgs<Entry> e)
        {
            base.OnElementChanged(e);
            Control.BorderStyle = UITextBorderStyle.Bezel;
            Control.TextColor = UIColor.Black;
        }
    }
}