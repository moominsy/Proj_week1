package com.example.tabtest2;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ViewHolderPage extends RecyclerView.ViewHolder{

    Word word;
    private TextView textview;
    private RelativeLayout rl_layout;
    private Boolean isEng = true;
    public ViewHolderPage(@NonNull View itemView) {
        super(itemView);
        textview = itemView.findViewById(R.id.textview);
        rl_layout = itemView.findViewById(R.id.rl_layout);

        rl_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(word != null ) {
                    if (isEng) {
                        textview.setText(word.getKor());
                        isEng = false;
                    } else {
                        textview.setText(word.getEng());
                        isEng = true;
                    }
                }
            }
        });
    }

    public void onBind(Word word){
        this.word = word;
        isEng = true;
        textview.setText(word.getEng());
    }
}
