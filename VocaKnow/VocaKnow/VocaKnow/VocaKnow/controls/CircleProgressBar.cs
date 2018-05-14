using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Xamarin.Forms;

namespace VocaKnow.controls
{
    public class CircleProgressBar : Image
    {
        public CircleProgressBar()
        {
            Source = "wait.png";
            HorizontalOptions = LayoutOptions.Center;
            VerticalOptions = LayoutOptions.Center;
            Device.StartTimer(TimeSpan.FromMilliseconds(10), Callback);
        }

        private bool Callback()
        {
            Rotation = (Rotation + 1) % 360;
            return IsVisible;
        }
    }
}
