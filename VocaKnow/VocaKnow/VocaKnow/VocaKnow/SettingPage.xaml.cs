using System;
using System.Threading.Tasks;
using Xamarin.Forms;
using Xamarin.Forms.Xaml;

namespace VocaKnow
{
    [XamlCompilation(XamlCompilationOptions.Compile)]
    public partial class SettingPage : ContentPage
    {        

        //public double dHeight;
        //public double dWidth;
        //public controls.LoadingOverlay overlayMsg;
        
        private controls.UserSetting Setting = controls.UserSetting.getInstance;
        private controls.SetScreenLockUnLock Lock = controls.SetScreenLockUnLock.getInstance;
        private controls.AlertMsg Alert = controls.AlertMsg.getInstance;
        
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

            SkinButton(SkinType);
                        
            EnterClose.BackgroundColor = CurSkin;
            iconview.Foreground = CurSkin;

            set3.TintColor = CurSkin;
            set3.ThumbColor = CurSkin;
            
            set4.TintColor = CurSkin;
            set4.ThumbColor = CurSkin;
            
            set5.TintColor = CurSkin;
            set5.ThumbColor = CurSkin;            
        }

        public SettingPage()
        {
            InitializeComponent();

            //스킨 설정
            InitSkinStyle();

            string sVersion = Setting.KataVersion;
            string sPlayNumber = Setting.KataPlayNumber;
            string sKataTime = Setting.KataTime;
            string sWordKataTime = Setting.WordKataTime;
            //int nKataTimeRepeat = Setting.KataTimeRepeat;
            //int nWordKataTimeRepeat = Setting.WordKataTimeRepeat;

            bool bKalimatSoundToggle = false;
            int nKalimatSound = Convert.ToInt32(Setting.KalimatSound);
            if (nKalimatSound == 1)
                bKalimatSoundToggle = true;
            else
                bKalimatSoundToggle = false;

            bool bSoundToggle = false;
            int nKataSound = Convert.ToInt32(Setting.KataSound);
            if (nKataSound == 1)
                bSoundToggle = true;
            else
                bSoundToggle = false;

            bool bScreenLockToggle = false;
            int nScreenLock = Convert.ToInt32(Setting.ScreenLock);
            if (nScreenLock == 1)
                bScreenLockToggle = true;
            else
                bScreenLockToggle = false;

            set1.Text = sKataTime;
            set2.Text = sWordKataTime;
            set3.IsToggled = bKalimatSoundToggle;
            set4.IsToggled = bSoundToggle;
            set5.IsToggled = bScreenLockToggle;
            //set6.Text = string.Format("{0}", nKataTimeRepeat);
            //set7.Text = string.Format("{0}", nWordKataTimeRepeat);
        }

        private void SettingVocknow()
        {
            try
            {
                if (set1.Text != Setting.KataTime)
                {
                    Setting.KataTime = set1.Text;
                }

                if (set2.Text != Setting.WordKataTime)
                {
                    Setting.WordKataTime = set2.Text;
                }
                
                int nSet3 = set3.IsToggled ? 1 : 0;
                if (nSet3 != Convert.ToInt32(Setting.KalimatSound))
                {
                    Setting.KalimatSound = string.Format("{0}", set3.IsToggled ? 1 : 0);
                }
                
                int nSet4 = set4.IsToggled ? 1 : 0;
                if (nSet4 != Convert.ToInt32(Setting.KataSound))
                {
                    Setting.KataSound = string.Format("{0}", set4.IsToggled ? 1 : 0);
                }
                
                int nSet5 = set5.IsToggled ? 1 : 0;
                if (nSet5 != Convert.ToInt32(Setting.ScreenLock))
                {
                    Setting.ScreenLock = string.Format("{0}", set5.IsToggled ? 1 : 0);
                    Lock.SetLockUnLock(set5.IsToggled);
                }
                
                //함수에 스트링 전달
                MessagingCenter.Send(MainPage.GetMainPageInstance(), "ChangeSetting");

            } catch { }
        }

