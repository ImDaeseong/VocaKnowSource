using System;
using System.Threading.Tasks;
using Xamarin.Forms;
using Xamarin.Forms.Xaml;

namespace VocaKnow
{
    [XamlCompilation(XamlCompilationOptions.Compile)]
    public partial class MainPage : ContentPage
    {
        private controls.UserSetting Setting = controls.UserSetting.getInstance;
        private controls.SetStatusBarSkin StatusBarSkin = controls.SetStatusBarSkin.getInstance;


        public static MainPage instance = new MainPage();
        public static MainPage GetMainPageInstance()
        {
            if (instance == null)
            {
                return new MainPage();
            }
            return instance;
        }

        public string GetCurrentTime()
        {
            DateTime date = DateTime.Now;
            return date.ToString("yyyy-MM-dd");
        }

        
        public int nSelectTabIndex;
        public int CurrentPlayIndex;
       
               
        private string sSkinType;
        private int SkinType;
        private Color CurSkin;
        private Color StatusSkin;
        private void InitSkinStyle()
        {
            SkinType = Setting.SkinStyle;

            if (SkinType == 1)
            {
                CurSkin = Color.FromHex("#33A7D6");
                StatusSkin = Color.FromHex("#0299CC");
                sSkinType = "bookA.png";
            }
            else if (SkinType == 2)
            {
                CurSkin = Color.FromHex("#493335");
                StatusSkin = Color.FromHex("#3D2B2B");
                sSkinType = "bookB.png";
            }
            else if (SkinType == 3)
            {
                CurSkin = Color.FromHex("#FF80AB");
                StatusSkin = Color.FromHex("#EA7CAA");
                sSkinType = "bookC.png";
            }
            else if (SkinType == 4)
            {
                CurSkin = Color.FromHex("#4CAF50");
                StatusSkin = Color.FromHex("#42AA49");
                sSkinType = "bookD.png";
            }
            else if (SkinType == 5)
            {
                CurSkin = Color.FromHex("#3F51B5");
                StatusSkin = Color.FromHex("#3551AF");
                sSkinType = "bookE.png";
            }
            else if (SkinType == 6)
            {
                CurSkin = Color.FromHex("#B71C1C");
                StatusSkin = Color.FromHex("#A31416");
                sSkinType = "bookF.png";
            }
            else if (SkinType == 7)
            {
                CurSkin = Color.FromHex("#37474F");
                StatusSkin = Color.FromHex("#334249");
                sSkinType = "bookG.png";
            }
           
            boxline.Color = CurSkin;

            tabImg1.Source = sSkinType;
            tabImg2.Source = sSkinType;
            tabImg3.Source = sSkinType;
            tabImg4.Source = sSkinType;
            tabImg5.Source = sSkinType;

            StatusBarSkin.ChangeStatusBarColor(StatusSkin);
        }              

        public MainPage()
        {
            InitializeComponent();

            try
            {
                //스킨 설정
                InitSkinStyle();


                //------------ MessagingCenter 해제           

                //스킨 변경
                MessagingCenter.Unsubscribe<MainPage>(this, "SkinStyle");

                //함수에 스트링 콜
                MessagingCenter.Unsubscribe<MainPage, string>(this, "AllView");

                //설정이 끝나면 현재뷰 초기화
                MessagingCenter.Unsubscribe<MainPage>(this, "ChangeSetting");

                
                //------------ MessagingCenter 설정        

                //스킨 변경
                MessagingCenter.Subscribe<MainPage>(this, "SkinStyle", (sender) =>
                {
                    InitSkinStyle();
                });

                //함수에 스트링 콜
                MessagingCenter.Subscribe<MainPage, string>(this, "AllView", (sender, arg) => {
                    SelectKataAllView(arg);
                });

                //설정이 끝나면 현재뷰 초기화
                MessagingCenter.Subscribe<MainPage>(this, "ChangeSetting", (sender) =>
                {
                    if (ChangeContent.Content.ToString() == "VocaKnow.ListView")
                    {
                        var content = new ListView();
                        ChangeContent.Content = content;
                        SetTabTextColor(1);
                    }
                    else if (ChangeContent.Content.ToString() == "VocaKnow.AllView")
                    {
                        var content = new AllView();
                        ChangeContent.Content = content;
                        SetTabTextColor(2);
                    }
                    else if (ChangeContent.Content.ToString() == "VocaKnow.IngatView")
                    {
                        var content = new IngatView();
                        ChangeContent.Content = content;
                        SetTabTextColor(3);
                    }
                    else if (ChangeContent.Content.ToString() == "VocaKnow.KataKataView")
                    {
                        var content = new KataKataView();
                        ChangeContent.Content = content;
                        SetTabTextColor(4);
                    }
                });
                                
                NavigationPage.SetHasNavigationBar(this, false);
                SetTabTextColor(1);

                //인트로 화면
                if (Setting.IntroPopup == false) 
                    Navigation.PushModalAsync(new UserGuidePage());//Navigation.PushModalAsync(new IntroPage());

            } catch { }
        }
               
