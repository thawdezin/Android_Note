package com.thawdezin.note.fragment;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.thawdezin.note.File.FileUtils;
import com.thawdezin.note.File.PermissionUtils;
import com.thawdezin.note.R;
import com.thawdezin.note.activities.MainActivity;
import com.thawdezin.note.adapters.FileAdapter;
import com.thawdezin.note.data.vos.MyFile;
import com.thawdezin.note.glide.GifSizeFilter;
import com.thawdezin.note.glide.Glide4Engine;
import com.thawdezin.note.utils.DateTimeHelper;
import com.thawdezin.note.utils.MyConstants;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.filter.Filter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;
import static android.view.View.GONE;
import static android.view.View.OnClickListener;
import static android.view.View.VISIBLE;

public class FragmentFile extends BaseFragment {
    private static boolean flag = true;
    private static String imageFilePath;
    private static List<MyFile> myFileList = new ArrayList<>();
    private static int filePosition;
    private static boolean flagForRecyclerViewScroll = false;
    /**
     * fragment file views
     */
    private static RecyclerView recyclerView;

    private static int getFilePosition() {
        return filePosition;
    }

    public static void setFilePosition(int filePosition) {
        FragmentFile.filePosition = filePosition;
    }

    /**
     * getting Photo FileList
     */
    public static List<MyFile> getMyFileList() {
        return myFileList;
    }

