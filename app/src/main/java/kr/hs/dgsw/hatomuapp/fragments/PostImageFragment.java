package kr.hs.dgsw.hatomuapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kr.hs.dgsw.hatomuapp.databinding.FragmentPostImageBinding;


public class PostImageFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";

    private String imgUrl;

    private FragmentPostImageBinding binding;

    public PostImageFragment() {
        // Required empty public constructor
    }

    public static PostImageFragment newInstance(String param1) {
        PostImageFragment fragment = new PostImageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            imgUrl = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPostImageBinding.inflate(inflater, container, false);
        binding.setImgUrl(imgUrl);
        return binding.getRoot();
    }

}
