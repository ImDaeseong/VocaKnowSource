package ds.id.Bahasa.Common;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import ds.id.Bahasa.R;

public class gMenuItem extends ConstraintLayout {

    private TextView tv1;
    private String sText;

    public gMenuItem(Context context){
        super(context);
        init(context);
    }

    public gMenuItem(Context context, AttributeSet attributeSet){
        super(context, attributeSet);
        init(context);
    }

    public gMenuItem(Context context, AttributeSet attributeSet, int defStyle) {
        super(context, attributeSet, defStyle);
        init(context);
    }

    private void init(Context context){
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.menuitem, this, true);
        tv1 = (TextView)findViewById(R.id.tv1);
    }

    public void setSkinResource(int nResource){
        setBackgroundResource(nResource);
    }

    public void setText(String sText){
        this.sText = sText;
        tv1.setText(sText);
    }

    public String getText() {
        return sText;
    }
}