using System;
using System.Collections.ObjectModel;
using VocaKnow.Interfaces;
using Xamarin.Forms;
using Xamarin.Forms.Xaml;
using System.Diagnostics;
using System.Runtime.CompilerServices;
using System.Threading.Tasks;

namespace VocaKnow
{
    [XamlCompilation(XamlCompilationOptions.Compile)]
    public partial class AllView : ContentView
    {        
        private int nRepeatCount;
        private int nRepeatTime = 0;
        private bool bIsAutoPlay = false;
        
        
        private controls.KataDatabase kataPlay = controls.KataDatabase.getInstance;
        private controls.UserSetting Setting = controls.UserSetting.getInstance;
        private controls.ToSpeak TextToSpeech = controls.ToSpeak.getInstance;
        private bool bUseSound = false;


        private KataItems item;
        private ObservableCollection<KataItems> mCollection;
        private int CurrentPlayIndex;
        private int nTotalPageCount;


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
            
            Kataviewin.BackgroundColor = CurSkin;

            upPrePayImage.ButtonColor = CurSkin;
            upNextplayImage.ButtonColor = CurSkin;

            Editsentence.ButtonColor = CurSkin;
        }

        public AllView(string sName = "")
        {
            InitializeComponent();
            
            //스킨 설정
            InitSkinStyle();
                        
            try
            {
                TextToSpeech.InitSpeak();

                //사운드 사용 여부
                int nKataSound = Convert.ToInt32(Setting.KalimatSound);
                if (nKataSound == 1)
                    bUseSound = true;
                else
                    bUseSound = false;

                DisplayMarquee();

                if (sName == "")
                    mCollection = new ObservableCollection<KataItems>(kataPlay.GetItems());
                else
                {
                    string[] sPart = sName.Split('/');
                    mCollection = new ObservableCollection<KataItems>(kataPlay.GetItemsPart(sPart[0], sPart[1]));
                }

                nTotalPageCount = mCollection.Count;

                CurrentKata();

            }catch{}
            
            this.SizeChanged += AllView_SizeChanged;            
        }
        
        private async void CurrentKata()
        {
            try
            {
                if (CurrentPlayIndex < 0 || mCollection.Count == 0) return;

                item = mCollection[CurrentPlayIndex];
                KataIndo.Text = item.KataIndo;
                KataKor.Text = item.KataKor;
                lembutlidah.Text = item.lembutlidah;

                CounterTimerLabel();

                PlaySuaraSound(lembutlidah.Text);

            } catch{}
        }
        
        private async void PreKata()
        {
            try
            {
                if (mCollection.Count == 0) return;
                
                await SwipeAnimateEffect(true);
                /*
                await Kataview.TranslateTo(-300, 0);
                await Kataview.FadeTo(0, 1);
                await Kataview.TranslateTo(300, 0);
                await Kataview.FadeTo(1, 1);
                await Kataview.TranslateTo(0, 0);
                */

                CurrentPlayIndex--;

                if (CurrentPlayIndex < 0)
                    CurrentPlayIndex = mCollection.Count - 1;

                item = mCollection[CurrentPlayIndex];
                KataIndo.Text = item.KataIndo;
                KataKor.Text = item.KataKor;
                lembutlidah.Text = item.lembutlidah;

                //종료시 저장
                //App.PlayNumberIndex = CurrentPlayIndex;
                //MainPage.GetMainPageInstance().SetPlayKataIndex(CurrentPlayIndex);

                if(!bIsAutoPlay)
                    CounterTimerLabel();

                PlaySuaraSound(lembutlidah.Text);
                
            } catch{}
        }

        private async void NextKata()
        {
            try
            {
                if (mCollection.Count == 0) return;
                
                await SwipeAnimateEffect(false);
                /*
                await Kataview.TranslateTo(300, 0);
                await Kataview.FadeTo(0, 1);
                await Kataview.TranslateTo(-300, 0);
                await Kataview.FadeTo(1, 1);
                await Kataview.TranslateTo(0, 0);
                */

                CurrentPlayIndex++;

                if (CurrentPlayIndex > (mCollection.Count - 1))
                    CurrentPlayIndex = 0;
                
                //Debug.WriteLine(CurrentPlayIndex.ToString());

                item = mCollection[CurrentPlayIndex];
                KataIndo.Text = item.KataIndo;
                KataKor.Text = item.KataKor;
                lembutlidah.Text = item.lembutlidah;

                //종료시 저장
                //App.PlayNumberIndex = CurrentPlayIndex;
                //MainPage.GetMainPageInstance().SetPlayKataIndex(CurrentPlayIndex);

                if(!bIsAutoPlay)
                    CounterTimerLabel();

                PlaySuaraSound(lembutlidah.Text);                

            } catch{}
        }

