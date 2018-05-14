using Xamarin.Forms;

namespace VocaKnow.controls
{
    public class EmptyLabel : Label
    {
        public EmptyLabel()
        {
            TextColor = Color.White;
            Text = "";
            HorizontalOptions = LayoutOptions.Center;
            HorizontalTextAlignment = TextAlignment.Center;
            VerticalTextAlignment = TextAlignment.Center;
            FontSize = Device.GetNamedSize(NamedSize.Small, typeof(Label));//FontSize = Device.GetNamedSize(NamedSize.Large, typeof(Label));
            AbsoluteLayout.SetLayoutFlags(this, AbsoluteLayoutFlags.PositionProportional);
            AbsoluteLayout.SetLayoutBounds(this, new Rectangle(.5, .5, 400, 10));//AbsoluteLayout.SetLayoutBounds(this, new Rectangle(.5, .5, 400, 300));
        }
    }
}
