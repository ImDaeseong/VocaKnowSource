using VocaKnow.Interfaces;
using Xamarin.Forms;

namespace VocaKnow.controls
{
    public class SetScreenLockUnLock
    {
        private static SetScreenLockUnLock selfInstance = null;
        public static SetScreenLockUnLock getInstance
        {
            get
            {
                if (selfInstance == null) selfInstance = new SetScreenLockUnLock();
                return selfInstance;
            }
        }

        static IScreenLock ScreenLock
        {
            get
            {
                return DependencyService.Get<IScreenLock>();
            }
        }

        public void SetLockUnLock(bool bLock)
        {
            if (bLock)
                ScreenLock.Lock();
            else
                ScreenLock.Unlock();
        }
    }
}
