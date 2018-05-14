using System;
using System.Threading.Tasks;
using Xamarin.Forms;

namespace VocaKnow
{
    public partial class App : Application
    {
        private controls.UserSetting Setting = controls.UserSetting.getInstance;
        private controls.SetScreenLockUnLock Lock = controls.SetScreenLockUnLock.getInstance;
        private controls.KataLog log = controls.KataLog.getInstance;
        private controls.ToSpeak TextToSpeech = controls.ToSpeak.getInstance;

        public static void WriteLogString(string sText)
        {
            DependencyService.Get<VocaKnow.Interfaces.ILogFile >().WriteString(sText);
        }

        private void SetLock()
        {
            bool bToggle = false;
            int nScreenLock = Convert.ToInt32(Setting.ScreenLock);
            if (nScreenLock == 1)
                bToggle = true;
            else
                bToggle = false;
            Lock.SetLockUnLock(bToggle);
        }


        public static async Task ShowMessage(string sTitle, string sMessage)
        {
            await Xamarin.Forms.Application.Current.MainPage.DisplayAlert(sTitle, sMessage, "Ok");
        }

        public static double ScreenHeight;
        public static double ScreenWidth;

        //static bool bSplah = false;

        public static int PlayNumberIndex;
        

        public App()
        {
            InitializeComponent();

            MainPage = new NavigationPage(new MainPage());

            /*
            //Background color
            MainPage.SetValue(NavigationPage.BarBackgroundColorProperty, Color.Orange);

            //Title color
            MainPage.SetValue(NavigationPage.BarTextColorProperty, Color.White);
            */

            //음성 초기화
            TextToSpeech.InitSpeak();

            /*
            if (bSplah)
            {
                MainPage = new NavigationPage(new MainPage());
            }
            else
            {
                bSplah = true;
                MainPage = new NavigationPage(new Splash());
            }
            */
        }
        
        protected override void OnStart()
        {
            SetLock();
            // Handle when your app starts
        }

        protected override void OnSleep()
        {
            // Handle when your app sleeps
        }

        protected override void OnResume()
        {
            // Handle when your app resumes
        }        
        
    }
}
