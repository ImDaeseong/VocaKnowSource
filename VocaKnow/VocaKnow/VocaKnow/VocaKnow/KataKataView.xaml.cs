using System;
using System.Collections.ObjectModel;
using System.Diagnostics;
using System.Threading.Tasks;
using VocaKnow.controls;
using VocaKnow.Interfaces;
using Xamarin.Forms;
using Xamarin.Forms.Xaml;

namespace VocaKnow
{
    [XamlCompilation(XamlCompilationOptions.Compile)]
    public partial class KataKataView : ContentView
    {
        private string sKataIndo;
        private string sKataIndoTambah;
        private string sKataKor;
        
        private bool bChangColor = false;

        private KataDatabase kataPlay = KataDatabase.getInstance;
        private ObservableCollection<RegKataItems> mCollection;

        private UserSetting Setting = UserSetting.getInstance;
        private AlertMsg Alert = AlertMsg.getInstance;

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
            
            eSearch.BorderColor = CurSkin;// Color.White;
        }

        public KataKataView()
        {
            InitializeComponent();

            //스킨 설정
            InitSkinStyle();

            stackList.Children.Clear();

            this.SizeChanged += KataKataView_SizeChanged;
        }

        private void eSearch_TextChanged(object sender, TextChangedEventArgs e)
        {
            try
            {
                var eSearch = (Entry)sender;
                string sSearch = eSearch.Text;

                if (sSearch == "") return;

                var ItemTapped = new TapGestureRecognizer();
                ItemTapped.Tapped += ItemTapped_Tapped;

                stackList.Children.Clear();

                mCollection = new ObservableCollection<RegKataItems>(kataPlay.GetRegSearchKata(sSearch));

                foreach (RegKataItems data in mCollection)
                {
                    var item = new MyKataCellView(data.KataKor, data.KataIndo, data.KataIndoTambah);
                    item.GestureRecognizers.Add(ItemTapped);
                    stackList.Children.Add(item);
                }
                mCollection.Clear();

                SetbtnKataAddVisible(false);
            }
            catch (Exception ex)
            {
                Debug.WriteLine(ex.Message.ToString());
            }
        }

        private void LoadAllKataData()
        {
            try
            {
                var ItemTapped = new TapGestureRecognizer();
                ItemTapped.Tapped += ItemTapped_Tapped;

                stackList.Children.Clear();

                mCollection = new ObservableCollection<RegKataItems>(kataPlay.GetRegItems());

                foreach (RegKataItems data in mCollection)
                {
                    var item = new MyKataCellView(data.KataKor, data.KataIndo, data.KataIndoTambah);
                    item.GestureRecognizers.Add(ItemTapped);
                    stackList.Children.Add(item);
                }
                mCollection.Clear();
            }
            catch { }
        }

        private void ItemTapped_Tapped(object sender, EventArgs e)
        {
            try
            {
                var item = (MyKataCellView)sender;

                var slItem = item.Parent as StackLayout;
                if (slItem != null)
                {
                    foreach (var viewitem in slItem.Children)
                    {
                        viewitem.BackgroundColor = Color.White;
                    }
                }
                
                if (item.KataIndo == sKataIndo)
                {
                    item.BackgroundColor = Color.White;
                    SetBtnKataEditEnable(false);

                    sKataIndo = "";
                    sKataIndoTambah = "";
                    sKataKor = "";
                }
                else
                {
                    item.BackgroundColor = Color.FromHex("#FFC10E");
                    SetBtnKataEditEnable(true);

                    sKataIndo = item.KataIndo;
                    sKataIndoTambah = item.KataIndoTambah;
                    sKataKor = item.KataKor;
                }
            }
            catch {}
        }
        
        protected override void InvalidateLayout()
        {
            base.InvalidateLayout();
        }

        private void KataKataView_SizeChanged(object sender, EventArgs e)
        {
            SetLocationLabelVisible();

            this.UpdateChildrenLayout();
            this.InvalidateLayout();
        }

