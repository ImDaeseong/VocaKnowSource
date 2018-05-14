using Xamarin.Forms;
using Xamarin.Forms.Platform.iOS;
using VocaKnow.Renderers;
using VocaKnow.iOS.Renderers;

[assembly: ExportRenderer(typeof(CurvedCornersLabel), typeof(CurvedCornersLabelRenderer))]
namespace VocaKnow.iOS.Renderers
{
    public class CurvedCornersLabelRenderer : LabelRenderer
    {
        protected override void OnElementChanged(ElementChangedEventArgs<Label> e)
        {
            base.OnElementChanged(e);

            if (e.NewElement != null)
            {
                var _xfViewReference = (CurvedCornersLabel)Element;
                this.Layer.CornerRadius = (float)_xfViewReference.CurvedCornerRadius;
                this.Layer.BackgroundColor = _xfViewReference.CurvedBackgroundColor.ToCGColor();
            }
        }
    }
}