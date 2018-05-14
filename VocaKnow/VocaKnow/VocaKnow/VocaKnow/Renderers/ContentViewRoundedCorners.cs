using Xamarin.Forms;

namespace VocaKnow.Renderers
{
    public class ContentViewRoundedCorners : ContentView
    {
        public static readonly BindableProperty CornerRadiusProperty = BindableProperty.Create("CornerRadius", typeof(float), typeof(ContentViewRoundedCorners), default(float));

        public float CornerRadius
        {
            get { return (float)GetValue(CornerRadiusProperty); }
            set { SetValue(CornerRadiusProperty, value); }
        }
    }
}
