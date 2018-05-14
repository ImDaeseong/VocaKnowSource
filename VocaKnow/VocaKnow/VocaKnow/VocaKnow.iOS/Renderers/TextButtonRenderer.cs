using Xamarin.Forms;
using VocaKnow.Renderers;
using VocaKnow.iOS.Renderers;
using Xamarin.Forms.Platform.iOS;
using UIKit;

[assembly: ExportRenderer(typeof(TextButton), typeof(TextButtonRenderer))]
namespace VocaKnow.iOS.Renderers
{
    public class TextButtonRenderer : ButtonRenderer
    {
        protected override void OnElementChanged(ElementChangedEventArgs<Button> e)
        {            
            /*
            base.OnElementChanged(e);
            if (Control != null)
            {
                Control.HorizontalAlignment = UIControlContentHorizontalAlignment.Left;
                Control.ContentEdgeInsets = new UIEdgeInsets(0, 10, 0, 10);
                Control.Layer.CornerRadius = 0f;
            }
            */

            base.OnElementChanged(e);

            TextButton skin = (TextButton)Element;
            if (skin != null)
            {
                Control.HorizontalAlignment = UIControlContentHorizontalAlignment.Left;
                Control.ContentEdgeInsets = new UIEdgeInsets(0, 10, 0, 10);
                Control.Layer.CornerRadius = 0f;

                if (skin.SkinStyle == 1)
                {

                }
                else if (skin.SkinStyle == 2)
                {

                }
                else if (skin.SkinStyle == 3)
                {

                }
                else if (skin.SkinStyle == 4)
                {

                }
                else if (skin.SkinStyle == 5)
                {

                }
                else if (skin.SkinStyle == 6)
                {

                }
                else if (skin.SkinStyle == 7)
                {

                }
            } 
                       
        }
    }
}