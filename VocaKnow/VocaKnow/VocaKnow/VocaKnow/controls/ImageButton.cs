using System;
using System.Threading.Tasks;
using Xamarin.Forms;

namespace VocaKnow.controls
{
    public class ImageButton : StackLayout
    {
        public event EventHandler Click;
        private void OnClick()
        {
            Click?.Invoke(this, EventArgs.Empty);
        }

        private Image img = new Image();
        public ImageSource Source
        {
            set { img.Source = value; }
        }

        public ImageButton()
        {
            this.GestureRecognizers.Add(new TapGestureRecognizer
            {
                Command = new Command(async () => await ImageButtonClick()),
            });
            this.Children.Add(img);
            this.Scale = .95;
        }

        private async Task ImageButtonClick()
        {
            await this.ScaleTo(0.9, 75, Easing.CubicOut);

            //this.OnClick();

            await this.ScaleTo(1, 75, Easing.CubicIn);
        }

    }
}
