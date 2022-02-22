package com.example.mytest;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.example.mytest.Database.DatabaseHelper;
import com.example.mytest.Model.ItemModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import java.util.ArrayList;
import java.util.List;

public class AddNewItem extends BottomSheetDialogFragment {
    private static final int RESULT_LOAD_IMAGE = 1;
    public static final String TAG = "ActionBottomDialog";
    private EditText inputText;
    private Button saveButton;
    private Button uploadButton;
    private ImageView previewImage;
    private Uri selectedImage;
    List<ItemModel> itemsList;
    DatabaseHelper db;

    public static AddNewItem newInstance() {
        return new AddNewItem();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_new_item, container, false);

        itemsList = new ArrayList<>();
        db = new DatabaseHelper(getContext());
        db.openDatabase();

        uploadButton = view.findViewById(R.id.selectImageButton);
        inputText = view.findViewById(R.id.inputItemEditText);
        saveButton = view.findViewById(R.id.saveButton);
        previewImage = view.findViewById(R.id.previewImageView);

        final Bundle bundle = getArguments();

        if (bundle != null) {
            String item = bundle.getString("item");
            inputText.setText((item));
            if (item.length() > 0) {
                inputText.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
            }
            inputText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    //not required
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (charSequence.toString().equals("")) {
                        inputText.setEnabled(false);
                        inputText.setTextColor(Color.GRAY);
                    }
                    else {
                        inputText.setEnabled(true);
                        inputText.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    //not required
                }
            });
        }

        inputText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("text");
            }
        });

        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadGallery();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = inputText.getText().toString();
                String imageRes = selectedImage.toString();
                if (!text.equals("")) {
                    ItemModel item = new ItemModel();
                    item.setTitle(text);
                    item.setImageRes(imageRes);
                    itemsList.add(item);
                    inputText.getText().clear();
                    createToast(text + " has been added!");
                    db.insertItem(item);
                    dismiss();
                }
                else {
                    createToast("Please enter a title!");
                }
            }
        });


        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && data != null){
            selectedImage = data.getData();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                getContext().getContentResolver().takePersistableUriPermission(selectedImage, Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            }
            Glide.with(getContext()).load(selectedImage).into(previewImage);
            createToast("Your image has been uploaded!");
        }
    }

    public void loadGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

   @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        Activity activity = getActivity();
        if (activity instanceof DialogCloseListener) {
            ((DialogCloseListener)activity).handleDialogClose(dialog);
        }
    }

    private void createToast(String str) {
        Toast.makeText(getContext(), str, Toast.LENGTH_SHORT).show();
    }

}
