package com.atcampus.shopper.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.atcampus.shopper.Adapter.RewardAdapter;
import com.atcampus.shopper.Model.RewardModel;
import com.atcampus.shopper.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class RewardFragment extends Fragment {

    public RewardFragment() {
        // Required empty public constructor
    }

    private RecyclerView rewardRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reward, container, false);

        rewardRecyclerView = view.findViewById(R.id.rewardRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        rewardRecyclerView.setLayoutManager(linearLayoutManager);

        List<RewardModel> rewardModelList = new ArrayList<>();
        rewardModelList.add(new RewardModel("Title for Reward 1","20-12-2020","Lorem ipsum is placeholder text commonly used in the graphic, print, and publishing industries for previewing layouts and visual mockups."));
        rewardModelList.add(new RewardModel("Title for Reward 2","10-11-2020","Lorem ipsum is placeholder text commonly used in the graphic, print, and publishing industries for previewing layouts and visual mockups."));
        rewardModelList.add(new RewardModel("Title for Reward 3","14-12-2020","Lorem ipsum is placeholder text commonly used in the graphic, print, and publishing industries for previewing layouts and visual mockups."));
        rewardModelList.add(new RewardModel("Title for Reward 4","29-10-2020","Lorem ipsum is placeholder text commonly used in the graphic, print, and publishing industries for previewing layouts and visual mockups."));
        rewardModelList.add(new RewardModel("Title for Reward 5","12-12-2020","Lorem ipsum is placeholder text commonly used in the graphic, print, and publishing industries for previewing layouts and visual mockups."));
        rewardModelList.add(new RewardModel("Title for Reward 6","19-12-2020","Lorem ipsum is placeholder text commonly used in the graphic, print, and publishing industries for previewing layouts and visual mockups."));

        RewardAdapter rewardAdapter = new RewardAdapter(rewardModelList);
        rewardRecyclerView.setAdapter(rewardAdapter);
        rewardAdapter.notifyDataSetChanged();
        return view;
    }
}