        private async void PlaySuaraSound(string sText)
        {
            try
            {
                if (bUseSound)
                {
                    await Task.Delay(1000);

                    TextToSpeech.Speak(sText);
                }
            }
            catch { }
        }

        private void DisplayMarquee()
        {
            if (bIsAutoPlay)
            {
                nRepeatTime = Convert.ToInt32(Setting.KataTime);
                mView.ContentMarquee = string.Format("지금은 {0}초마다 자동 실행중입니다.", nRepeatTime);
            }
            else
            {
                nRepeatTime = Convert.ToInt32(Setting.KataTime);
                mView.ContentMarquee = string.Format("Play 버튼을 클릭하시면 {0}초마다 자동 실행합니다.", nRepeatTime);
            }
        }

        private async Task SwipeAnimateEffect(bool bLeft)
        {
            if (bLeft)
            {
                //await Kataview.FadeTo(0);
                //Kataview.IsVisible = true;
                var animate = new Animation();
                var registerin = new Animation(callback: ax => Kataview.TranslationX = ax,
                        start: Width,
                        end: 0,
                        easing: Easing.SpringOut);
                animate.Add(0, 1, registerin);
                animate.Commit(Kataview, "RegisterIn", length: 1000);
                //await Kataview.FadeTo(1);
            }
            else
            {
                //await Kataview.FadeTo(0);
                //Kataview.IsVisible = true;
                var animate = new Animation();
                var registerin = new Animation(callback: ax => Kataview.TranslationX = ax,
                        start: -Width,
                        end: 0,
                        easing: Easing.SpringOut);
                animate.Add(0, 1, registerin);
                animate.Commit(Kataview, "RegisterIn", length: 1000);
                //await Kataview.FadeTo(1);
            }
        }

        private async void Kataview_SwipeLeft(object sender, EventArgs e)
        {
            PreKata();
            
            if (bIsAutoPlay) nRepeatCount = 0;
        }

        private async void Kataview_SwipeRight(object sender, EventArgs e)
        {
            NextKata();
            
            if (bIsAutoPlay) nRepeatCount = 0;
        }

        private async void TapGestureRecognizer_TappedPrePlayKata(object sender, EventArgs e)
        {
            PreKata();
        }

        private async void TapGestureRecognizer_TappedNextPlayKata(object sender, EventArgs e)
        {
            NextKata();
        }
        
        private void CounterTimerLabel()
        {
            CounterTimer.TextColor = CurSkin;// Color.FromHex("#33A7D6");

            int nDisplayIndex = CurrentPlayIndex + 1;
            CounterTimer.Text = string.Format("현재 페이지 ({0}/{1})", nDisplayIndex, nTotalPageCount);
        }

        public async void RunAutoPlay()
        {            
            Device.StartTimer(TimeSpan.FromSeconds(1), () =>
            {
                if (!bIsAutoPlay)
                {
                    nRepeatCount = 0;
                    CounterTimerLabel();
                    return false;
                }

                nRepeatCount++;


                if (nRepeatCount % 2 == 0)
                {
                    CounterTimer.TextColor = CurSkin;
                    //CounterTimer.BackgroundColor = Color.White;
                }
                else
                {
                    CounterTimer.TextColor = Color.Transparent;
                    //CounterTimer.BackgroundColor = Color.Transparent;
                }

                int nDisplayIndex = CurrentPlayIndex + 1;
                CounterTimer.Text = string.Format("{0}초 경과 ({1}/{2})", nRepeatCount, nDisplayIndex, nTotalPageCount);


                 //사운드 사용시
                if (bUseSound)
                {                    
                    if (TextToSpeech.IsSpeaking) return true;
                }

                if (nRepeatTime <= nRepeatCount)
                {
                    nRepeatCount = 0;
                    NextKata();
                }               
                                    
                return true;
            });

            /*
            Device.StartTimer(TimeSpan.FromSeconds(5), () =>
            {
                if (bIsAutoPlay)
                    return false;
                
                NextKata();
                return true;
            });
            */
        }

