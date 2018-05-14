using System;
using Xamarin.Forms;

namespace VocaKnow.controls
{   
    public class ImageClick : Image
    {
        public event EventHandler Click;

        public ImageClick()
        {
            this.AddTouchHandler(this, this.OnClick);
        }

        private void OnClick()
        {
            Click?.Invoke(this, EventArgs.Empty);
        }

        protected void AddTouchHandler(View view, Action action)
        {
            view.GestureRecognizers.Add(new TapGestureRecognizer
            {
                Command = new Command(() =>
                {
                    view.Opacity = 0.6;
                    view.FadeTo(1);
                    action();
                })
            });
        }

    }
}
