using VocaKnow.Droid.Renderers;
using VocaKnow.Renderers;
using Xamarin.Forms;
using Xamarin.Forms.Platform.Android;

[assembly: ExportRenderer(typeof(CustomFrame), typeof(CustomFrameRenderer))]
namespace VocaKnow.Droid.Renderers
{
    public class CustomFrameRenderer : FrameRenderer
    {
        protected override void OnElementChanged(ElementChangedEventArgs<Frame> e)
        {
            base.OnElementChanged(e);
            Background = Resources.GetDrawable(Resource.Drawable.roundedBorder);
        }
    }
}