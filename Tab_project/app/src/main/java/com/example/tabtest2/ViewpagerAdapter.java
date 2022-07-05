package com.example.tabtest2;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ViewpagerAdapter extends RecyclerView.Adapter<ViewHolderPage>{
    private ArrayList<Word> wordlist;

    public ViewpagerAdapter(ArrayList<Word> wordlist) {
        this.wordlist = wordlist;
    }


    @Override
    public ViewHolderPage onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.viewpage, parent, false);
        return new ViewHolderPage(view);
    }

    @Override
    public void onBindViewHolder(ViewHolderPage holder, int position) {
        if(holder instanceof ViewHolderPage){
            ViewHolderPage viewHolder = (ViewHolderPage) holder;
            viewHolder.onBind(wordlist.get(position));
            //Log.i("bind", Integer.toString(position));
        }
    }

    @Override
    public int getItemCount() {
        return wordlist.size();
    }

    public void AddItem(Word word){
        wordlist.add(word);
        notifyDataSetChanged();
    }
    public void DeleteItem(int idx){
        wordlist.remove(idx);
        notifyDataSetChanged();
    }

}
