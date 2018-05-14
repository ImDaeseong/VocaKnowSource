using Xamarin.Forms;
using Xamarin.Forms.Platform.Android;
using Android.Graphics.Drawables;
using Android.Graphics.Drawables.Shapes;
using Android.Graphics;
using VocaKnow.Renderers;
using VocaKnow.Droid.Renderers;

[assembly: ExportRenderer(typeof(PlaceholderEditor), typeof(PlaceholderEditorRenderer))]
namespace VocaKnow.Droid.Renderers
{
    public class PlaceholderEditorRenderer : EditorRenderer
    {
        protected override void OnElementChanged(ElementChangedEventArgs<Editor> e)
        {
            base.OnElementChanged(e);

            if (e.NewElement != null)
            {
                PlaceholderEditor entity = e.NewElement as PlaceholderEditor;
                this.Control.Hint = entity.Placeholder;
                this.Control.SetHintTextColor(entity.PlaceholderColor.ToAndroid());

                ShapeDrawable shape = new ShapeDrawable(new RectShape());
                shape.Paint.Color = Xamarin.Forms.Color.Transparent.ToAndroid();
                shape.Paint.StrokeWidth = 5;
                shape.Paint.SetStyle(Paint.Style.Stroke);
                this.Control.SetBackground(shape);
            }
        }
    }
}