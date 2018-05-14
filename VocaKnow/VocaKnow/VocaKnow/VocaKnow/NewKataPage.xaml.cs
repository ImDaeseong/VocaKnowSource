using System;
using System.Threading.Tasks;
using VocaKnow.controls;
using VocaKnow.Renderers;
using Xamarin.Forms;
using Xamarin.Forms.Xaml;

namespace VocaKnow
{
    [XamlCompilation(XamlCompilationOptions.Compile)]
    public partial class NewKataPage : ContentPage
    {
        private KataDatabase kataPlay = KataDatabase.getInstance;
        private MicRecording MicRecord = MicRecording.getInstance;
        private UserSetting Setting = UserSetting.getInstance;
        private AlertMsg Alert = AlertMsg.getInstance;

        private bool bAdd;
        private string sSavedKataIndo;

        bool IsRecording = false;
        bool IsPrePlaying = false;
        bool bOpenRecordPopup = false;


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

            btnSave.TextColor = CurSkin;
            btnSave.SkinStyle = SkinType;

            btnEdit.TextColor = CurSkin;
            btnEdit.SkinStyle = SkinType;

            btnDelete.TextColor = CurSkin;
            btnDelete.SkinStyle = SkinType;

            frameRecord.BackgroundColor = CurSkin;

            framePlay.BackgroundColor = CurSkin;
        }

        public NewKataPage(bool bAdd, string sKataIndo, string sKataIndoTambah, string sKataKor)
        {
            InitializeComponent();

            try
            {
                //스킨 설정
                InitSkinStyle();
                
                this.bAdd = bAdd;

                IsVisibleButton(bAdd);

                if (bAdd)
                {
                    EnterClose.Text = "단어 추가";

                    KataIndo.Text = "";
                    KataIndoTambah.Text = "";
                    KataKor.Text = "";
                }
                else
                {
                    EnterClose.Text = "단어 편집";

                    KataIndo.Text = sKataIndo;
                    KataIndoTambah.Text = sKataIndoTambah;
                    KataKor.Text = sKataKor;
                    sSavedKataIndo = sKataIndo;
                }

                var tapplayImagee = new TapGestureRecognizer();
                tapplayImagee.Tapped += (s, e) =>
                {
                    TapGestureRecognizer_TappedPlay(this, null);
                };
                btnPlay.GestureRecognizers.Add(tapplayImagee);


                //녹음 파일 존재 여부 체크
                string sPath = string.Format("{0}/{1}.mp4", MicRecord.AudioFilePath, KataIndo.Text);// string sPath = string.Format("{0}/{1}.wav", MicRecord.AudioFilePath, KataIndo.Text);
                if (FilesManager.FileExists(sPath))
                    MicRecord.SetAudioFilePath(KataIndo.Text);

            } catch { }
            
        }
               

        protected override void OnAppearing()//protected async override void OnAppearing()
        {
            base.OnAppearing();

            //await SlideIn();
        }

        protected override void OnDisappearing()//protected async override void OnDisappearing()
        {
            base.OnDisappearing();

            //await SlideOut();

            try
            {
                MicRecord.StopAudio();
            }
            catch { }
        }

        /*
        private async Task SlideIn()
        {
            await this.FadeTo(0);
            var animate = new Animation();
            var slidein = new Animation(callback: ax => this.TranslationY = ax,
                    start: Height,
                    end: 0,
                    easing: Easing.SpringOut);
            animate.Add(0, 1, slidein);
            animate.Commit(this, "SlideIn", length: 1000);
            await this.FadeTo(1);
        }

        private async Task SlideOut()
        {
            await this.FadeTo(1);
            var animate = new Animation();
            var slideout = new Animation(callback: ax => this.TranslationY = ax,
                   start: 0,
                   end: -Height,
                   easing: Easing.SpringIn);
            animate.Add(0, 1, slideout);
            animate.Commit(this, "SlideOut", length: 1000);
            await this.FadeTo(0);
        }
        */

