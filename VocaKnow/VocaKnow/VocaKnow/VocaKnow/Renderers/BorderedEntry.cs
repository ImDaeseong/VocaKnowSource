using Xamarin.Forms;

namespace VocaKnow.Renderers
{
    public class BorderedEntry : Entry
    {
        public static readonly BindableProperty BorderColorProperty = BindableProperty.Create(nameof(BorderColor), typeof(Color), typeof(Entry), Color.Default);
        public Color BorderColor
        {
            get { return (Color)GetValue(BorderColorProperty); }
            set { SetValue(BorderColorProperty, value); }
        }

        public static readonly BindableProperty BorderColorActiveProperty = BindableProperty.Create(nameof(BorderColorActive), typeof(Color), typeof(Entry), Color.Default);
        public Color BorderColorActive
        {
            get { return (Color)GetValue(BorderColorActiveProperty); }
            set { SetValue(BorderColorActiveProperty, value); }
        }

        public static readonly BindableProperty BorderHeightProperty = BindableProperty.Create(nameof(BorderHeight), typeof(double), typeof(Entry), 1.0);
        public double BorderHeight
        {
            get { return (double)GetValue(BorderHeightProperty); }
            set { SetValue(BorderHeightProperty, value); }
        }

        public static readonly BindableProperty HasBorderProperty = BindableProperty.Create(nameof(HasBorder), typeof(bool), typeof(Entry), false);
        public bool HasBorder
        {
            get { return (bool)GetValue(HasBorderProperty); }
            set { SetValue(HasBorderProperty, value); }
        }
    }
}
