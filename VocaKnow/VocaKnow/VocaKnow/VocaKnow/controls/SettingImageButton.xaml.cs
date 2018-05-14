using System;
using Xamarin.Forms;
using Xamarin.Forms.Xaml;

namespace VocaKnow.controls
{
    [XamlCompilation(XamlCompilationOptions.Compile)]
    public partial class SettingImageButton : ContentView
    {
        public string Text
        {
            set { lblEditor.Text = value; }
        }


        public event EventHandler Click;
        
        public SettingImageButton()
        {
            InitializeComponent();

            var columnDefinitions = SettingImageButtonView.ColumnDefinitions;
            columnDefinitions[1].Width = 0;

            this.WidthRequest = 240;
            
            this.AddTouchHandler(this, this.OnClick);
        }        

        private void OnClick()
        {
            Click?.Invoke(this, EventArgs.Empty);
        }

        protected void AddTouchHandler(View view, Action action)
        {
            view.GestureRecognizers.Add(new TapGestureRecognizer
            {
                Command = new Command(() =>
                {
                    view.Opacity = 0.6;
                    view.FadeTo(1);                    
                    action();
                })
            });
        }

        //private void FloatingActionButton_Clicked(object sender, EventArgs e)
        //{
        //    OnClick();
        //}
    }
}