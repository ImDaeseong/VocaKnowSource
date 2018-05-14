using VocaKnow.Interfaces;
using Xamarin.Forms;

namespace VocaKnow.controls
{
    public class SetStatusBarSkin 
    {
        private static SetStatusBarSkin selfInstance = null;
        public static SetStatusBarSkin getInstance
        {
            get
            {
                if (selfInstance == null) selfInstance = new SetStatusBarSkin();
                return selfInstance;
            }
        }

        static ISetStatusBarStyle StatusBarSkin
        {
            get
            {
                return DependencyService.Get<ISetStatusBarStyle>();
            }
        }

        public void ChangeStatusBarColor(Color color)
        {
            StatusBarSkin.ChangeStatusBarColor(color);
        }
    }

}