        private void TapGestureRecognizerList_Tapped(object sender, EventArgs e)
        {
            try
            {
                if (nSelectTabIndex == 1) return;

                var content = new ListView();
                ChangeContent.Content = content;
                SetTabTextColor(1);

            } catch{}                  
        }

        private void TapGestureRecognizerAll_Tapped(object sender, EventArgs e)
        {
            try
            {
                if (nSelectTabIndex == 2) return;

                //await ChangeContent.FadeTo(0);
                //ChangeContent.IsVisible = true;

                var content = new AllView();
                ChangeContent.Content = content;

                //await ChangeContent.FadeTo(1);

                SetTabTextColor(2);

            } catch { }
        }
        
        private void TapGestureRecognizerIngatMyKata_Tapped(object sender, EventArgs e)
        {
            try
            {
                if (nSelectTabIndex == 3) return;

                //await FlowAnimateEffect(ChangeContent);

                //await ChangeContent.FadeTo(0);
                //ChangeContent.IsVisible = true;

                var content = new IngatView();                
                ChangeContent.Content = content;

                //await ChangeContent.FadeTo(1);

                SetTabTextColor(3);

            } catch { }
        }

        private  void TapGestureRecognizerMyKata_Tapped(object sender, EventArgs e)
        {
            try
            {
                if (nSelectTabIndex == 4) return;

                var content = new KataKataView();
                ChangeContent.Content = content;
                SetTabTextColor(4);

            } catch { }
        }
        
        private async void TapGestureRecognizerSetMyKata_Tapped(object sender, EventArgs e)
        {
            try
            {
                await AnimateStackLayout(sl5);
                await Navigation.PushModalAsync(new SettingPage() );

            } catch { }
        }
        
        private async Task<bool> AnimateStackLayout(StackLayout sl)
        {
            await sl.ScaleTo(0.9, 75, Easing.CubicOut);
            await sl.ScaleTo(1, 75, Easing.CubicIn);
            return true;
        }      

        private void ChangeContent_SizeChanged(object sender, EventArgs e)
        {
            /*
            if (this.Width <= 0 || this.Height <= 0)
                return;
                 
            ContentView contentView = (ContentView)sender;
            double width = contentView.Width;
            double height = contentView.Height;
            
            contentView.WidthRequest = (this.Width - 10);
            contentView.HeightRequest = (this.Height - 60);
            */
        }
        
        public void SelectKataAllView(string sName)
        {
            var content = new AllView(sName);
            ChangeContent.Content = content;
            SetTabTextColor(2);
        }

        private void SetTabTextColor(int nIndex)
        {
            nSelectTabIndex = nIndex;

            if (nIndex == 1)
            {
                sl1.ScaleTo(1, 75);
                sl2.ScaleTo(0.9, 75);
                sl3.ScaleTo(0.9, 75);
                sl4.ScaleTo(0.9, 75);
                sl5.ScaleTo(0.9, 75);

                tab1.TextColor = Color.Orange;
                tab2.TextColor = Color.FromHex("#5A297D");
                tab3.TextColor = Color.FromHex("#5A297D");
                tab4.TextColor = Color.FromHex("#5A297D");
                tab5.TextColor = Color.FromHex("#5A297D");
            }
            else if (nIndex == 2)
            {
                sl1.ScaleTo(0.9, 75);
                sl2.ScaleTo(1, 75);
                sl3.ScaleTo(0.9, 75);
                sl4.ScaleTo(0.9, 75);
                sl5.ScaleTo(0.9, 75);

                tab1.TextColor = Color.FromHex("#5A297D");
                tab2.TextColor = Color.Orange;
                tab3.TextColor = Color.FromHex("#5A297D");
                tab4.TextColor = Color.FromHex("#5A297D");
                tab5.TextColor = Color.FromHex("#5A297D");
            }
            else if (nIndex == 3)
            {
                sl1.ScaleTo(0.9, 75);
                sl2.ScaleTo(0.9, 75);
                sl3.ScaleTo(1, 75);
                sl4.ScaleTo(0.9, 75);
                sl5.ScaleTo(0.9, 75);

                tab1.TextColor = Color.FromHex("#5A297D");
                tab2.TextColor = Color.FromHex("#5A297D");
                tab3.TextColor = Color.Orange;
                tab4.TextColor = Color.FromHex("#5A297D");
                tab5.TextColor = Color.FromHex("#5A297D");
            }
            else if (nIndex == 4)
            {
                sl1.ScaleTo(0.9, 75);
                sl2.ScaleTo(0.9, 75);
                sl3.ScaleTo(0.9, 75);
                sl4.ScaleTo(1, 75);
                sl5.ScaleTo(0.9, 75);

                tab1.TextColor = Color.FromHex("#5A297D");
                tab2.TextColor = Color.FromHex("#5A297D");
                tab3.TextColor = Color.FromHex("#5A297D");
                tab4.TextColor = Color.Orange;
                tab5.TextColor = Color.FromHex("#5A297D");
            }
            else if (nIndex == 5)
            {
                sl1.ScaleTo(0.9, 75);
                sl2.ScaleTo(0.9, 75);
                sl3.ScaleTo(0.9, 75);
                sl4.ScaleTo(0.9, 75);
                sl5.ScaleTo(1, 75);

                tab1.TextColor = Color.FromHex("#5A297D");
                tab2.TextColor = Color.FromHex("#5A297D");
                tab3.TextColor = Color.FromHex("#5A297D");
                tab4.TextColor = Color.FromHex("#5A297D");
                tab5.TextColor = Color.Orange;
            }
        }

