using Xamarin.Forms;
using VocaKnow.Renderers;
using VocaKnow.iOS.Renderers;
using Xamarin.Forms.Platform.iOS;
using CoreGraphics;

[assembly: ExportRenderer(typeof(ColorProgressBar), typeof(ColorProgressBarRenderer))]
namespace VocaKnow.iOS.Renderers
{
    public class ColorProgressBarRenderer : ProgressBarRenderer
    {
        protected override void OnElementChanged(ElementChangedEventArgs<ProgressBar> e)
        {
            base.OnElementChanged(e);

            Control.ProgressTintColor = Color.FromRgb(255, 255, 255).ToUIColor();
            var semiTransparentColor = new Color(255, 255, 255, 0.5);
            Control.TrackTintColor = semiTransparentColor.ToUIColor();
        }
        
        public override void LayoutSubviews()
        {
            base.LayoutSubviews();

            this.ClipsToBounds = true;
            this.Layer.MasksToBounds = true;
            this.Layer.CornerRadius = 5;

            var X = 1.0f;
            var Y = 1.2f;

            CGAffineTransform transform = CGAffineTransform.MakeScale(X, Y);
            transform.TransformSize(this.Frame.Size);
            this.Transform = transform;
        }
    }
}