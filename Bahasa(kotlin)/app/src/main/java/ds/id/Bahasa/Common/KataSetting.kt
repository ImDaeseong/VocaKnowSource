package ds.id.Bahasa.Common

import ds.id.Bahasa.Controls.SharedPreferencesUtil

class KataSetting {

    private val tag: String = KataSetting::class.java.simpleName

    companion object {
        private var instance: KataSetting? = null
        fun getInstance(): KataSetting {
            if (instance == null) {
                instance = KataSetting()
            }
            return instance as KataSetting
        }
    }

    //앱 버전
    var kataVersion: String?
        get() = SharedPreferencesUtil.getInstance().getValue("KataVersion", "") as String?
        set(sValue) {
            SharedPreferencesUtil.getInstance().setValue("KataVersion", sValue!!)
        }

    //현재 화면 번호
    var kataPlayNumber: String?
        get() = SharedPreferencesUtil.getInstance().getValue("KataPlayNumber", "") as String?
        set(sValue) {
            SharedPreferencesUtil.getInstance().setValue("KataPlayNumber", sValue!!)
        }

    //자동 반복 시간
    var kataTime: Int
        get() = SharedPreferencesUtil.getInstance().getValue("KataTime", 10) as Int
        set(nValue) {
            SharedPreferencesUtil.getInstance().setValue("KataTime", nValue)
        }

    var wordKataTime: Int
        get() = SharedPreferencesUtil.getInstance().getValue("WordKataTime", 10) as Int
        set(nValue) {
            SharedPreferencesUtil.getInstance().setValue("WordKataTime", nValue)
        }

    //문장 사운드 지원
    var kalimatSound: Boolean?
        get() = SharedPreferencesUtil.getInstance().getValue("KalimatSound", false) as Boolean?
        set(bValue) {
            SharedPreferencesUtil.getInstance().setValue("KalimatSound", bValue!!)
        }

    //단어장 사운드 지원
    var kataSound: Boolean?
        get() = SharedPreferencesUtil.getInstance().getValue("kataSound", false) as Boolean?
        set(bValue) {
            SharedPreferencesUtil.getInstance().setValue("kataSound", bValue!!)
        }

    //스크린 화면 항상켜기
    var screenLock: Boolean?
        get() = SharedPreferencesUtil.getInstance().getValue("ScreenLock", false) as Boolean?
        set(bValue) {
            SharedPreferencesUtil.getInstance().setValue("ScreenLock", bValue!!)
        }

    //광고 하루에 한번만
    var upDateTime: String?
        get() = SharedPreferencesUtil.getInstance().getValue("AdvUpdateTime", "") as String?
        set(sValue) {
            SharedPreferencesUtil.getInstance().setValue("AdvUpdateTime", sValue!!)
        }

    //설명문 한번만
    var introPopup: Boolean?
        get() = SharedPreferencesUtil.getInstance().getValue("Intro", false) as Boolean?
        set(bValue) {
            SharedPreferencesUtil.getInstance().setValue("Intro", bValue!!)
        }

    //스킨 타입
    var skinStyle: Int
        get() = SharedPreferencesUtil.getInstance().getValue("SkinStyle", 0) as Int
        set(nValue) {
            SharedPreferencesUtil.getInstance().setValue("SkinStyle", nValue)
        }

    //자동 반복 횟수
    var kataTimeRepeat: Int
        get() = SharedPreferencesUtil.getInstance().getValue("KataTimeRepeat", 0) as Int
        set(nValue) {
            SharedPreferencesUtil.getInstance().setValue("KataTimeRepeat", nValue)
        }

    var wordKataTimeRepeat: Int
        get() = SharedPreferencesUtil.getInstance().getValue("WordKataTimeRepeat", 10) as Int
        set(nValue) {
            SharedPreferencesUtil.getInstance().setValue("WordKataTimeRepeat", nValue)
        }

    fun deleteKataSetting(sKey: String?) {
        SharedPreferencesUtil.getInstance().remove(sKey)
    }
}
