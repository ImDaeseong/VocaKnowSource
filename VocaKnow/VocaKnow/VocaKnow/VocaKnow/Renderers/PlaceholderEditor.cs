using Xamarin.Forms;

namespace VocaKnow.Renderers
{
    public class PlaceholderEditor : Editor
    {
        public static readonly BindableProperty PlaceholderProperty = BindableProperty.Create<PlaceholderEditor, string>(p => p.Placeholder, "");
        public string Placeholder
        {
            get { return (string)base.GetValue(PlaceholderProperty); }
            set { base.SetValue(PlaceholderProperty, value); }
        }

        public static readonly BindableProperty PlaceholderColorProperty = BindableProperty.Create<PlaceholderEditor, Color>(p => p.PlaceholderColor, Color.Gray);
        public Color PlaceholderColor
        {
            get { return (Color)base.GetValue(PlaceholderColorProperty); }
            set { base.SetValue(PlaceholderColorProperty, value); }
        }
    }
}