        protected override void OnSizeAllocated(double width, double height)
        {
            base.OnSizeAllocated(width, height);

            if (width <= 0 || height <= 0) return;

            RelativeLayout.SetBoundsConstraint(btnTextRecord,
               BoundsConstraint.FromExpression(
                  () => new Rectangle(
                      relLayout.X + 10,
                      ((relLayout.Height - btnTextRecord.Height) - 10),
                      btnTextRecord.Width,
                      btnTextRecord.Height),
               null));

            this.UpdateChildrenLayout();
        }

        private async void btnSave_Clicked(object sender, EventArgs e)
        {
            try
            {
                await AnimateTextButton(btnSave);

                if (KataIndo.Text == "" || KataKor.Text == "")
                {
                    await App.ShowMessage("단어장 편집", "등록할 단어가 없습니다.");
                    return;
                }

                if (!kataPlay.IsExistKata(KataIndo.Text))
                    kataPlay.InsertRegKata(KataIndo.Text, KataIndoTambah.Text, KataKor.Text);

                KataIndo.Text = "";
                KataIndoTambah.Text = "";
                KataKor.Text = "";

                Alert.ShortAlert("새로운 단어가 등록 되었습니다.");

                await RecordPopup();
                
            }
            catch { }

            //await Navigation.PopModalAsync();
        }

        private async void btnEdit_Clicked(object sender, EventArgs e)
        {
            try
            {
                await AnimateTextButton(btnEdit);

                kataPlay.UpdateRegKata(sSavedKataIndo, KataIndo.Text, KataIndoTambah.Text, KataKor.Text);

                Alert.ShortAlert("선택된 단어가 수정 되었습니다.");

                await Navigation.PopModalAsync();

            }catch { }
        }

