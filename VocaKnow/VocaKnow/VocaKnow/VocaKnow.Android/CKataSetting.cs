using Android.App;
using Android.Content;
using Xamarin.Forms;
using VocaKnow.Droid;
using VocaKnow.Interfaces;

[assembly: Dependency(typeof(CKataSetting))]
namespace VocaKnow.Droid
{
    public class CKataSetting : IKataSetting
    {
        const string SETTINGSKEY_KATAVERSION = "KataVersion";
        const string SETTINGSKEY_KATAPLAYNUMBER = "KataPlayNumber";
        const string SETTINGSKEY_KATATIME = "KataTime";
        const string SETTINGSKEY_WORDKATATIME = "WordKataTime";
        const string SETTINGSKEY_KALIMATSOUND = "KalimatSound";
        const string SETTINGSKEY_KATASOUND = "kataSound";
        const string SETTINGSKEY_SCREENLOCK = "ScreenLock";
        const string SETTINGSKEY_ADVUPDATETIME = "AdvUpdateTime";
        const string SETTINGSKEY_INTROPOPUP = "Intro";
        const string SETTINGSKEY_SKINSTYLE = "SkinStyle";
        const string SETTINGSKEY_KATATIMEREPEAT = "KataTimeRepeat";
        const string SETTINGSKEY_WORDKATATIMEREPEAT = "WordKataTimeRepeat";

        public string KataVersion
        {
            get
            {
                var prefs = Android.App.Application.Context.GetSharedPreferences("MySharedPrefs", FileCreationMode.Private);
                return prefs.GetString(SETTINGSKEY_KATAVERSION, "");
            }
            set
            {
                var prefs = Android.App.Application.Context.GetSharedPreferences("MySharedPrefs", FileCreationMode.Private);
                var prefsEditor = prefs.Edit();
                prefsEditor.PutString(SETTINGSKEY_KATAVERSION, value);
                prefsEditor.Commit();
            }
        }

        public string KataPlayNumber
        {
            get
            {
                var prefs = Android.App.Application.Context.GetSharedPreferences("MySharedPrefs", FileCreationMode.Private);
                return prefs.GetString(SETTINGSKEY_KATAPLAYNUMBER, "");
            }
            set
            {
                var prefs = Android.App.Application.Context.GetSharedPreferences("MySharedPrefs", FileCreationMode.Private);
                var prefsEditor = prefs.Edit();
                prefsEditor.PutString(SETTINGSKEY_KATAPLAYNUMBER, value);
                prefsEditor.Commit();
            }
        }
        public string KataTime
        {
            get
            {
                var prefs = Android.App.Application.Context.GetSharedPreferences("MySharedPrefs", FileCreationMode.Private);
                return prefs.GetString(SETTINGSKEY_KATATIME, "");
            }
            set
            {
                var prefs = Android.App.Application.Context.GetSharedPreferences("MySharedPrefs", FileCreationMode.Private);
                var prefsEditor = prefs.Edit();
                prefsEditor.PutString(SETTINGSKEY_KATATIME, value);
                prefsEditor.Commit();
            }            
        }
                
        public string WordKataTime
        {
            get
            {
                var prefs = Android.App.Application.Context.GetSharedPreferences("MySharedPrefs", FileCreationMode.Private);
                return prefs.GetString(SETTINGSKEY_WORDKATATIME, "");
            }
            set
            {
                var prefs = Android.App.Application.Context.GetSharedPreferences("MySharedPrefs", FileCreationMode.Private);
                var prefsEditor = prefs.Edit();
                prefsEditor.PutString(SETTINGSKEY_WORDKATATIME, value);
                prefsEditor.Commit();
            }
        }
        
        public string KalimatSound
        {
            get
            {
                var prefs = Android.App.Application.Context.GetSharedPreferences("MySharedPrefs", FileCreationMode.Private);
                return prefs.GetString(SETTINGSKEY_KALIMATSOUND, "");
            }
            set
            {
                var prefs = Android.App.Application.Context.GetSharedPreferences("MySharedPrefs", FileCreationMode.Private);
                var prefsEditor = prefs.Edit();
                prefsEditor.PutString(SETTINGSKEY_KALIMATSOUND, value);
                prefsEditor.Commit();
            }
        }
        
