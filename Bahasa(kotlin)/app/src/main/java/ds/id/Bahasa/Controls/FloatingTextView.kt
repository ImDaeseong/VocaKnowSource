package ds.id.Bahasa.Controls

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import ds.id.Bahasa.R

class FloatingTextView(viewGroup: ViewGroup) {

    private val floatingview: View = LayoutInflater.from(viewGroup.context).inflate(R.layout.floating_layout, viewGroup,false)
    private val cLClose: View
    private val tv1: TextView
    private val tv2: TextView

    init {

        tv1 = floatingview.findViewById<TextView>(R.id.tv1)
        tv2 = floatingview.findViewById<TextView>(R.id.tv2)
        cLClose = floatingview.findViewById<View>(R.id.cLClose)
        cLClose.setOnClickListener {

            floatingview.visibility = View.GONE
        }
    }

    fun getFloatingview(): View? {
        return floatingview
    }

    fun setText1(sText: String?, color: Int) {
        tv1.text = sText
        tv1.setTextColor(color)
    }

    fun setText2(sText: String?, color: Int) {
        tv2.text = sText
        tv2.setTextColor(color)
    }
}