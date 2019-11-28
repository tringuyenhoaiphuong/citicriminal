package com.lemycanh.citycriminal;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.theartofdev.edmodo.cropper.CropImage;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import pl.aprilapps.easyphotopicker.ChooserType;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import pl.aprilapps.easyphotopicker.MediaFile;
import pl.aprilapps.easyphotopicker.MediaSource;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {


    private TextView mTvTitle;
    private TextView mTvContent;
    private TextView mTvTimestamp;
    private CheckBox mCkResolved;
    private Problem problem;
    private ImageView mIvEvident;
    private Button mBtnTakePicture;
    private EasyImage easyImage;

    public DetailFragment() {
        // Required empty public constructor
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        problem = event.getProblem();
        mTvTitle.setText(problem.getTitle());
        mTvContent.setText(problem.getContent());

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        mTvTimestamp.setText(dateFormat.format(problem.getTimestamp()));

        mCkResolved.setChecked(problem.isResolved());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        this.mTvTitle = view.findViewById(R.id.tv_problem_title);
        this.mTvContent = view.findViewById(R.id.tv_problem_content);
        this.mTvTimestamp = view.findViewById(R.id.tv_problem_timestamp);
        this.mCkResolved = view.findViewById(R.id.ck_problem_resolved);
        this.mIvEvident = view.findViewById(R.id.iv_evident);
        this.mBtnTakePicture = view.findViewById(R.id.btn_takepicture);

        this.mCkResolved.setOnClickListener(v -> {
            problem.setResolved(mCkResolved.isChecked());
            EventBus.getDefault().post(new ProblemUpdatedEvent(problem));
        });

        this.mBtnTakePicture.setOnClickListener(v -> {
            easyImage = new EasyImage.Builder(getActivity())
                    .allowMultiple(false)
                    .setCopyImagesToPublicGalleryFolder(false)
                    .setChooserTitle("Pick media")
                    .setChooserType(ChooserType.CAMERA_AND_GALLERY)
                    .build();
            easyImage.openChooser(DetailFragment.this);
        });

        return view;
    }

    public static android.app.Fragment CreateInstance() {
        return new DetailFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        handleImageCrop(requestCode, resultCode, data);

        handleImagePicker(requestCode, resultCode, data);
    }

    private void handleImageCrop(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == Activity.RESULT_OK) {
                Uri resultUri = result.getUri();
                mIvEvident.setImageURI(resultUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    private void handleImagePicker(int requestCode, int resultCode, Intent data) {
        easyImage.handleActivityResult(requestCode, resultCode, data, DetailFragment.this.getActivity(), new DefaultCallback() {
            @Override
            public void onMediaFilesPicked(MediaFile[] imageFiles, MediaSource source) {
                CropImage.activity(Uri.fromFile(imageFiles[0].getFile()))
                        .start(getActivity(), DetailFragment.this);
            }
        });
    }
}
