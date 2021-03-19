package ds.id.Bahasa;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

import ds.id.Bahasa.Database.regkataItems;

public class RegKataAdapter extends RecyclerView.Adapter<RegKataAdapter.ViewHolder> {

    private static final String TAG = RegKataAdapter.class.getSimpleName();

    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

    private OnItemClickListener listener = null;
    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }


    private Context context;
    private List<regkataItems> list;
    private int focusedItem = -1;

    public RegKataAdapter(Context context, List<regkataItems> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.kata_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if(focusedItem > -1) {

            //아이템 선택/비선택 색상
            holder.itemView.setSelected(focusedItem == position);

            if (focusedItem == position)
                holder.itemView.setBackgroundColor(Color.parseColor("#FFC10E"));
            else
                holder.itemView.setBackgroundColor(Color.parseColor("#EDEDED"));
        }

        String stv1 = String.format("%s", list.get(position).getKataIndo());
        String stv2 = String.format("%s", list.get(position).getKataIndoTambah());
        String stv3 = String.format("%s", list.get(position).getKataKor());
        holder.tv1.setText(stv1);
        holder.tv2.setText(stv2);
        holder.tv3.setText(stv3);
    }

    @Override
    public int getItemCount() {
        return (list != null) ? list.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        protected TextView tv1;
        protected TextView tv2;
        protected TextView tv3;

        public ViewHolder(View v){
            super(v);

            this.tv1 = (TextView)v.findViewById(R.id.tv1);
            this.tv2 = (TextView)v.findViewById(R.id.tv2);
            this.tv3 = (TextView)v.findViewById(R.id.tv3);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition() ;
                    if (pos != RecyclerView.NO_POSITION) {

                        notifyItemChanged(focusedItem);
                        focusedItem = getLayoutPosition();
                        notifyItemChanged(focusedItem);
                        //focusedItem = pos;
                        //regkataItems item = list.get(pos);
                        //Log.e(TAG, "item:" + item);

                        if (listener != null) {
                            listener.onItemClick(v, pos);
                        }
                    }
                }
            });

        }
    }
}