        private async void TapGestureRecognizer_TappedPlay(object sender, EventArgs e)
        {            
            if (!bIsAutoPlay)
            {
                bIsAutoPlay = true;
                playImage.ButtonColor = CurSkin;// Color.FromHex("#33A7D6");
                
                Editsentence.IsVisible = false;

                IsVisibleBottomArrow(bIsAutoPlay);
                
                RunAutoPlay();                
            }
            else
            {
                bIsAutoPlay = false;
                playImage.ButtonColor = Color.Gray;

                Editsentence.IsVisible = true;

                IsVisibleBottomArrow(bIsAutoPlay);
                
                RunAutoPlay();                
            }

            DisplayMarquee();
        }
               
        protected override void InvalidateLayout()
        {
            base.InvalidateLayout();
        }

        private void IsVisibleBottomArrow(bool bVisible)
        {
            if (this.Width > this.Height)
            {
                if (bIsAutoPlay)
                {
                    upPrePayImage.IsVisible = false;
                    upNextplayImage.IsVisible = false;
                }
                else
                {
                    upPrePayImage.IsVisible = !bVisible;
                    upNextplayImage.IsVisible = !bVisible;
                }
            }
            else
            {
                upPrePayImage.IsVisible = false;
                upNextplayImage.IsVisible = false;
            }
        }

        private void SetLocationArrow(bool bVisible)
        {
            if (bVisible)
            {
                IsVisibleBottomArrow(false);

                SetDisplayEditButton(true);


                RelativeLayout.SetBoundsConstraint(upPrePayImage,
                BoundsConstraint.FromExpression(
                    () => new Rectangle(
                        relLayout.X + 10,
                        ((relLayout.Height - upPrePayImage.Height) - 10),//(relLayout.Height / 2 - upPrePayImage.Height / 2),
                        upPrePayImage.Width,
                        upPrePayImage.Height),
                null));

                RelativeLayout.SetBoundsConstraint(upNextplayImage,
                BoundsConstraint.FromExpression(
                    () => new Rectangle(
                        ((relLayout.Width - upNextplayImage.Width) - 10),
                        ((relLayout.Height - upPrePayImage.Height) - 10),//(relLayout.Height / 2 - upPrePayImage.Height / 2),
                        upNextplayImage.Width,
                        upNextplayImage.Height),
                null));
                                                
            }
            else
            {
                IsVisibleBottomArrow(true);

                SetDisplayEditButton(false);
            }

            RelativeLayout.SetBoundsConstraint(playImage,
               BoundsConstraint.FromExpression(
                   () => new Rectangle(
                       ((relLayout.Width - playImage.Width) - 1.5),//((relLayout.Width - playImage.Width) - 10),
                       (relLayout.Y + 1.5),//relLayout.Y,//relLayout.Y + 10,
                       playImage.Width,
                       playImage.Height),
               null));            
        }

        private void SetDisplayEditButton(bool bWidthVisible)
        {
            if (bWidthVisible)
            {
                double dX = (this.Width / 2) + ((Kataview.X + Kataview.WidthRequest) / 2) - Editsentence.Width;
                double dY = (this.Height / 2) + ((Kataview.Y + Kataview.HeightRequest) / 2) - Editsentence.Height;

                RelativeLayout.SetBoundsConstraint(Editsentence,
                  BoundsConstraint.FromExpression(
                      () => new Rectangle(
                          dX,
                          dY,
                          Editsentence.Width,
                          Editsentence.Height),
                  null));
            }
            else
            {
                double dX = (this.Width / 2) + ((Kataview.X + Kataview.WidthRequest) / 2) - Editsentence.Width;
                double dY = (this.Height / 2) + ((Kataview.Y + Kataview.HeightRequest) / 2) - (Editsentence.Height) - 5;

                RelativeLayout.SetBoundsConstraint(Editsentence,
                  BoundsConstraint.FromExpression(
                      () => new Rectangle(
                          dX,
                          dY,
                          Editsentence.Width,
                          Editsentence.Height),
                  null));
            }

        }
        
