using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using VocaKnow.controls;
using VocaKnow.Renderers;
using Xamarin.Forms;
using Xamarin.Forms.Xaml;

namespace VocaKnow
{
    [XamlCompilation(XamlCompilationOptions.Compile)]
    public partial class EditKalimatPage : ContentPage
    {
        private KataDatabase kataPlay = KataDatabase.getInstance;
        private UserSetting Setting = UserSetting.getInstance;
        private AlertMsg Alert = AlertMsg.getInstance;

        private ContentView AllviewHandle;

        private string sSavedKataIndo;

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

            EnterClose.BackgroundColor = CurSkin;

            lblIndo.TextColor = CurSkin;
            lblTambah.TextColor = CurSkin;

            btnEdit.TextColor = CurSkin;
            btnEdit.SkinStyle = SkinType;            
        }

        public EditKalimatPage(ContentView view,  string sKataIndo, string sKataIndoTambah)
        {
            InitializeComponent();

            try
            {
                AllviewHandle = view;

                //스킨 설정
                InitSkinStyle();

                EnterClose.Text = "문장 편집";

                KataIndo.Text = sKataIndo;
                KataIndoTambah.Text = sKataIndoTambah;
                sSavedKataIndo = sKataIndo;

            }catch{}
        }

        protected override void OnAppearing()
        {
            base.OnAppearing();

            //await SlideIn();
        }

        protected override void OnDisappearing()
        {
            base.OnDisappearing();

            //await SlideOut();           
        }

        private async void btnEdit_Clicked(object sender, EventArgs e)
        {
            try
            {
                await AnimateTextButton(btnEdit);

                kataPlay.UpdateKalimat(KataIndo.Text, KataIndoTambah.Text, sSavedKataIndo);

                Alert.ShortAlert("선택된 문장이 수정 되었습니다.");

                ((AllView)AllviewHandle).UpdateKalimat(KataIndo.Text, KataIndoTambah.Text);                               

                await Navigation.PopModalAsync();

            } catch { }
        }

        private async void EnterClose_Click(object sender, EventArgs e)
        {
            await Navigation.PopModalAsync();
        }

        private async Task<bool> AnimateTextButton(TextButton sl)
        {
            await sl.ScaleTo(0.95, 50, Easing.CubicOut);
            await sl.ScaleTo(1, 50, Easing.CubicIn);
            return true;
        }


    }
}