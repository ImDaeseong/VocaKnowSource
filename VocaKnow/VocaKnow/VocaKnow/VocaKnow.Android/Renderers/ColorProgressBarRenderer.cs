using System;
using Xamarin.Forms;
using VocaKnow.Renderers;
using VocaKnow.Droid.Renderers;
using Xamarin.Forms.Platform.Android;

[assembly: ExportRenderer(typeof(ColorProgressBar), typeof(ColorProgressBarRenderer))]
namespace VocaKnow.Droid.Renderers
{
    public class ColorProgressBarRenderer : ProgressBarRenderer
    {
        protected override void OnElementChanged(ElementChangedEventArgs<ProgressBar> e)
        {
            base.OnElementChanged(e);

            try
            {
                var solidTransparentColor = new Color(255, 255, 255, 1.0);
                Control.ProgressDrawable.SetColorFilter(solidTransparentColor.ToAndroid(), Android.Graphics.PorterDuff.Mode.SrcIn);
                Control.ProgressTintList = Android.Content.Res.ColorStateList.ValueOf(solidTransparentColor.ToAndroid());
            }
            catch (Exception ex)
            {
                System.Diagnostics.Debug.WriteLine(ex.Message);
            }
        }

    }
}