        private async void EnterClose_Click(object sender, EventArgs e)
        {
            try
            {                
                Device.BeginInvokeOnMainThread(() =>
                {
                    //overlayMsg = new controls.LoadingOverlay(fMain, "잠시만 기다려주세요.", (int)dWidth, (int)dHeight);
                    //overlayMsg.ShowOverlay(this);
                    //fMain.WidthRequest = dWidth;
                    //fMain.HeightRequest = dHeight;

                    Setting.KataTime = set1.Text;
                    Setting.WordKataTime = set2.Text;
                    Setting.KalimatSound = string.Format("{0}", set3.IsToggled ? 1 : 0);
                    Setting.KataSound = string.Format("{0}", set4.IsToggled ? 1 : 0);
                    Setting.ScreenLock = string.Format("{0}", set5.IsToggled ? 1 : 0);
                    Lock.SetLockUnLock(set5.IsToggled);

                    //Setting.KataTimeRepeat = Convert.ToInt32(set6.Text);
                    //Setting.WordKataTimeRepeat = Convert.ToInt32(set7.Text);
                    
                    //함수에 스트링 전달
                    MessagingCenter.Send(MainPage.GetMainPageInstance(), "ChangeSetting");
                    
                });

                await Navigation.PopModalAsync();
               
            } catch { }
        }

        private async void TapGestureRecognizerInfo_Tapped(object sender, EventArgs e)
        {
            await Navigation.PushModalAsync(new UserGuidePage(), false);//await Navigation.PushModalAsync(new IntroPage());
        }

        private void TapGestureRecognizerF1_Tapped(object sender, EventArgs e)
        {
            Setting.SkinStyle = 1;
            InitSkinStyle();
            MessagingCenter.Send(MainPage.GetMainPageInstance(), "SkinStyle");
        }

        private void TapGestureRecognizerF2_Tapped(object sender, EventArgs e)
        {
            Setting.SkinStyle = 2;
            InitSkinStyle();
            MessagingCenter.Send(MainPage.GetMainPageInstance(), "SkinStyle");
        }

        private void TapGestureRecognizerF3_Tapped(object sender, EventArgs e)
        {
            Setting.SkinStyle = 3;
            InitSkinStyle();
            MessagingCenter.Send(MainPage.GetMainPageInstance(), "SkinStyle");
        }

        private void TapGestureRecognizerF4_Tapped(object sender, EventArgs e)
        {
            Setting.SkinStyle = 4;
            InitSkinStyle();
            MessagingCenter.Send(MainPage.GetMainPageInstance(), "SkinStyle");
        }

        private void TapGestureRecognizerF5_Tapped(object sender, EventArgs e)
        {
            Setting.SkinStyle = 5;
            InitSkinStyle();
            MessagingCenter.Send(MainPage.GetMainPageInstance(), "SkinStyle");
        }

        private void TapGestureRecognizerF6_Tapped(object sender, EventArgs e)
        {
            Setting.SkinStyle = 6;
            InitSkinStyle();
            MessagingCenter.Send(MainPage.GetMainPageInstance(), "SkinStyle");
        }

        private void TapGestureRecognizerF7_Tapped(object sender, EventArgs e)
        {
            Setting.SkinStyle = 7;
            InitSkinStyle();
            MessagingCenter.Send(MainPage.GetMainPageInstance(), "SkinStyle");
        }               