    @Override
    public void onStart() {
        super.onStart();
        MainActivity.getFloatingActionButton().show();
        MainActivity.getFloatingActionButton().setImageResource(R.drawable.ic_fab_file);
        if (flag) {
            File storage = Objects.requireNonNull(getContext()).getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            assert storage != null;
            File[] fileList = storage.listFiles();
            assert fileList != null;
            for (File list : fileList) {
                String imageFilePath = list.getAbsolutePath();
                MyFile myFile = new MyFile(imageFilePath);
                myFileList.add(myFile);
            }
        }
        setUpRecyclerViews();
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity.setCurrentFragment(MyConstants.currentFile);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        MainActivity.getFloatingActionButton().setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                gettingAction();
            }
        });
        return view;
    }

    private void setUpRecyclerViews() {
        FileAdapter<MyFile> mFileAdapter = new FileAdapter<>(myFileList);
        recyclerView.setAdapter(mFileAdapter);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        recyclerView.setItemViewCacheSize(20); //Has no effect
        recyclerView.setItemAnimator(null);
        if (filePosition != 0) {
            flagForRecyclerViewScroll = true;
        }
        if (flagForRecyclerViewScroll) {
            recyclerView.scrollToPosition(getFilePosition());
            setFilePosition(0);
        } else {
            recyclerView.scrollToPosition(0);
        }
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0 && MainActivity.getBottomNavigationView().isShown()) {
                    MainActivity.getBottomNavigationView().setVisibility(GONE);
                } else if (dy < 0)
                    MainActivity.getBottomNavigationView().setVisibility(VISIBLE);
            }

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
        flag = false;


    }

    @Override
    int getLayoutId() {
        return R.layout.file_fragment;
    }

    @Override
    void onViewReady(@NonNull View view, @Nullable Bundle savedInstanceState) {

    }

    /**
     * Alert dialog for capture or select from galley
     */
    private void gettingAction() {
        final CharSequence[] items = {
                "Take Photo", "Choose from Gallery",
                "Cancel"
        };


        AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getContext()));
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (items[which].equals(MyConstants.TAKE_PHOTO)) {
                    // Here, thisActivity is the current activity
                    if (ContextCompat.checkSelfPermission(Objects.requireNonNull(getContext()),
                            Manifest.permission.CAMERA)
                            != PackageManager.PERMISSION_GRANTED) {
                        // Should we show an explanation?
                        if (ActivityCompat.shouldShowRequestPermissionRationale(Objects.requireNonNull(getActivity()),
                                Manifest.permission.CAMERA)) {
                            // Show an expanation to the user *asynchronously* -- don't block
                            // this thread waiting for the user's response! After the user
                            // sees the explanation, try again to request the permission.
                            ActivityCompat.requestPermissions(getActivity(),
                                    new String[]{Manifest.permission.CAMERA},
                                    MyConstants.IMAGE_CAPTURE_CODE);
                        } else {
                            if (PermissionUtils.isFirstTimeAskingPermission(getContext(), Manifest.permission.CAMERA)) {

                                PermissionUtils.firstTimeAskingPermission(getActivity(), Manifest.permission.CAMERA, false);

                                // No explanation needed, we can request the permission.
                                ActivityCompat.requestPermissions(getActivity(),
                                        new String[]{Manifest.permission.CAMERA},
                                        MyConstants.IMAGE_CAPTURE_CODE);
                                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                                // app-defined int constant. The callback method gets the
                                // result of the request.
                            } else {
                                displayNeverAskAgainDialog();
                                //Permission disable by device policy or user denied permanently. Show proper error message
                            }
                        }
                    } else {
                        takePhoto();
                    }

                } else if (items[which].equals(MyConstants.PICK_IMAGE)) {
                    // Here, thisActivity is the current activity
                    if ((ContextCompat.checkSelfPermission(Objects.requireNonNull(getContext()),
                            Manifest.permission.READ_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(getContext(),
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED)) {
                        // Should we show an explanation?
                        if ((ActivityCompat.shouldShowRequestPermissionRationale(Objects.requireNonNull(getActivity()),
                                Manifest.permission.READ_EXTERNAL_STORAGE)) && (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                                Manifest.permission.WRITE_EXTERNAL_STORAGE))) {
                            // Show an expanation to the user *asynchronously* -- don't block
                            // this thread waiting for the user's response! After the user
                            // sees the explanation, try again to request the permission.
                            ActivityCompat.requestPermissions(getActivity(),
                                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                    MyConstants.IMAGE_GALLERY_CODE);
                        } else {
                            if ((PermissionUtils.isFirstTimeAskingPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)) &&
                                    (PermissionUtils.isFirstTimeAskingPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE))) {

                                PermissionUtils.firstTimeAskingPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE, false);
                                PermissionUtils.firstTimeAskingPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE, false);

                                // No explanation needed, we can request the permission.
                                ActivityCompat.requestPermissions(getActivity(),
                                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                        MyConstants.IMAGE_GALLERY_CODE);
                                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                                // app-defined int constant. The callback method gets the
                                // result of the request.
                            } else {
                                displayNeverAskAgainDialog();
                                //Permission disable by device policy or user denied permanently. Show proper error message
                            }
                        }
                    } else {
                        choosePhoto();
                    }

                } else if (items[which].equals("Cancel")) {
                    dialog.dismiss();
                } else {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }


    /**
     * Take Photo with Camera
     */

    private void takePhoto() {
        Intent cInt = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //Create a file to store the image
        File photoFile = null;
        try {
            photoFile = createImageFile();
        } catch (IOException ex) {
            // Error occurred while creating the File
        }
        if (photoFile != null) {
            Uri photoUri = FileProvider.getUriForFile(Objects.requireNonNull(getContext()), getContext().getPackageName() + ".provider", photoFile);
            cInt.putExtra(MediaStore.EXTRA_OUTPUT,
                    photoUri);
            startActivityForResult(cInt, MyConstants.IMAGE_CAPTURE_CODE);
        }
    }

    /**
     * Image picker from gallery with Matisse
     */
    private void choosePhoto() {
        Matisse.from(FragmentFile.this)
                .choose(MimeType.ofImage())
                .countable(true)
                .maxSelectable(10)
                .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f)
                .imageEngine(new Glide4Engine())
                .forResult(MyConstants.IMAGE_GALLERY_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent data) {
        if (requestCode == MyConstants.IMAGE_CAPTURE_CODE) {
            if (resultCode == RESULT_OK) {
                setFilePosition(0);
                MyFile cameraPhotoPath = new MyFile(imageFilePath);
                myFileList.add(cameraPhotoPath);
            } else {
                MyFile cameraPhotoPath = new MyFile(imageFilePath);
                String deleteUri = cameraPhotoPath.getImageUri();
                File deleteFile = new File(deleteUri);
                deleteFile.delete();
            }
        } else if (requestCode == MyConstants.IMAGE_GALLERY_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                List<Uri> mSelected = Matisse.obtainResult(data);
                for (int i = 0; i < mSelected.size(); i++) {
                    saveGalleryImage(mSelected.get(i), i);
                    MyFile galleryPath = new MyFile(mSelected.get(i).toString());
                    myFileList.add(galleryPath);

                }
            }
        }
    }

    /**
     * Save Gallery Pick Image in external file directory
     */
    private void saveGalleryImage(Uri uri, int i) {
        try {
            String sourceImagePath = FileUtils.getPath(getContext(), uri);
            //create destination(new image file)name
            File root = Objects.requireNonNull(getContext()).getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            String imageFileName = "IMG_" + currentDateFormat() + "_" + currentTimeFormat() + "_" + i;

            //source image
            File source = new File(sourceImagePath);
            File galleryFile = new File(root + File.separator + imageFileName);

            //copy image into file directory
            FileChannel src = new FileInputStream(source).getChannel();
            FileChannel dst = new FileOutputStream(galleryFile).getChannel();
            dst.transferFrom(src, 0, src.size());
            src.close();
            dst.close();
        } catch (Exception ignored) {
        }
    }

    /**
     * Get current date
     */
    private String currentDateFormat() {
        long millis = System.currentTimeMillis();
        Date date = new Date(millis);
        return DateTimeHelper.getDateFormat().format(date);
    }

    /**
     * Get Current Time
     */
    private String currentTimeFormat() {
        long millis = System.currentTimeMillis();
        Date date = new Date(millis);
        return DateTimeHelper.getTimeFormat().format(date);
    }

    /**
     * Save capture image in external file directory
     */
    private File createImageFile() throws IOException {
        String partFileDateName = currentDateFormat();
        String partFileTimeName = currentTimeFormat();
        String imageFileName = "IMG_" + partFileDateName + partFileTimeName + "_";
        File storageDir = Objects.requireNonNull(getContext()).getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        imageFilePath = image.getAbsolutePath();
        return image;
    }

    private void displayNeverAskAgainDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getContext()));
        builder.setMessage("We need to capture image and to take photo from gallery for performing necessary task. Please permit the permission through "
                + "Settings screen.\n\nSelect Permissions -> Enable permission");
        builder.setCancelable(false);
        builder.setPositiveButton("Permit Manually", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Intent intent = new Intent();
                intent.setAction(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", Objects.requireNonNull(getContext()).getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    /**
     * Callback that handles the status of the permissions request.
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MyConstants.IMAGE_GALLERY_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(
                            getContext(),
                            "Permission granted! Please click on pick a file once again.",
                            Toast.LENGTH_SHORT
                    ).show();
                } else {
                    Toast.makeText(
                            getContext(),
                            "Permission denied to read your External storage :(",
                            Toast.LENGTH_SHORT
                    ).show();

                }
            }
            case MyConstants.IMAGE_CAPTURE_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(
                            getContext(),
                            "Permission granted! Please click on pick a file once again.",
                            Toast.LENGTH_SHORT
                    ).show();
                } else {
                    Toast.makeText(
                            getContext(),
                            "Permission denied to read your External storage :(",
                            Toast.LENGTH_SHORT
                    ).show();
                    //PermissionUtils.setShouldShowStatus(getContext(), Manifest.permission.CAMERA);
                }
            }
            break;
            default:
                throw new IllegalStateException("Unexpected value: " + requestCode);
        }
    }
}

