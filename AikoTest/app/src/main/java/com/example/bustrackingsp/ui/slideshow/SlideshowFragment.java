package com.example.bustrackingsp.ui.slideshow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.bustrackingsp.R;
import com.example.bustrackingsp.databinding.FragmentSlideshowBinding;
import com.example.bustrackingsp.mapUtils.MapUtils;

public class SlideshowFragment extends Fragment {

    private TextView textView;

    private FragmentSlideshowBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        SlideshowViewModel slideshowViewModel = new ViewModelProvider(this).get(SlideshowViewModel.class);

        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        textView = root.findViewById(R.id.ListOfLines);

        if(MapUtils.getLastTextMode() == 1) {
            textView.setText(MapUtils.getLinhasFormatado());
        } else if (MapUtils.getLastTextMode() == 2) {
            textView.setText(MapUtils.getPrevisaoText());
        }


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}