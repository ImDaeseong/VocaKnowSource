using System.ComponentModel;
using Xamarin.Forms;
using Xamarin.Forms.Platform.Android;
using Android.Graphics;
using VocaKnow.Renderers;
using VocaKnow.Droid.Renderers;

[assembly: ExportRenderer(typeof(ColorSwitch), typeof(ColorSwitchRenderer))]
namespace VocaKnow.Droid.Renderers
{
    public class ColorSwitchRenderer : SwitchRenderer
    {
        private Android.Graphics.Color TintColor;
        private Android.Graphics.Color ThumbColor;

        protected override void OnElementChanged(ElementChangedEventArgs<Xamarin.Forms.Switch> e)
        {
            base.OnElementChanged(e);

            /*
            if (Control != null)
            {
                //Control.SetBackgroundColor(Android.Graphics.Color.Blue);
                //Control.SetTextColor(global::Android.Graphics.Color.Red);
                //Control.ThumbDrawable.SetColorFilter(new Android.Graphics.Color(245, 245, 245), PorterDuff.Mode.SrcAtop);
                //Control.ThumbDrawable.SetColorFilter(Android.Graphics.Color.Orange, PorterDuff.Mode.Multiply);
                //Control.TrackDrawable.SetColorFilter(Android.Graphics.Color.Gray, PorterDuff.Mode.Overlay);

                //Control.SetBackgroundColor(Android.Graphics.Color.White);
                Control.ThumbDrawable.SetColorFilter(new Android.Graphics.Color(51, 167, 214), PorterDuff.Mode.Multiply);
                Control.TrackDrawable.SetColorFilter(new Android.Graphics.Color(225, 225, 227), PorterDuff.Mode.Overlay);                
            }
            */

            if (Control == null || Element == null) return;
            var element = (ColorSwitch)Element;
            UpdateProperties(element);
            UpdateSwitchColor();
        }

        protected override void OnElementPropertyChanged(object sender, PropertyChangedEventArgs e)
        {
            base.OnElementPropertyChanged(sender, e);
            if (sender == null) return;
            var element = (ColorSwitch)sender;
            UpdateProperties(element);
            UpdateSwitchColor();
        }

        private void UpdateProperties(ColorSwitch SwitchElement)
        {
            TintColor = SwitchElement.TintColor.ToAndroid();
            ThumbColor = SwitchElement.ThumbColor.ToAndroid();
        }

        private void UpdateSwitchColor()
        {
            Control.SetBackgroundColor(Android.Graphics.Color.White);
            Control.ThumbDrawable.SetColorFilter(TintColor, PorterDuff.Mode.Multiply);
            Control.TrackDrawable.SetColorFilter(ThumbColor, PorterDuff.Mode.Multiply);// Overlay);
        }
    }
}