        private async void btnDelete_Clicked(object sender, EventArgs e)
        {
            try
            {
                await AnimateTextButton(btnDelete);

                kataPlay.DeleteRegKata(sSavedKataIndo);

                //녹음 파일 존재 여부 체크
                string sPath = string.Format("{0}/{1}.mp4", MicRecord.AudioFilePath, sSavedKataIndo);//string sPath = string.Format("{0}/{1}.wav", MicRecord.AudioFilePath, sSavedKataIndo);
                if (FilesManager.FileExists(sPath))
                    FilesManager.FileDelete(sPath);

                Alert.ShortAlert("선택된 단어가 삭제 되었습니다.");

                await Navigation.PopModalAsync();

            }catch { }
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

        private async Task<bool> AnimateFloatingActionButton(FloatingActionButton sl)
        {
            await sl.ScaleTo(0.25, 50, Easing.CubicOut);
            await sl.ScaleTo(0.5, 50, Easing.CubicIn);
            return true;
        }

        private void IsVisibleButton(bool bAdd)
        {
            btnSave.IsVisible = bAdd;
            btnEdit.IsVisible = !bAdd;
            btnDelete.IsVisible = !bAdd;
        }
                
        private async void btnRecord_Clicked(object sender, EventArgs e)
        {
            if (KataIndo.Text == "") return;
            if (IsPrePlaying) return;

            await AnimateFloatingActionButton(btnRecord);

            try
            {
                if (!IsRecording)
                {
                    lblRecord.Text = "현재 녹음이 시작되었습니다.";
                    IsRecording = true;
                    //btnRecord.ButtonColor = Color.FromHex("#33A7D6");

                    MicRecord.SetAudioFilePath(KataIndo.Text);
                    MicRecord.SetRecording(IsRecording);

                    App.WriteLogString("현재 녹음이 시작되었습니다.");
                }
                else
                {
                    lblRecord.Text = "버튼을 클릭하면 녹음이 시작됩니다.";
                    IsRecording = false;
                    //btnRecord.ButtonColor = Color.Gray;

                    MicRecord.SetRecording(IsRecording);

                    App.WriteLogString("버튼을 클릭하면 녹음이 시작됩니다.");
                }
            }
            catch{}
        }

        private void TapGestureRecognizer_TappedPlay(object sender, EventArgs e)
        {            
            try
            {
                //App.WriteLogString("TapGestureRecognizer_TappedPlay1");

                string sPath = string.Format("{0}/{1}.mp4", MicRecord.AudioFilePath, KataIndo.Text);
                if (!FilesManager.FileExists(sPath))
                {
                    Alert.ShortAlert("녹음된 파일이 존재하지 않습니다.");
                    return;
                }

                if (IsPrePlaying) return;

                //App.WriteLogString("TapGestureRecognizer_TappedPlay2");

                if (!IsRecording)
                {                   
                    lblRecord.Text = "녹음내용 미리 듣기 중입니다.";
                    IsPrePlaying = true;
                    MicRecord.PlayAudio();

                    totalTime.Text = MicRecord.GetTotalTimeDisplay();
                    currentTime.Text = MicRecord.GetCurrentTimeDisplay();
                    progress.Progress = MicRecord.GetCurrentPosition() * 1.0f / MicRecord.GetPosition();

                    UpdatePlayState();                    
                }
                else
                {
                    //App.WriteLogString("TapGestureRecognizer_TappedPlay4");
                }

                //App.WriteLogString("TapGestureRecognizer_TappedPlay3");
            }
            catch
            {               
                //App.WriteLogString("TapGestureRecognizer_TappedPlay5");
            }
        }

        private void UpdatePlayState()
        {
            Device.StartTimer(new TimeSpan(1000), () =>
            {                
                progress.Progress = MicRecord.GetCurrentPosition() * 1.0f / MicRecord.GetPosition();

                currentTime.Text = MicRecord.GetCurrentTimeDisplay();

                bool bPlay = MicRecord.IsPlaying();
                if (!bPlay)
                {
                    lblRecord.Text = "버튼을 클릭하면 녹음이 시작됩니다.";
                    IsPrePlaying = false;

                    totalTime.Text = "00:00";
                    currentTime.Text = "00:00";
                    progress.Progress = 0;
                }

                return bPlay;
            });
        }
                
        private async void btnTextRecord_Clicked(object sender, EventArgs e)
        {
            if (KataIndo.Text == "" || KataKor.Text == "")
            {
                await App.ShowMessage("단어장 편집", "녹음할 단어가 없습니다.");
                return;
            }

            await RecordPopup();
        }
        
        private async Task RecordPopup()
        {
            if (bOpenRecordPopup)
            {
                await btnTextRecord.ScaleTo(0.65, 50, Easing.CubicOut);
                await btnTextRecord.ScaleTo(0.5, 50, Easing.CubicIn);

                bOpenRecordPopup = false;
                await SlideOutframeRecord(frameRecord);
                await SlideOutframeRecord(framePlay);
            }
            else
            {
                await btnTextRecord.ScaleTo(0.65, 50, Easing.CubicOut);
                await btnTextRecord.ScaleTo(0.75, 50, Easing.CubicIn);

                bOpenRecordPopup = true;
                await SlideInframeRecord(frameRecord);
                await SlideInframeRecord(framePlay);
            }
        }

        private async Task SlideInframeRecord(Frame target)
        {
            await target.FadeTo(0);
            target.IsVisible = true;
            var animate = new Animation();
            var slidein = new Animation(callback: ax => target.TranslationY = ax,
                    start: Height,
                    end: 0,
                    easing: Easing.SpringOut);
            animate.Add(0, 1, slidein);
            animate.Commit(target, "SlideIn", length: 1000);
            await target.FadeTo(1);
        }

        private async Task SlideOutframeRecord(Frame target)
        {
            await target.FadeTo(1);
            var animate = new Animation();
            var slideout = new Animation(callback: ax => target.TranslationY = ax,
                   start: 0,
                   end: -Height,
                   easing: Easing.SpringIn);
            animate.Add(0, 1, slideout);
            animate.Commit(target, "SlideOut", length: 1000);
            await target.FadeTo(0);
            target.IsVisible = false;
        }

    }
}