        private void SetLocationLabelVisible()
        {
            /*
            RelativeLayout.SetBoundsConstraint(btnchange,
             BoundsConstraint.FromExpression(
                 () => new Rectangle(
                     ((relLayout.Width - btnchange.Width) - 18),
                     ((relLayout.Height - btnchange.Height) - 15),
                     btnchange.Width,
                     btnchange.Height),
             null));

            if (this.Width > this.Height)
            {
                RelativeLayout.SetBoundsConstraint(btnKataAdd,
                BoundsConstraint.FromExpression(
                    () => new Rectangle(
                        (btnchange.X - btnKataAdd.Width),
                        (btnchange.Y),
                        btnKataAdd.Width,
                        btnKataAdd.Height),
                null));

                RelativeLayout.SetBoundsConstraint(btnKataEdit,
                 BoundsConstraint.FromExpression(
                     () => new Rectangle(
                         (btnKataAdd.X - btnKataEdit.Width),
                         (btnchange.Y),
                         btnKataEdit.Width,
                         btnKataEdit.Height),
                 null));
            }
            else
            {
                RelativeLayout.SetBoundsConstraint(btnKataAdd,
                  BoundsConstraint.FromExpression(
                      () => new Rectangle(
                          (btnchange.X),
                          (btnchange.Y - btnKataAdd.Height),
                          btnKataAdd.Width,
                          btnKataAdd.Height),
                  null));

                RelativeLayout.SetBoundsConstraint(btnKataEdit,
                  BoundsConstraint.FromExpression(
                      () => new Rectangle(
                          (btnchange.X),
                          (btnKataAdd.Y - btnKataEdit.Height),
                          btnKataEdit.Width,
                          btnKataEdit.Height),
                  null));
            }   
            */                        
        }

        protected override void OnSizeAllocated(double width, double height)
        {
            base.OnSizeAllocated(width, height);
        }

        private async void FloatingActionButton_Clicked(object sender, EventArgs e)
        {
            await AnimateFloatingActionButton(btnchange);

            if (bChangColor)
            {
                eSearch.Text = "";
                stackList.Children.Clear();

                SetbtnKataAddVisible(false);

                bChangColor = false;
                btnchange.ButtonColor = Color.Gray;

                SetBtnKataEditEnable(false);
            }
            else
            {
                eSearch.Text = "";
                LoadAllKataData();

                SetbtnKataAddVisible(true); 

                bChangColor = true;
                btnchange.ButtonColor = CurSkin;// Color.FromHex("#33A7D6");

                SetBtnKataEditEnable(false);
            }

            SetLocationLabelVisible(); 
        }

        private async Task<bool> AnimateFloatingActionButton(FloatingActionButton sl)
        {
            await sl.ScaleTo(0.95, 50, Easing.CubicOut);
            await sl.ScaleTo(1, 50, Easing.CubicIn);
            return true;
        }
                
        private void SetbtnKataAddVisible(bool bVisible)
        {
            btnKataAdd.IsVisible = bVisible;
            btnKataEdit.IsVisible = bVisible;            
        }

        private void SetBtnKataEditEnable(bool bVisible)
        {
            if (bVisible)
            {
                btnKataEdit.ButtonColor = Color.FromHex("#FFC10E");
                btnKataEdit.IsEnabled = true;          
            }
            else
            {
                btnKataEdit.ButtonColor = Color.Gray;
                btnKataEdit.IsEnabled = false;
            }            
        }

        private async void btnKataAdd_Clicked(object sender, EventArgs e)
        {
            try
            {
                await AnimateFloatingActionButton(btnKataAdd);

                eSearch.Text = "";
                stackList.Children.Clear();

                SetbtnKataAddVisible(false);

                bChangColor = false;
                btnchange.ButtonColor = Color.Gray;

                SetBtnKataEditEnable(false);

                await Navigation.PushModalAsync(new NewKataPage(true, sKataIndo, sKataIndoTambah, sKataKor));

                sKataIndo = "";
                sKataIndoTambah = "";
                sKataKor = "";
            }
            catch { }
        }

        private async void btnKataEdit_Clicked(object sender, EventArgs e)
        {
            try
            {
                await AnimateFloatingActionButton(btnKataEdit);

                eSearch.Text = "";
                stackList.Children.Clear();

                SetbtnKataAddVisible(false);

                bChangColor = false;
                btnchange.ButtonColor = Color.Gray;

                SetBtnKataEditEnable(false);

                await Navigation.PushModalAsync(new NewKataPage(false, sKataIndo, sKataIndoTambah, sKataKor));

                sKataIndo = "";
                sKataIndoTambah = "";
                sKataKor = "";
            }
            catch { }
        }

    }
}