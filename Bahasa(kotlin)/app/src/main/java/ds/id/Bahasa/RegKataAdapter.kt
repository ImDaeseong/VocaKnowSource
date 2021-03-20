package ds.id.Bahasa

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ds.id.Bahasa.Database.regkataItems


class RegKataAdapter(private val context: Context, private val list: List<regkataItems>?) : RecyclerView.Adapter<RegKataAdapter.ViewHolder>() {

    private val tag = RegKataAdapter::class.java.simpleName

    interface OnItemClickListener {
        fun onItemClick(v: View?, position: Int)
    }

    private var listener: OnItemClickListener? = null
    fun setOnItemClickListener(listener: OnItemClickListener?) {
        this.listener = listener
    }

    private var focusedItem = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.kata_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if (focusedItem > -1) {

            //아이템 선택/비선택 색상
            holder.itemView.isSelected = focusedItem == position
            if (focusedItem == position)
                holder.itemView.setBackgroundColor(Color.parseColor("#FFC10E"))
            else
                holder.itemView.setBackgroundColor(Color.parseColor("#EDEDED")
            )
        }

        val stv1 = String.format("%s", list!![position].kataIndo)
        val stv2 = String.format("%s", list[position].kataIndoTambah)
        val stv3 = String.format("%s", list[position].kataKor)
        holder.tv1.text = stv1
        holder.tv2.text = stv2
        holder.tv3.text = stv3
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {

        var tv1: TextView = v.findViewById<View>(R.id.tv1) as TextView
        var tv2: TextView = v.findViewById<View>(R.id.tv2) as TextView
        var tv3: TextView = v.findViewById<View>(R.id.tv3) as TextView

        init {
            v.setOnClickListener { v ->

                val pos = adapterPosition

                if (pos != RecyclerView.NO_POSITION) {

                    notifyItemChanged(focusedItem)
                    focusedItem = layoutPosition
                    notifyItemChanged(focusedItem)

                    if (listener != null) {
                        listener!!.onItemClick(v, pos)
                    }
                }
            }
        }
    }

}
