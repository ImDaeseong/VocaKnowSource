using System;
using UIKit;
using Xamarin.Forms;
using Xamarin.Forms.Platform.iOS;
using VocaKnow.iOS;
using VocaKnow.Interfaces;

[assembly: Dependency(typeof(CSetStatusBarStyle))]
namespace VocaKnow.iOS
{
    public class CSetStatusBarStyle : ISetStatusBarStyle
    {
        public CSetStatusBarStyle()
        {
        }

        public void ChangeStatusBarColor(Color color)
        {
            UINavigationBar.Appearance.BarTintColor = color.ToUIColor(); //UIColor.FromRGB(0, 123, 233);
            UINavigationBar.Appearance.TintColor = UIColor.White;
            UINavigationBar.Appearance.SetTitleTextAttributes(new UITextAttributes()
            {
                TextColor = UIColor.White
            });
        }

        private static UIColor ConvertFromString(string HexColor)
        {
            HexColor = HexColor.Replace("#", "");
            int r = Convert.ToInt32(HexColor.Substring(0, 2), 16);
            int g = Convert.ToInt32(HexColor.Substring(2, 2), 16);
            int b = Convert.ToInt32(HexColor.Substring(4, 2), 16);
            return UIColor.FromRGB(r, g, b);
        }

    }

}