package ds.id.Bahasa.Common;

import ds.id.Bahasa.Controls.SharedPreferencesUtil;

public class KataSetting {

    private static KataSetting instance;
    public static KataSetting getInstance(){
        if( instance == null){
            instance = new KataSetting();
        }
        return instance;
    }

    //앱 버전
    public void setKataVersion(String sValue){
        SharedPreferencesUtil.getInstance().setValue("KataVersion", sValue);
    }

    public String getKataVersion() {
        String sValue = (String)SharedPreferencesUtil.getInstance().getValue("KataVersion", "");
        return sValue;
    }

    //현재 화면 번호
    public void setKataPlayNumber(String sValue){
        SharedPreferencesUtil.getInstance().setValue("KataPlayNumber", sValue);
    }

    public String getKataPlayNumber() {
        String sValue = (String)SharedPreferencesUtil.getInstance().getValue("KataPlayNumber", "");
        return sValue;
    }

    //자동 반복 시간
    public void setKataTime(int nValue){
        SharedPreferencesUtil.getInstance().setValue("KataTime", nValue);
    }

    public int getKataTime() {
        int nValue = (int)SharedPreferencesUtil.getInstance().getValue("KataTime", 10);
        return nValue;
    }

    public void setWordKataTime(int nValue){
        SharedPreferencesUtil.getInstance().setValue("WordKataTime", nValue);
    }

    public int getWordKataTime() {
        int nValue = (int)SharedPreferencesUtil.getInstance().getValue("WordKataTime", 10);
        return nValue;
    }

    //문장 사운드 지원
    public void setKalimatSound(Boolean bValue){
        SharedPreferencesUtil.getInstance().setValue("KalimatSound", bValue);
    }

    public Boolean getKalimatSound() {
        Boolean bValue = (Boolean)SharedPreferencesUtil.getInstance().getValue("KalimatSound", false);
        return bValue;
    }

    //단어장 사운드 지원
    public void setKataSound(Boolean bValue){
        SharedPreferencesUtil.getInstance().setValue("kataSound", bValue);
    }

    public Boolean getKataSound() {
        Boolean bValue = (Boolean)SharedPreferencesUtil.getInstance().getValue("kataSound", false);
        return bValue;
    }

    //스크린 화면 항상켜기
    public void setScreenLock(Boolean bValue){
        SharedPreferencesUtil.getInstance().setValue("ScreenLock", bValue);
    }

    public Boolean getScreenLock() {
        Boolean bValue = (Boolean)SharedPreferencesUtil.getInstance().getValue("ScreenLock", false);
        return bValue;
    }

    //광고 하루에 한번만
    public void setUpDateTime(String sValue){
        SharedPreferencesUtil.getInstance().setValue("AdvUpdateTime", sValue);
    }

    public String getUpDateTime() {
        String sValue = (String)SharedPreferencesUtil.getInstance().getValue("AdvUpdateTime", "");
        return sValue;
    }

    //설명문 한번만
    public void setIntroPopup(Boolean bValue){
        SharedPreferencesUtil.getInstance().setValue("Intro", bValue);
    }

    public Boolean getIntroPopup() {
        Boolean bValue = (Boolean)SharedPreferencesUtil.getInstance().getValue("Intro", false);
        return bValue;
    }

    //스킨 타입
    public void setSkinStyle(int nValue){
        SharedPreferencesUtil.getInstance().setValue("SkinStyle", nValue);
    }

    public int getSkinStyle() {
        int nValue = (int)SharedPreferencesUtil.getInstance().getValue("SkinStyle", 0);
        return nValue;
    }

    //자동 반복 횟수
    public void setKataTimeRepeat(int nValue){
        SharedPreferencesUtil.getInstance().setValue("KataTimeRepeat", nValue);
    }

    public int getKataTimeRepeat() {
        int nValue = (int)SharedPreferencesUtil.getInstance().getValue("KataTimeRepeat", 0);
        return nValue;
    }

    public void setWordKataTimeRepeat(int nValue){
        SharedPreferencesUtil.getInstance().setValue("WordKataTimeRepeat", nValue);
    }

    public int getWordKataTimeRepeat() {
        int nValue = (int)SharedPreferencesUtil.getInstance().getValue("WordKataTimeRepeat", 10);
        return nValue;
    }

    public void deleteKataSetting(String sKey){
        SharedPreferencesUtil.getInstance().remove(sKey);
    }
}
