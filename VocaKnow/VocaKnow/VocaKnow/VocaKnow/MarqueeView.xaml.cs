using System;
using Xamarin.Forms;
using Xamarin.Forms.Xaml;

namespace VocaKnow
{
    [XamlCompilation(XamlCompilationOptions.Compile)]
    public partial class MarqueeView : ContentView
    {
        private bool bMoveLeft = false;     

        string sContent { get; set; }
        public String ContentMarquee
        {
            get
            {
                if (sContent == "" || sContent == null)
                    sContent = "play 버튼을 클릭하시면 10초마다 자동 실행합니다.";

                return sContent;
            }
            set
            {
                if (!string.IsNullOrEmpty(value))
                {
                    sContent = value.Replace("\n", " ");
                    Ticker.Text = sContent;
                }
            }
        }


        public MarqueeView()
        {
            InitializeComponent();

            Device.StartTimer(TimeSpan.FromSeconds(10), OnTimerTick);
        }

        private bool OnTimerTick()
        {
            playMarquee();
            return true;
        }

        public static Animation TransLateXAnimation(VisualElement element, double from, double to)
        {
            return new Animation(d => { element.TranslationX = d; }, from, to);
        }

        private async void playMarquee()
        {
            //ContentMarquee = ContentMarquee.Substring(1) + ContentMarquee.Substring(0, 1);
                        
            if (bMoveLeft)
            {
                bMoveLeft = false;
                var ani1 = TransLateXAnimation(Ticker, 1000, 0);
                this.Animate("tranx", ani1, 16, 1000, Easing.Linear, (d, f) => { });
            }
            else
            {
                bMoveLeft = true;
                var ani1 = TransLateXAnimation(Ticker, -1000, 0);
                this.Animate("tranx", ani1, 16, 1000, Easing.Linear, (d, f) => { });
            }

            /*
            Ticker.IsVisible = false;
            Ticker.TranslationX = 1000;
            Ticker.IsVisible = true;

            //await Task.Delay(1500);

            await Task.WhenAny<bool>
            (
                Ticker.TranslateTo(0, 0, 1000)
            );
            */
        }

    }
}