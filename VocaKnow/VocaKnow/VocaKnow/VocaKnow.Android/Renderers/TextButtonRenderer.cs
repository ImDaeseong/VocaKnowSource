using Android.Views;
using Xamarin.Forms;
using Xamarin.Forms.Platform.Android;
using VocaKnow.Renderers;
using VocaKnow.Droid.Renderers;

[assembly: ExportRenderer(typeof(TextButton), typeof(TextButtonRenderer))]
namespace VocaKnow.Droid.Renderers
{
    public class TextButtonRenderer : ButtonRenderer
    {
        protected override void OnElementChanged(ElementChangedEventArgs<Xamarin.Forms.Button> e)
        {
            /*
            base.OnElementChanged(e);
            if (Control != null)
            {
                Control.Gravity = GravityFlags.Center;
                Control.SetPadding(0, 15, 0, 0);
                Control.SetBackgroundResource(Resource.Drawable.TextButtonEditText);
            }
            */

            base.OnElementChanged(e);

            TextButton skin = (TextButton)Element;
            if (skin != null)
            {
                Control.Gravity = GravityFlags.Center;
                Control.SetPadding(0, 15, 0, 0);

                if(skin.SkinStyle == 1)
                    Control.SetBackgroundResource(Resource.Drawable.TextButtonEditText);
                else if (skin.SkinStyle == 2)
                    Control.SetBackgroundResource(Resource.Drawable.TextButtonEditTextA);
                else if (skin.SkinStyle == 3)
                    Control.SetBackgroundResource(Resource.Drawable.TextButtonEditTextB);
                else if (skin.SkinStyle == 4)
                    Control.SetBackgroundResource(Resource.Drawable.TextButtonEditTextC);
                else if (skin.SkinStyle == 5)
                    Control.SetBackgroundResource(Resource.Drawable.TextButtonEditTextD);
                else if (skin.SkinStyle == 6)
                    Control.SetBackgroundResource(Resource.Drawable.TextButtonEditTextE);
                else if (skin.SkinStyle == 7)
                    Control.SetBackgroundResource(Resource.Drawable.TextButtonEditTextF);               
            }
        }
    }
}