using Xamarin.Forms;

namespace VocaKnow.Renderers
{
    public class TextButton : Button
    {
        public static readonly BindableProperty SkinProperty = BindableProperty.Create(nameof(SkinStyle), typeof(int), typeof(TextButton), 1);

        public int SkinStyle
        {
            get { return (int)GetValue(SkinProperty); }
            set { SetValue(SkinProperty, value); }
        }

    }
}
