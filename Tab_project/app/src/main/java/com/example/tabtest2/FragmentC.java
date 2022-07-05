package com.example.tabtest2;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentC#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentC extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    MainActivity mainActivity;
    public FragmentC() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentA.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentC newInstance(String param1, String param2) {
        FragmentC fragment = new FragmentC();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity)getActivity();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        setHasOptionsMenu(true);
        //Log.i("onCreate", "");
    }


    ViewpagerAdapter wordadapter;
    ViewPager2 viewpager;
    ArrayList<Word> wordlist = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        ViewGroup rootview = (ViewGroup)inflater.inflate(R.layout.fragment_c,container,false);


        viewpager = rootview.findViewById(R.id.viewPager2);
        wordadapter = new ViewpagerAdapter(wordlist);
        viewpager.setAdapter(wordadapter);
        //Log.i("onCreateView", "");
        return rootview;
    }

    public void Initwords(MainActivity mainActivity){
        jsonParsing(getJsonString(mainActivity));
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_option, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.Add_option:
                View dialogView = getLayoutInflater().inflate(R.layout.add_dialog, null);
                final EditText EnglishEditText = (EditText)dialogView.findViewById(R.id.edittext_english);
                final EditText KoreanEditText = (EditText)dialogView.findViewById(R.id.edittext_korean);

                AlertDialog.Builder builder = new AlertDialog.Builder(mainActivity);
                builder.setView(dialogView);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int pos)
                    {
                        String english =  EnglishEditText.getText().toString();
                        String korean = KoreanEditText.getText().toString();
                        wordadapter.AddItem(new Word(english, korean));
                        Toast.makeText(mainActivity.getApplicationContext(),"Successfuly added", Toast.LENGTH_LONG).show();
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();

                break;

            case R.id.Delete_option:
                Toast.makeText(mainActivity, "Successfully Deleted", Toast.LENGTH_SHORT).show();
                wordadapter.DeleteItem(viewpager.getCurrentItem());
                break;

            case R.id.Save_option:
                Toast.makeText(mainActivity, "Words are saved", Toast.LENGTH_SHORT).show();
                WritetoJson(WordtoJson());
                break;
        }

        return super.onOptionsItemSelected(item);

    }


    private String getJsonString(MainActivity mainActivity)
    {
        String json = "";

        try {
            FileInputStream fis = mainActivity.openFileInput("Words.json");
            DataInputStream dis = new DataInputStream(fis);
            json = dis.readUTF();
            dis.close();
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }

        return json;
    }

    private void jsonParsing(String json)
    {
        try{
            JSONObject jsonObject = new JSONObject(json);

            JSONArray wordArray = jsonObject.getJSONArray("Words");
            for(int i=0; i<wordArray.length(); i++)
            {
                JSONObject wordObject = wordArray.getJSONObject(i);

                Word word = new Word();

                //Log.w("json", Integer.toString(i));
                word.setEng(wordObject.getString("Eng"));
                word.setKor(wordObject.getString("Kor"));

                wordlist.add(word);
            }

        }catch (JSONException e) {
            e.printStackTrace();
            //wordlist.add(new Word("apple", "사과"));
        }
    }

    private JSONObject WordtoJson() {
        JSONObject jsonObject = new JSONObject();
        JSONArray wordArray = new JSONArray();

        try {
            for (int i = 0; i < wordlist.size(); i++) {
                JSONObject wordObject = new JSONObject();

                Word word = wordlist.get(i);
                wordObject.put("Eng", word.getEng());
                wordObject.put("Kor", word.getKor());
                wordArray.put(wordObject);
            }
            jsonObject.put("Words", wordArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
    private void WritetoJson(JSONObject jsonObject) {
        try {
            FileOutputStream fos = mainActivity.openFileOutput("Words.json", mainActivity.MODE_PRIVATE);
            DataOutputStream dos = new DataOutputStream(fos);
            Log.i("save", jsonObject.toString());
            dos.writeUTF(jsonObject.toString());

            dos.flush();
            dos.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }



}