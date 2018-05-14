using Xamarin.Forms;
using Xamarin.Forms.Xaml;

namespace VocaKnow
{
    [XamlCompilation(XamlCompilationOptions.Compile)]
    public partial class ListView : ContentView
    {     
        public ListView()
        {
            InitializeComponent();

            LoadListData();            
        }
                
        private void LoadListData()
        {
            string[] titlelist =
            {
                "일상생활/인사,소개",
                "일상생활/날씨,여가",
                "일상생활/시장,교통",
                "일상생활/문화,관습",
                "일상생활/건강,병원",
                "일상생활/음식,식생활",
                "일상생활/은행,송금",
                "일상생활/기타",
                "작업지시/근무시작,끝",
                "작업지시/근무태도",
                "작업지시/작업공구",
                "작업지시/안전규칙",
                "작업지시/작업규칙등기타",
                "기숙사및식당/기숙사안내",
                "기숙사및식당/기숙사규칙",
                "기숙사및식당/기숙사환경",
                "기숙사및식당/식당안내",
                "기숙사및식당/식당규칙",
                "근로관련/급여,수당",
                "근로관련/회사,근무규칙",
                "근로관련/채권채무",
                "근로관련/고용허가서등",
                "근로관련/각종보험",
                "근로관련/기타",
                "고용관련신고/입사",
                "고용관련신고/퇴사",
                "고용관련신고/사업장변경",
                "고용관련신고/출입국",
                "고용관련신고/고용지원센터",
                "고용관련신고/기타사항"
            };

            for (int i = 0; i < titlelist.Length; i++)
            {
                var column = layout0;
                var item = new ListViewCell();
                item.BindingContext = new KataFolder(titlelist[i]);

                if (i % 2 == 0)
                    column = layout0;
                else
                    column = layout1;
                column.Children.Add(item);
            }            
        }   
        
        protected override void InvalidateLayout()
        {
            base.InvalidateLayout();
        }
        
        protected override void OnSizeAllocated(double width, double height)
        {
            base.OnSizeAllocated(width, height);            
        }
    }

    class KataFolder
    {
        public string sName { get; set; }
        public KataFolder(string Name)
        {
            this.sName = Name;
        }
    }
}