using System;

namespace VocaKnow.Interfaces
{
    public interface IKataSetting
    {
        //앱 버전
        string KataVersion { get; set; }

        //현재 화면 번호
        string KataPlayNumber { get; set; }

        //자동 반복 시간
        string KataTime { get; set; }

        string WordKataTime { get; set; }

        //문장 사운드 지원
        string KalimatSound { get; set; }

        //단어장 사운드 지원
        string KataSound { get; set; }

        //스크린 화면 항상켜기
        string ScreenLock { get; set; }

        //광고 하루에 한번만
        string UpDateTime { get; set; }

        //설명문 한번만
        bool IntroPopup { get; set; }

        //스킨 타입
        int SkinStyle { get; set; }

        //자동 반복 횟수
        int KataTimeRepeat { get; set; }
        int WordKataTimeRepeat { get; set; }
    }
}