        private async Task FlowAnimateEffect(ContentView view)
        {
            var width = Application.Current.MainPage.Width;

            var storyboard = new Animation();
          
            var flowRight = new Animation(callback: d => view.TranslationX = d,
                                           start: 0,
                                           end: width,
                                           easing: Easing.SpringIn);

            var flowLeft = new Animation(callback: d => view.TranslationX = d,
                                           start: -width,
                                           end: 0,
                                           easing: Easing.BounceOut);
                       
            storyboard.Add(0, 0.5, flowRight);
            storyboard.Add(0.5, 1, flowLeft);
            storyboard.Commit(view, "Loop", length: 1400);
        }

        public void SetPlayKataIndex(int CurrentPlayIndex)
        {
            System.Diagnostics.Debug.WriteLine(CurrentPlayIndex);
        }
        
        protected override bool OnBackButtonPressed()
        {
            /*
            try
            {
                Setting.KataPlayNumber = string.Format("{0}", App.PlayNumberIndex);
            }
            catch { }
            */

            //return true;
            return base.OnBackButtonPressed();
        }

        protected override void OnAppearing()
        {
            base.OnAppearing();

            try
            {
                /*
                //------------ MessagingCenter 해제           

                //스킨 변경
                MessagingCenter.Unsubscribe<MainPage>(this, "SkinStyle");

                //함수에 스트링 콜
                MessagingCenter.Unsubscribe<MainPage, string>(this, "AllView");

                //설정이 끝나면 현재뷰 초기화
                MessagingCenter.Unsubscribe<MainPage>(this, "ChangeSetting");


                //------------ MessagingCenter 설정        

                //스킨 변경
                MessagingCenter.Subscribe<MainPage>(this, "SkinStyle", (sender) =>
                {
                    InitSkinStyle();
                });

                //함수에 스트링 콜
                MessagingCenter.Subscribe<MainPage, string>(this, "AllView", (sender, arg) => {
                    SelectKataAllView(arg);
                });

                //설정이 끝나면 현재뷰 초기화
                MessagingCenter.Subscribe<MainPage>(this, "ChangeSetting", (sender) =>
                {
                    if (ChangeContent.Content.ToString() == "VocaKnow.ListView")
                    {
                        var content = new ListView();
                        ChangeContent.Content = content;
                        SetTabTextColor(1);
                    }
                    else if (ChangeContent.Content.ToString() == "VocaKnow.AllView")
                    {
                        var content = new AllView();
                        ChangeContent.Content = content;
                        SetTabTextColor(2);
                    }
                    else if (ChangeContent.Content.ToString() == "VocaKnow.IngatView")
                    {
                        var content = new IngatView();
                        ChangeContent.Content = content;
                        SetTabTextColor(3);
                    }
                    else if (ChangeContent.Content.ToString() == "VocaKnow.KataKataView")
                    {
                        var content = new KataKataView();
                        ChangeContent.Content = content;
                        SetTabTextColor(4);
                    }
                });
                */

                /*
                string sSavedTime = Setting.UpDateTime;
                string sCurTime = GetCurrentTime();
                if (sSavedTime != sCurTime)
                {
                    //Call AdvPopup
                    Setting.UpDateTime = sCurTime;
                }
                */

                string sVersion = Setting.KataVersion;
                string sKataTime = Setting.KataTime;
                string sPlayNumber = Setting.KataPlayNumber;                
                //Debug.WriteLine("PageName: " + XamarinAppSettings.PageName);
                //Debug.WriteLine("PageName: " + App.CurrentIndex);

            }catch{}
        }

        protected override void OnDisappearing()
        {
            base.OnDisappearing();

            try
            {
                //함수만 콜
                /*          
                MessagingCenter.Unsubscribe<MainPage>(this, "AllView");
                */

                /*
                //함수에 스트링 콜
                MessagingCenter.Unsubscribe<MainPage, string>(this, "AllView");

                //설정이 끝나면 현재뷰 초기화
                MessagingCenter.Unsubscribe<MainPage>(this, "ChangeSetting");

                //스킨 변경
                MessagingCenter.Unsubscribe<MainPage>(this, "SkinStyle");
                */

            } catch { }

        }

    }
}