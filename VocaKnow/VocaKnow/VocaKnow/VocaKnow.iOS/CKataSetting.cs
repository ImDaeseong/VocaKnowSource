using System;
using NUnit.Framework;
using Xamarin.Forms;
using VocaKnow.iOS;
using VocaKnow.Interfaces;
using Foundation;

[assembly: Dependency(typeof(CKataSetting))]
namespace VocaKnow.iOS
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

        public  string KataVersion
        {
            get
            {
                return NSUserDefaults.StandardUserDefaults.StringForKey(SETTINGSKEY_KATAVERSION);
            }
            set
            {
                NSUserDefaults.StandardUserDefaults.SetString(value, SETTINGSKEY_KATAVERSION);
                NSUserDefaults.StandardUserDefaults.Synchronize();
            }
        }

        public string KataPlayNumber
        {
            get
            {
                return NSUserDefaults.StandardUserDefaults.StringForKey(SETTINGSKEY_KATAPLAYNUMBER);
            }
            set
            {
                NSUserDefaults.StandardUserDefaults.SetString(value, SETTINGSKEY_KATAPLAYNUMBER);
                NSUserDefaults.StandardUserDefaults.Synchronize();
            }
        }
        public string KataTime
        {
            get
            {
                return NSUserDefaults.StandardUserDefaults.StringForKey(SETTINGSKEY_KATATIME);
            }
            set
            {
                NSUserDefaults.StandardUserDefaults.SetString(value, SETTINGSKEY_KATATIME);
                NSUserDefaults.StandardUserDefaults.Synchronize();
            }
        }

        public string WordKataTime
        {
            get
            {
                return NSUserDefaults.StandardUserDefaults.StringForKey(SETTINGSKEY_WORDKATATIME);
            }
            set
            {
                NSUserDefaults.StandardUserDefaults.SetString(value, SETTINGSKEY_WORDKATATIME);
                NSUserDefaults.StandardUserDefaults.Synchronize();
            }
        }

        public string KalimatSound
        {
            get
            {
                return NSUserDefaults.StandardUserDefaults.StringForKey(SETTINGSKEY_KALIMATSOUND);
            }
            set
            {
                NSUserDefaults.StandardUserDefaults.SetString(value, SETTINGSKEY_KALIMATSOUND);
                NSUserDefaults.StandardUserDefaults.Synchronize();
            }
        }

        public string KataSound
        {
            get
            {
                return NSUserDefaults.StandardUserDefaults.StringForKey(SETTINGSKEY_KATASOUND);
            }
            set
            {
                NSUserDefaults.StandardUserDefaults.SetString(value, SETTINGSKEY_KATASOUND);
                NSUserDefaults.StandardUserDefaults.Synchronize();
            }
        }

        public string ScreenLock
        {
            get
            {
                return NSUserDefaults.StandardUserDefaults.StringForKey(SETTINGSKEY_SCREENLOCK);
            }
            set
            {
                NSUserDefaults.StandardUserDefaults.SetString(value, SETTINGSKEY_SCREENLOCK);
                NSUserDefaults.StandardUserDefaults.Synchronize();
            }
        }

        public string UpDateTime
        {
            get
            {
                return NSUserDefaults.StandardUserDefaults.StringForKey(SETTINGSKEY_ADVUPDATETIME);
            }
            set
            {
                NSUserDefaults.StandardUserDefaults.SetString(value, SETTINGSKEY_ADVUPDATETIME);
                NSUserDefaults.StandardUserDefaults.Synchronize();
            }
        }

        public bool IntroPopup
        {
            get
            {
                return NSUserDefaults.StandardUserDefaults.BoolForKey(SETTINGSKEY_INTROPOPUP);
            }
            set
            {
                NSUserDefaults.StandardUserDefaults.SetBool(value, SETTINGSKEY_INTROPOPUP);
                NSUserDefaults.StandardUserDefaults.Synchronize();
            }
        }

        public int SkinStyle
        {
            get
            {
                return (int)NSUserDefaults.StandardUserDefaults.IntForKey(SETTINGSKEY_SKINSTYLE);
            }
            set
            {
                NSUserDefaults.StandardUserDefaults.SetInt(value, SETTINGSKEY_SKINSTYLE);
                NSUserDefaults.StandardUserDefaults.Synchronize();
            }
        }

        public int KataTimeRepeat
        {
            get
            {
                return (int)NSUserDefaults.StandardUserDefaults.IntForKey(SETTINGSKEY_KATATIMEREPEAT);
            }
            set
            {
                NSUserDefaults.StandardUserDefaults.SetInt(value, SETTINGSKEY_KATATIMEREPEAT);
                NSUserDefaults.StandardUserDefaults.Synchronize();
            }
        }

        public int WordKataTimeRepeat
        {
            get
            {
                return (int)NSUserDefaults.StandardUserDefaults.IntForKey(SETTINGSKEY_WORDKATATIMEREPEAT);
            }
            set
            {
                NSUserDefaults.StandardUserDefaults.SetInt(value, SETTINGSKEY_WORDKATATIMEREPEAT);
                NSUserDefaults.StandardUserDefaults.Synchronize();
            }
        }

        public void DeleteKataSetting(string sKey)
        {
            NSUserDefaults.StandardUserDefaults.RemoveObject(sKey);
        }

        public CKataSetting()
        {
        }
    }
}