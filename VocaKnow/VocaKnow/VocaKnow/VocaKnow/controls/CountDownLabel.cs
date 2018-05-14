﻿using System.Threading.Tasks;
using Xamarin.Forms;

namespace VocaKnow.controls
{
    public class CountDownLabel : ContentView
    {
        private int count;

        private Label countLabel;

        public CountDownLabel()
        {
            countLabel = new Label
            {
                FontSize = Device.GetNamedSize(NamedSize.Large, typeof(Label)) * 8.0,
                HorizontalTextAlignment = TextAlignment.Center,
                VerticalTextAlignment =TextAlignment.Center,
                Opacity = 1,
                TextColor = Color.Orange,//.White,
            };

            Content = countLabel;
            Count = 10;
        }

        public int Count
        {
            get { return count; }
            set
            {
                count = value;
            }
        }

        public async Task CountDown()
        {
            for (int i = count; i > 0; i--)
            {
                countLabel.Scale = 1;
                countLabel.Opacity = 1;
                countLabel.Text = i.ToString();

                countLabel.ScaleTo(0.5, 1000);
                countLabel.FadeTo(0.2, 1000);

                await Task.Delay(1000);
            }
        }
    }

}
