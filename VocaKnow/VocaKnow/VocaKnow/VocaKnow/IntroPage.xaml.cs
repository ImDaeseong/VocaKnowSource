using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

using Xamarin.Forms;
using Xamarin.Forms.Xaml;

namespace VocaKnow
{
    [XamlCompilation(XamlCompilationOptions.Compile)]
    public partial class IntroPage : CarouselPage
    {
        private controls.UserSetting Setting = controls.UserSetting.getInstance;

        private int SkinType;
        private Color CurSkin;
        private void InitSkinStyle()
        {
            SkinType = Setting.SkinStyle;

            if (SkinType == 1)
                CurSkin = Color.FromHex("#33A7D6");
            else if (SkinType == 2)
                CurSkin = Color.FromHex("#493335");
            else if (SkinType == 3)
                CurSkin = Color.FromHex("#FF80AB");
            else if (SkinType == 4)
                CurSkin = Color.FromHex("#4CAF50");
            else if (SkinType == 5)
                CurSkin = Color.FromHex("#3F51B5");
            else if (SkinType == 6)
                CurSkin = Color.FromHex("#B71C1C");
            else if (SkinType == 7)
                CurSkin = Color.FromHex("#37474F");
            
            page1.BackgroundColor = CurSkin;
            page2.BackgroundColor = CurSkin;
            page3.BackgroundColor = CurSkin;
            page4.BackgroundColor = CurSkin;
            page5.BackgroundColor = CurSkin;
            page6.BackgroundColor = CurSkin;
            page7.BackgroundColor = CurSkin;
        }

        public IntroPage()
        {
            InitializeComponent();

            InitSkinStyle();
        }

        private void next1_Clicked(object sender, EventArgs e)
        {
            CurrentPage = page2;
        }

        private void next2_Clicked(object sender, EventArgs e)
        {
            CurrentPage = page3;
        }

        private void next3_Clicked(object sender, EventArgs e)
        {
            CurrentPage = page4;
        }

        private void next4_Clicked(object sender, EventArgs e)
        {
            CurrentPage = page5;
        }

        private void next5_Clicked(object sender, EventArgs e)
        {
            CurrentPage = page6;
        }

        private void next6_Clicked(object sender, EventArgs e)
        {
            CurrentPage = page7;
        }

        private async void next7_Clicked(object sender, EventArgs e)
        {
            try
            {
                Setting.IntroPopup = true;
                await Task.Delay(500);
                await Navigation.PopModalAsync();
            }
            catch { }
        }

        protected override void OnAppearing()
        {
            base.OnAppearing();

        }

        protected override void OnDisappearing()
        {
            base.OnDisappearing();
            
            try
            {
                Setting.IntroPopup = true;
            }catch { }
        }

        protected override bool OnBackButtonPressed()
        {
            /*
            try
            {
                Setting.IntroPopup = true;
            }catch { }
            */
            return base.OnBackButtonPressed();
        }

    }
}