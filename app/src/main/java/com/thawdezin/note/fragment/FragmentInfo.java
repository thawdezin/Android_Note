package com.thawdezin.note.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.thawdezin.note.R;
import com.thawdezin.note.activities.InfoPresidentsSeeAllActivity;
import com.thawdezin.note.activities.MainActivity;
import com.thawdezin.note.adapters.InfoAdapter;
import com.thawdezin.note.data.vos.AmericanPresidents;
import com.thawdezin.note.utils.MyConstants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FragmentInfo extends BaseFragment {
    private static RecyclerView recyclerView;
    private static List<AmericanPresidents> presidentsSeeAllList = new ArrayList<>();
    private static List<AmericanPresidents> presidentsSeeAllListWithYear = new ArrayList<>();

    public static List<AmericanPresidents> getPresidentsSeeAllListWithYear() {
        return presidentsSeeAllListWithYear;
    }

    public static List<AmericanPresidents> getPresidentsSeeAllList() {
        return presidentsSeeAllList;
    }

    @Override
    public void onStart() {
        super.onStart();
        MainActivity.getFloatingActionButton().hide();
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity.setCurrentFragment(MyConstants.currentInfo);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        recyclerView = view.findViewById(R.id.info_recycler_view);
        TextView tvInfoSeeAll = view.findViewById(R.id.tv_info_see_all);
        tvInfoSeeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), InfoPresidentsSeeAllActivity.class);
                startActivity(intent);
            }
        });
        setUpRecyclerView();
        return view;

    }

    private void setUpRecyclerView() {
        presidentsSeeAllList = loadJSONFromAsset();
        InfoAdapter infoAdapter = new InfoAdapter(presidentsSeeAllList);
        recyclerView.setAdapter(infoAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
    }

    private ArrayList<AmericanPresidents> loadJSONFromAsset() {
        ArrayList<AmericanPresidents> locList = new ArrayList<>();
        String json;
        try {
            InputStream is = Objects.requireNonNull(getActivity()).getAssets().open("american_presidents.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        try {
            JSONArray m_jArry = new JSONArray(json);
            for (int i = m_jArry.length() - 1; i >= 0; i--) {
                JSONObject jo_inside = m_jArry.getJSONObject(i);
                AmericanPresidents americanPresidentsSeeAll = new AmericanPresidents();
                AmericanPresidents americanPresidentsSeeAllWithYear = new AmericanPresidents();
                String tookOffice;
                String leftOffice;
                int presidentNumber = (int) jo_inside.get("number");
                americanPresidentsSeeAll.setNumber((int) jo_inside.get("number"));
                americanPresidentsSeeAllWithYear.setNumber((int) jo_inside.get("number"));

                americanPresidentsSeeAll.setPresident(jo_inside.getString("president"));
                americanPresidentsSeeAllWithYear.setPresident(jo_inside.getString("president"));

                americanPresidentsSeeAll.setPhotoUrl((String) jo_inside.get("photoUrl"));
                americanPresidentsSeeAllWithYear.setPhotoUrl((String) jo_inside.get("photoUrl"));

                String tookOfficeYear = (String) jo_inside.get("took_office");
                String leftOfficeYear = (String) jo_inside.get("left_office");
                americanPresidentsSeeAllWithYear.setTookOffice(tookOfficeYear);
                americanPresidentsSeeAllWithYear.setLeftOffice(leftOfficeYear);

                int birthYear = jo_inside.optInt("birth_year");
                int deathYear = jo_inside.optInt("death_year");

                americanPresidentsSeeAll.setParty((String) jo_inside.get("party"));
                americanPresidentsSeeAllWithYear.setParty((String) jo_inside.get("party"));

                americanPresidentsSeeAll.setBirthYear(birthYear);
                americanPresidentsSeeAllWithYear.setBirthYear(birthYear);

                americanPresidentsSeeAll.setDeathYear(deathYear);
                americanPresidentsSeeAllWithYear.setDeathYear(deathYear);

                if(presidentNumber<=25){
                    tookOffice = tookOfficeYear.substring(0,4);
                    americanPresidentsSeeAll.setTookOffice(tookOffice);
                }
                else{
                    tookOffice = tookOfficeYear.substring(tookOfficeYear.length()-4);
                    americanPresidentsSeeAll.setTookOffice(tookOffice);
                }

                if(presidentNumber<=24){
                    leftOffice = leftOfficeYear.substring(0,4);
                    Log.d("Length>>",leftOfficeYear.length() +"'.mlfldlfldfdlfkdkf");
                    americanPresidentsSeeAll.setLeftOffice(leftOffice);
                }
                else if ( presidentNumber>24 && presidentNumber<= 44){
                    leftOffice = leftOfficeYear.substring(leftOfficeYear.length()-4);
                    Log.d("Length>>",leftOfficeYear.length() +"'.mlfldlfldfdlfkdkf");
                    americanPresidentsSeeAll.setLeftOffice(leftOffice);
                }
                else{
                    americanPresidentsSeeAll.setLeftOffice(MyConstants.PRESENT_PRESIDENT);
                    americanPresidentsSeeAllWithYear.setLeftOffice(MyConstants.PRESENT_PRESIDENT);
                }
                //Add your values in your `ArrayList` as below:
                locList.add(americanPresidentsSeeAll);
                presidentsSeeAllListWithYear.add(americanPresidentsSeeAllWithYear);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return locList;
    }


    @Override
    int getLayoutId() {
        return R.layout.info_fragment;
    }

    @Override
    void onViewReady(@NonNull View view, @Nullable Bundle savedInstanceState) {

    }
}
