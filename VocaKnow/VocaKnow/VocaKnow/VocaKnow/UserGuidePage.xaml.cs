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
    public partial class UserGuidePage : ContentPage
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

            this.BackgroundColor = CurSkin;
        }

        public UserGuidePage()
        {
            InitializeComponent();

            InitSkinStyle();


            var images = new[] {
              new FileImageSource  { File  = "intro1.png" },
              new FileImageSource  { File  = "intro2.png" },
              new FileImageSource  { File  = "intro3.png" },
              new FileImageSource  { File  = "intro4.png" },
              new FileImageSource  { File  = "intro5.png" },
              new FileImageSource  { File  = "intro6.png" },
              new FileImageSource  { File  = "intro7.png" }
            };

            carouselImg.Images = images;
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
            }
            catch { }
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

        private async void EnterClose_Click(object sender, EventArgs e)
        {
            try
            {
                Setting.IntroPopup = true;
                await Task.Delay(500);
                await Navigation.PopModalAsync();
            }
            catch { }
        }
       
    }
}