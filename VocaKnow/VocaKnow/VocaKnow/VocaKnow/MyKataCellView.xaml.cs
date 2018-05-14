using System.ComponentModel;
using Xamarin.Forms;
using Xamarin.Forms.Xaml;
using System.Runtime.CompilerServices;
using VocaKnow.controls;

namespace VocaKnow
{
    [XamlCompilation(XamlCompilationOptions.Compile)]
    public partial class MyKataCellView : ContentView, INotifyPropertyChanged
    {
        public event PropertyChangedEventHandler PropertyChanged;
       
        protected void RaisePropertyChanged(string propertyName = "")
        {
            var changed = PropertyChanged;
            if (changed != null)
            {
                PropertyChanged(this, new PropertyChangedEventArgs(propertyName));
            }
        }

        private UserSetting Setting = UserSetting.getInstance;
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
           
            boxLine.BackgroundColor = CurSkin;
        }

        public MyKataCellView(string KataKor, string KataIndo, string KataIndoTambah)
        {
            InitializeComponent();

            //스킨 설정
            InitSkinStyle();            

            this.KataKor = KataKor;
            this.KataIndo = KataIndo;
            this.KataIndoTambah = KataIndoTambah;

            this.MinimumHeightRequest = 50;
        }

        private string _KataKor;
        public string KataKor
        {
            get { return _KataKor; }
            set { _KataKor = value; lblKataKor.Text = value; }
        }

        private string _KataIndo;
        public string KataIndo
        {
            get { return _KataIndo; }
            set { _KataIndo = value; lblKataIndo.Text = value; }
        }

        private string _KataIndoTambah;
        public string KataIndoTambah
        {
            get { return _KataIndoTambah; }
            set
            {
                _KataIndoTambah = value;
                lblKataIndoTambah.Text = value;

                if (_KataIndoTambah == "" || _KataIndoTambah == null)
                {
                    lblKataIndoTambah.IsVisible = false;
                    lblKataIndoTambah.HeightRequest = 0;
                }
                else
                    lblKataIndoTambah.IsVisible = true;
                RaisePropertyChanged();
            }
        }

    }
}