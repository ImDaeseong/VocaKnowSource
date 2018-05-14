using System;
using Xamarin.Forms;
using Xamarin.Forms.Xaml;
using System.Diagnostics;
using System.Threading.Tasks;

namespace VocaKnow
{
    [XamlCompilation(XamlCompilationOptions.Compile)]
    public partial class ListViewCell : ContentView
    {
        private controls.UserSetting Setting = controls.UserSetting.getInstance;

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
            
            frameViewCell.BackgroundColor = CurSkin;
        }

        public ListViewCell()
        {
            InitializeComponent();

            //스킨 설정
            InitSkinStyle();
        }

        private async void TapGestureRecognizer_Tapped(object sender, EventArgs e)
        {
            try
            {
                var selectedFrameItem = sender as Frame;
                if (selectedFrameItem != null)
                {
                    Label selectedItem = new Label();
                    string sMenuView = "";
                    var fContent = selectedFrameItem.Content;
                    if (fContent.GetType() == typeof(Grid))
                    {
                        Grid fGridlayout = (Grid)fContent;
                        foreach (var item in fGridlayout.Children)
                        {
                            if (item.GetType() == typeof(Label))
                            {
                                //Label lLabel = (Label)item;                                
                                //sMenuView = lLabel.Text;

                                selectedItem = (Label)item;
                                sMenuView = selectedItem.Text;
                                break;
                            }
                        }
                    }

                    if (sMenuView != "")
                    {
                        await AnimateLabel(selectedItem);

                        //await AnimateFrame(selectedFrameItem);

                        //함수에 스트링 전달
                        MessagingCenter.Send<MainPage, string>(MainPage.GetMainPageInstance(), "AllView", sMenuView);
                    }
                    selectedItem = null;
                }
                else
                {
                    var selectedItem = sender as Label;
                    if (selectedItem != null)
                    {  
                        await AnimateLabel(selectedItem);

                        //함수에 스트링 전달
                        MessagingCenter.Send<MainPage, string>(MainPage.GetMainPageInstance(), "AllView", selectedItem.Text);

                        //함수만 호출
                        //MessagingCenter.Send(MainPage.GetMainPageInstance(), "AllView");      
                    }
                    selectedItem = null;
                }

                selectedFrameItem = null;
            }
            catch
            {
                Debug.WriteLine("ListViewCell_ItemTapped Error");
            }
        }
        
        private async Task<bool> AnimateLabel(Label sl)
        {           
            await sl.RotateTo(360, 200).ContinueWith(task =>
            {
                sl.ScaleTo(1d, 1, Easing.CubicIn);
                sl.FadeTo(1d, 50);
            });
            return true;
            
            /*
            await sl.ScaleTo(1.1d, 100, Easing.CubicOut);//await sl.ScaleTo(1.5d, 100, Easing.CubicOut);
            await sl.FadeTo(0, 100).ContinueWith(task =>
            {
                sl.ScaleTo(1d, 1, Easing.CubicIn);
                sl.FadeTo(1d, 50);
            });
            return true;
            */
        }

        private async Task<bool> AnimateFrame(Frame sl)
        {            
            await sl.ScaleTo(0.95, 50, Easing.Linear);
            await Task.Delay(10);
            await sl.ScaleTo(1, 50, Easing.Linear);
            return true;
            
            /*
            await sl.ScaleTo(0.8, 50, Easing.Linear);
            await Task.Delay(100);
            await sl.ScaleTo(1, 50, Easing.Linear);              
            return true;
            */
        }
    }
}