        private void SkinButton(int SkinStyle)
        {
            lblSkin.TextColor = CurSkin;

            if (SkinStyle == 1)
            {
                f1.BackgroundColor = Color.Orange; 
                f1.Opacity = 0.8;

                f2.BackgroundColor = Color.Transparent;
                f2.Opacity = 1;

                f3.BackgroundColor = Color.Transparent;
                f3.Opacity = 1;

                f4.BackgroundColor = Color.Transparent;
                f4.Opacity = 1;

                f5.BackgroundColor = Color.Transparent;
                f5.Opacity = 1;

                f6.BackgroundColor = Color.Transparent;
                f6.Opacity = 1;

                f7.BackgroundColor = Color.Transparent;
                f7.Opacity = 1;
            }
            else if (SkinStyle == 2)
            {
                f1.BackgroundColor = Color.Transparent;
                f1.Opacity = 1;

                f2.BackgroundColor = Color.Orange;
                f2.Opacity = 0.8;

                f3.BackgroundColor = Color.Transparent;
                f3.Opacity = 1;

                f4.BackgroundColor = Color.Transparent;
                f4.Opacity = 1;

                f5.BackgroundColor = Color.Transparent;
                f5.Opacity = 1;

                f6.BackgroundColor = Color.Transparent;
                f6.Opacity = 1;

                f7.BackgroundColor = Color.Transparent;
                f7.Opacity = 1;
            }
            else if (SkinStyle == 3)
            {
                f1.BackgroundColor = Color.Transparent;
                f1.Opacity = 1;

                f2.BackgroundColor = Color.Transparent;
                f2.Opacity = 1;

                f3.BackgroundColor = Color.Orange;
                f3.Opacity = 0.8;

                f4.BackgroundColor = Color.Transparent;
                f4.Opacity = 1;

                f5.BackgroundColor = Color.Transparent;
                f5.Opacity = 1;

                f6.BackgroundColor = Color.Transparent;
                f6.Opacity = 1;

                f7.BackgroundColor = Color.Transparent;
                f7.Opacity = 1;
            }
            else if (SkinStyle == 4)
            {
                f1.BackgroundColor = Color.Transparent;
                f1.Opacity = 1;

                f2.BackgroundColor = Color.Transparent;
                f2.Opacity = 1;

                f3.BackgroundColor = Color.Transparent;
                f3.Opacity = 1;

                f4.BackgroundColor = Color.Orange;
                f4.Opacity = 0.8;

                f5.BackgroundColor = Color.Transparent;
                f5.Opacity = 1;

                f6.BackgroundColor = Color.Transparent;
                f6.Opacity = 1;

                f7.BackgroundColor = Color.Transparent;
                f7.Opacity = 1;
            }
            else if (SkinStyle == 5)
            {
                f1.BackgroundColor = Color.Transparent;
                f1.Opacity = 1;

                f2.BackgroundColor = Color.Transparent;
                f2.Opacity = 1;

                f3.BackgroundColor = Color.Transparent;
                f3.Opacity = 1;

                f4.BackgroundColor = Color.Transparent;
                f4.Opacity = 1;

                f5.BackgroundColor = Color.Orange;
                f5.Opacity = 0.8;

                f6.BackgroundColor = Color.Transparent;
                f6.Opacity = 1;

                f7.BackgroundColor = Color.Transparent;
                f7.Opacity = 1;
            }
            else if (SkinStyle == 6)
            {
                f1.BackgroundColor = Color.Transparent;
                f1.Opacity = 1;

                f2.BackgroundColor = Color.Transparent;
                f2.Opacity = 1;

                f3.BackgroundColor = Color.Transparent;
                f3.Opacity = 1;

                f4.BackgroundColor = Color.Transparent;
                f4.Opacity = 1;

                f5.BackgroundColor = Color.Transparent;
                f5.Opacity = 1;

                f6.BackgroundColor = Color.Orange;
                f6.Opacity = 0.8;

                f7.BackgroundColor = Color.Transparent;
                f7.Opacity = 1;
            }
            else if (SkinStyle == 7)
            {
                f1.BackgroundColor = Color.Transparent;
                f1.Opacity = 1;

                f2.BackgroundColor = Color.Transparent;
                f2.Opacity = 1;

                f3.BackgroundColor = Color.Transparent;
                f3.Opacity = 1;

                f4.BackgroundColor = Color.Transparent;
                f4.Opacity = 1;

                f5.BackgroundColor = Color.Transparent;
                f5.Opacity = 1;

                f6.BackgroundColor = Color.Transparent;
                f6.Opacity = 1;

                f7.BackgroundColor = Color.Orange;
                f7.Opacity = 0.8;
            }
        }

        protected override void OnAppearing()
        {
            base.OnAppearing();
        }

        protected override void OnSizeAllocated(double width, double height)
        {
            //dWidth = width;
            //dHeight = height;

            base.OnSizeAllocated(width, height);
        }

        protected override void OnDisappearing()
        {
            //try
            //{
            //    if (overlayMsg != null)
            //        overlayMsg.HideOverlay(this);
            //}catch { }

            base.OnDisappearing();
        }

        protected override bool OnBackButtonPressed()
        {           
            //함수에 스트링 전달
            MessagingCenter.Send(MainPage.GetMainPageInstance(), "ChangeSetting");

            return base.OnBackButtonPressed();
        }

    }

}