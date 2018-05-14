using Xamarin.Forms;
using Xamarin.Forms.Platform.iOS;
using UIKit;
using VocaKnow.Renderers;
using VocaKnow.iOS.Renderers;

[assembly: ExportRenderer(typeof(BoxBorderEntry), typeof(BoxBorderEntryRenderer))]
namespace VocaKnow.iOS.Renderers
{
    public class BoxBorderEntryRenderer : EntryRenderer
    {
        protected override void OnElementChanged(ElementChangedEventArgs<Entry> e)
        {
            base.OnElementChanged(e);

            if (Control != null)
            {
                Control.TextColor = UIColor.LightGray;
                Control.Layer.BorderColor = UIColor.LightGray.CGColor;
                Control.Layer.BorderWidth = 3.0f;
            }
        }
    }
}