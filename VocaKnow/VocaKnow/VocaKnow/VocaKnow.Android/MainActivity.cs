using Android.App;
using Android.Content.PM;
using Android.OS;
using Android.Views;
using Android.Preferences;
using Android.Content;
//using Android.Graphics;
//using Android.Graphics.Drawables;

namespace VocaKnow.Droid
{
    /*
    [Activity(Label = "VocaKnow", Icon = "@drawable/icon", Theme = "@style/Theme.Splash", MainLauncher = true, NoHistory = true, ConfigurationChanges = ConfigChanges.ScreenSize | ConfigChanges.Orientation, ScreenOrientation = ScreenOrientation.Portrait)]
    public class SplashActivity : Activity
    {
        protected override void OnCreate(Bundle bundle)
        {
            base.OnCreate(bundle);
            StartActivity(typeof(MainActivity));
            Finish();
        }
    }
    */

    [Activity(Label = "VocaKnow", Icon = "@drawable/icon", Theme = "@style/MainTheme", MainLauncher = false, ConfigurationChanges = ConfigChanges.ScreenSize | ConfigChanges.Orientation, WindowSoftInputMode = SoftInput.AdjustPan | SoftInput.AdjustResize)]
    public class MainActivity : global::Xamarin.Forms.Platform.Android.FormsAppCompatActivity
    {
        //private controls.UserSetting Setting = controls.UserSetting.getInstance;

        protected override void OnCreate(Bundle bundle)
        {
            base.OnCreate(bundle);
                        
            global::Xamarin.Forms.Forms.Init(this, bundle);
            
            //Window.SetSoftInputMode(Android.Views.SoftInput.AdjustResize);
           
            var width = Resources.DisplayMetrics.WidthPixels;
            var height = Resources.DisplayMetrics.HeightPixels;
            var density = Resources.DisplayMetrics.Density;
            App.ScreenWidth = (width - 0.5f) / density;
            App.ScreenHeight = (height - 0.5f) / density;

            LoadApplication(new App());

            /*
            // Set the status bar color.
            if (Build.VERSION.SdkInt >= BuildVersionCodes.Lollipop)
            {
                Window.ClearFlags(WindowManagerFlags.TranslucentStatus);
                Window.AddFlags(WindowManagerFlags.DrawsSystemBarBackgrounds);

                string sSkinColor = "";
                int SkinType = Setting.SkinStyle;
                if (SkinType == 1)
                    sSkinColor = "#33A7D6";
                else if (SkinType == 2)
                    sSkinColor = "#493335";
                else if (SkinType == 3)
                    sSkinColor = "#FF80AB";
                else if (SkinType == 4)
                    sSkinColor = "#4CAF50";
                else if (SkinType == 5)
                    sSkinColor = "#3F51B5";
                else if (SkinType == 6)
                    sSkinColor = "#B71C1C";
                else if (SkinType == 7)
                    sSkinColor = "#37474F";

                Window.SetNavigationBarColor(new Android.Graphics.Color(Color.ParseColor(sSkinColor)));
                Window.SetStatusBarColor(new Android.Graphics.Color(Color.ParseColor(sSkinColor)));
                //Window.SetStatusBarColor(new Android.Graphics.Color(255, 255, 255));        
            }          

            //AddShortcut();
            */
        }

        private void AddShortcut()
        {
            ISharedPreferences sharedPreferences = PreferenceManager.GetDefaultSharedPreferences(this);
            var IsShortCut = sharedPreferences.GetBoolean("IsShortCut", false);
            if (!IsShortCut)
            {
                var shortcutIntent = new Intent(this, typeof(SplashActivity));
                shortcutIntent.SetAction(Intent.ActionMain);

                var iconResource = Intent.ShortcutIconResource.FromContext(this, Resource.Drawable.icon);

                var intent = new Intent();
                intent.PutExtra(Intent.ExtraShortcutIntent, shortcutIntent);
                intent.PutExtra(Intent.ExtraShortcutName, "바하사 인도네시아");
                intent.PutExtra(Intent.ExtraShortcutIconResource, iconResource);
                intent.SetAction("com.android.launcher.action.INSTALL_SHORTCUT");
                intent.PutExtra("duplicate", false);
                SendBroadcast(intent);

                //업데이트 설정
                ISharedPreferencesEditor editor = sharedPreferences.Edit();
                editor.PutBoolean("IsShortCut", true);
                editor.Apply();
            }
        }

        private void RemoveShortcut()
        {
            var shortcutIntent = new Intent(this, typeof(SplashActivity));
            shortcutIntent.SetAction(Intent.ActionMain);

            var intent = new Intent();
            intent.PutExtra(Intent.ExtraShortcutIntent, shortcutIntent);
            intent.PutExtra(Intent.ExtraShortcutName, "바하사 인도네시아");
            intent.SetAction("com.android.launcher.action.UNINSTALL_SHORTCUT");
            SendBroadcast(intent);
        }

        //public override void OnBackPressed()
        //{
        //    return;
        //}
    }
}