        private void AllView_SizeChanged(object sender, EventArgs e)
        {
            if (this.Width > this.Height)
            {
                Kataview.WidthRequest = (this.Width - 200); 
                Kataview.HeightRequest = (this.Height - 55) + (MarqueeMenu.Height);
                
                MarqueeMenu.IsVisible = false;

                SetLocationArrow(true);
            }
            else
            {
                Kataview.WidthRequest = (this.Width - 10);
                Kataview.HeightRequest = (this.Height / 2) + ((MarqueeMenu.Height) / 2);
                
                MarqueeMenu.IsVisible = true;

                SetLocationArrow(false);
            }
            
            this.UpdateChildrenLayout();
            //this.InvalidateLayout();
        }

        protected override void OnSizeAllocated(double width, double height)
        {            
            base.OnSizeAllocated(width, height);
                     
            /*
            if (width <= 0 || height <= 0)
                return;
                        
            if (this.Width > this.Height)
            {
                Kataview.WidthRequest = (this.Width - 10);
                Kataview.HeightRequest = (this.Height - 55) + (MarqueeMenu.Height);
                
                MarqueeMenu.IsVisible = false;
            }
            else
            {
                Kataview.WidthRequest = (this.Width - 10);
                Kataview.HeightRequest = (this.Height / 2) + ((MarqueeMenu.Height) / 2);
                
                MarqueeMenu.IsVisible = true;
            }
            */
        }

        private async Task SlideIn()
        {
            await this.FadeTo(0);
            var animate = new Animation();
            var slidein = new Animation(callback: ax => this.TranslationY = ax,
                    start: -Height,
                    end: 0,
            easing: Easing.SpringOut);
            animate.Add(0, 1, slidein);
            animate.Commit(this, "SlideIn", length: 1000);
            await this.FadeTo(1);
                        
            /*
            await this.FadeTo(0);
            var animate = new Animation();
            var slidein = new Animation(callback: ax => this.TranslationY = ax,
                    start: -Height,
                    end: 0,
            easing: Easing.Linear);//easing: Easing.SpringOut);
            animate.Add(0, 1, slidein);
            animate.Commit(this, "SlideIn", length: 500);//animate.Commit(this, "SlideIn", length: 1000);
            await this.FadeTo(1);
            */
        }

        private void IsShowControls(bool bShow)
        {
            PlayBoard.IsVisible = bShow;
            //MarqueeMenu.IsVisible = bShow;
            playImage.IsVisible = bShow;
            //Editsentence.IsVisible = bShow;
        }
        
        protected async override void OnParentSet()
        {
            base.OnParentSet();

            if (Parent != null)
            {
                //Appearing
                await SlideIn();

                //깜박임 방지
                IsShowControls(true);    
            }
            else
            {
                //깜박임 방지
                IsShowControls(false);
                
                //Disappearing
                if (bIsAutoPlay)
                {
                    bIsAutoPlay = false;
                    playImage.ButtonColor = Color.Gray;
                    upPrePayImage.ButtonColor = Color.Gray;
                    upNextplayImage.ButtonColor = Color.Gray;

                    IsVisibleBottomArrow(bIsAutoPlay);

                    RunAutoPlay();
                    DisplayMarquee();
                }
            }

            /*
            VisualElement view = ParentView;
            while (!(view is Page))
            {
                view = view.ParentView;
            }
            Page page = (Page)view;
            page.Disappearing += page_Disappearing;
            page.Appearing += page_Appearing;
            */
        }

        private async void Editsentence_Clicked(object sender, EventArgs e)
        {
            try
            {
                await Navigation.PushModalAsync(new EditKalimatPage(this, KataIndo.Text, lembutlidah.Text));

            }catch { }
        }

        public void UpdateKalimat(string sKataIndo, string sKataIndoTambah)
        {
            try
            {
                KataItems item = mCollection[CurrentPlayIndex];
                item.KataIndo = sKataIndo;
                item.lembutlidah = sKataIndoTambah;
                mCollection[CurrentPlayIndex] = item;

                CurrentKata();

            }catch { }
        }

        /*
        void page_Appearing(object sender, EventArgs e)
        {            
        }
        
        void page_Disappearing(object sender, EventArgs e)
        {
        }
        */

    }
        
}