        public string KataSound
        {
            get
            {
                var prefs = Android.App.Application.Context.GetSharedPreferences("MySharedPrefs", FileCreationMode.Private);
                return prefs.GetString(SETTINGSKEY_KATASOUND, "");
            }
            set
            {
                var prefs = Android.App.Application.Context.GetSharedPreferences("MySharedPrefs", FileCreationMode.Private);
                var prefsEditor = prefs.Edit();
                prefsEditor.PutString(SETTINGSKEY_KATASOUND, value);
                prefsEditor.Commit();
            }
        }

        public string ScreenLock
        {
            get
            {
                var prefs = Android.App.Application.Context.GetSharedPreferences("MySharedPrefs", FileCreationMode.Private);
                return prefs.GetString(SETTINGSKEY_SCREENLOCK, "");
            }
            set
            {
                var prefs = Android.App.Application.Context.GetSharedPreferences("MySharedPrefs", FileCreationMode.Private);
                var prefsEditor = prefs.Edit();
                prefsEditor.PutString(SETTINGSKEY_SCREENLOCK, value);
                prefsEditor.Commit();
            }
        }

        public string UpDateTime
        {
            get
            {
                var prefs = Android.App.Application.Context.GetSharedPreferences("MySharedPrefs", FileCreationMode.Private);
                return prefs.GetString(SETTINGSKEY_ADVUPDATETIME, "");
            }
            set
            {
                var prefs = Android.App.Application.Context.GetSharedPreferences("MySharedPrefs", FileCreationMode.Private);
                var prefsEditor = prefs.Edit();
                prefsEditor.PutString(SETTINGSKEY_ADVUPDATETIME, value);
                prefsEditor.Commit();
            }
        }

        public bool IntroPopup
        {
            get
            {
                var prefs = Android.App.Application.Context.GetSharedPreferences("MySharedPrefs", FileCreationMode.Private);
                return prefs.GetBoolean(SETTINGSKEY_INTROPOPUP, false);
            }
            set
            {
                var prefs = Android.App.Application.Context.GetSharedPreferences("MySharedPrefs", FileCreationMode.Private);
                var prefsEditor = prefs.Edit();
                prefsEditor.PutBoolean(SETTINGSKEY_INTROPOPUP, value);
                prefsEditor.Commit();
            }
        }

        public int SkinStyle
        {
            get
            {
                var prefs = Android.App.Application.Context.GetSharedPreferences("MySharedPrefs", FileCreationMode.Private);
                return prefs.GetInt(SETTINGSKEY_SKINSTYLE, 1);
            }
            set
            {
                var prefs = Android.App.Application.Context.GetSharedPreferences("MySharedPrefs", FileCreationMode.Private);
                var prefsEditor = prefs.Edit();
                prefsEditor.PutInt(SETTINGSKEY_SKINSTYLE, value);
                prefsEditor.Commit();
            }
        }
                
        public int KataTimeRepeat
        {
            get
            {
                var prefs = Android.App.Application.Context.GetSharedPreferences("MySharedPrefs", FileCreationMode.Private);
                return prefs.GetInt(SETTINGSKEY_KATATIMEREPEAT, 1);
            }
            set
            {
                var prefs = Android.App.Application.Context.GetSharedPreferences("MySharedPrefs", FileCreationMode.Private);
                var prefsEditor = prefs.Edit();
                prefsEditor.PutInt(SETTINGSKEY_KATATIMEREPEAT, value);
                prefsEditor.Commit();
            }
        }

        public int WordKataTimeRepeat
        {
            get
            {
                var prefs = Android.App.Application.Context.GetSharedPreferences("MySharedPrefs", FileCreationMode.Private);
                return prefs.GetInt(SETTINGSKEY_WORDKATATIMEREPEAT, 1);
            }
            set
            {
                var prefs = Android.App.Application.Context.GetSharedPreferences("MySharedPrefs", FileCreationMode.Private);
                var prefsEditor = prefs.Edit();
                prefsEditor.PutInt(SETTINGSKEY_WORDKATATIMEREPEAT, value);
                prefsEditor.Commit();
            }
        }
        
        public void DeleteKataSetting(string sKey)
        {
            var prefs = Android.App.Application.Context.GetSharedPreferences("MySharedPrefs", FileCreationMode.Private);
            prefs.Edit().Remove(sKey).Commit();
        }
        
        public CKataSetting()
        {
        }
    }
}