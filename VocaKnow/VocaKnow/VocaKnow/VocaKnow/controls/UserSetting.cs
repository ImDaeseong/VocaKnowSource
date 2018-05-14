using VocaKnow.Interfaces;
using Xamarin.Forms;

namespace VocaKnow.controls
{
    public class UserSetting
    {
        private static UserSetting selfInstance = null;
        public static UserSetting getInstance
        {
            get
            {
                if (selfInstance == null) selfInstance = new UserSetting();
                return selfInstance;
            }
        }

        static IKataSetting LocalSettings
        {
            get
            {
                return DependencyService.Get<IKataSetting>();
            }
        }

        public string KataVersion
        {
            get
            {
                if (LocalSettings.KataVersion == "")
                    LocalSettings.KataVersion = "20170801";

                return LocalSettings.KataVersion;
            }
            set
            {
                LocalSettings.KataVersion = value;
            }
        }

        public string KataPlayNumber
        {
            get
            {
                if (LocalSettings.KataPlayNumber == "")
                    LocalSettings.KataPlayNumber = "0";

                return LocalSettings.KataPlayNumber;
            }
            set
            {
                LocalSettings.KataPlayNumber = value;
            }
        }

        public string KataTime
        {
            get
            {
                if (LocalSettings.KataTime == "")
                    LocalSettings.KataTime = "10";

                return LocalSettings.KataTime;
            }
            set
            {
                LocalSettings.KataTime = value;
            }
        }

        public string WordKataTime
        {
            get
            {
                if (LocalSettings.WordKataTime == "")
                    LocalSettings.WordKataTime = "10";

                return LocalSettings.WordKataTime;
            }
            set
            {
                LocalSettings.WordKataTime = value;
            }
        }
                
        public string KalimatSound
        {
            get
            {
                if (LocalSettings.KalimatSound == "")
                    LocalSettings.KalimatSound = "0";

                return LocalSettings.KalimatSound;
            }
            set
            {
                LocalSettings.KalimatSound = value;
            }
        }

        public string KataSound
        {
            get
            {
                if (LocalSettings.KataSound == "")
                    LocalSettings.KataSound = "0";

                return LocalSettings.KataSound;
            }
            set
            {
                LocalSettings.KataSound = value;
            }
        }

        public string ScreenLock
        {
            get
            {
                if (LocalSettings.ScreenLock == "")
                    LocalSettings.ScreenLock = "0";

                return LocalSettings.ScreenLock;
            }
            set
            {
                LocalSettings.ScreenLock = value;
            }
        }

        public string UpDateTime
        {
            get
            {
                return LocalSettings.UpDateTime;
            }
            set
            {
                LocalSettings.UpDateTime = value;
            }
        }

        public bool IntroPopup
        {
            get
            {
                return LocalSettings.IntroPopup;
            }
            set
            {
                LocalSettings.IntroPopup = value;
            }
        }

        public int SkinStyle
        {
            get
            {
                return LocalSettings.SkinStyle;
            }
            set
            {
                LocalSettings.SkinStyle = value;
            }
        }

        //자동 반복 횟수
        public int KataTimeRepeat
        {
            get
            {
                return LocalSettings.KataTimeRepeat;
            }
            set
            {
                LocalSettings.KataTimeRepeat = value;
            }
        }
        
        public int WordKataTimeRepeat
        {
            get
            {
                return LocalSettings.WordKataTimeRepeat;
            }
            set
            {
                LocalSettings.WordKataTimeRepeat = value;
            }
        }
        
        public UserSetting()
        {
            //DependencyService.Get<IKataSetting>(); 
        }
    }
}
