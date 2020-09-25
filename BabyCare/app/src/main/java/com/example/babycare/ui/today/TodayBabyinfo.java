package com.example.babycare.ui.today;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.example.babycare.AfterLogin_Main;
import com.example.babycare.BuildConfig;
import com.example.babycare.MainActivity;
import com.example.babycare.R;
import com.example.babycare.database.UserAccount;
import com.example.babycare.viewmodel.UserViewModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import static android.app.Activity.RESULT_OK;

public class TodayBabyinfo extends Fragment {


    private String pathToFile;
    private Button b;
    private ImageView viewImage;
    private ImageView feedImage;
    private ImageView sleepImage;
    private StorageReference storageReference;
    private FloatingActionButton feed;
    private FloatingActionButton sleep;
    boolean isFirstLoading = true;

    TodayViewModel todayViewModel;

    private TextView breast;
    private TextView showBabyName;
    private TextView showBabyDate;
    private TextView lastfeed;
    private String lastFeedTime;

    private TextView sleepTime;
    private TextView lastSleep;
    private String lastSleepTime;


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {



        super.onSaveInstanceState(outState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);




        Log.i("oncreate", "oncreate execute");

        storageReference = FirebaseStorage.getInstance().getReference();
        if(Build.VERSION.SDK_INT >= 23) {
            requestPermissions(new String[] {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},2);
        }




    }



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.today_babyinfo, container, false);
        todayViewModel = ViewModelProviders.of(getActivity()).get(TodayViewModel.class);
        feedImage = root.findViewById(R.id.FEEDIMAGE);
        sleepImage = root.findViewById(R.id.SLEEPIMAGE);
        sleepImage.setImageResource(R.drawable.baby_sleep);
        feedImage.setImageResource(R.drawable.baby_bottle);
        //last feed

        todayViewModel.getFeedDate().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {


                lastFeedTime =s;
            }
        });

        lastfeed = root.findViewById(R.id.LASTFEDTIME);
        Timer updateTimer = new Timer();
        updateTimer.schedule(new TimerTask() {
            @Override
            public void run() {

                try {

                    Date date = new Date();
                    String strDateFormat = "HH:mm:ss";
                    SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
//                    Log.i("current", "curr" + sdf.format(date));
                    String currentTime = sdf.format(date);
//                    Log.i("current", "curr22" + lastFeedTime);
                    if (lastFeedTime == null){
                        lastFeedTime = currentTime;
                    }

                    SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");

//                    Date currentTime = Calendar.getInstance().getTime();




                    Date date1 = format.parse(currentTime);
                    Date date2 = format.parse(lastFeedTime);
//                    Log.i("diff", "date1:::" + date1);
//                    Log.i("diff", "date2:::" + date2);
                    Long mills = date1.getTime() - date2.getTime();

                    int hours = (int) (mills / (1000 * 60 * 60));
                    int mins = (int) (mills / (1000 * 60)) % 60;
                    String diff = "before:   "  +hours + " : " + mins;
//                    Log.i("diff", "curr22" + diff);
                    lastfeed.setText(diff);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }, 0, 1000);


        showBabyName = root.findViewById(R.id.show_baby_name);
        showBabyName.setText(todayViewModel.getBabyName().getValue());
        showBabyDate = root.findViewById(R.id.show_baby_date);
        showBabyDate.setText((todayViewModel.getBabyDate().getValue()));





        //switch
        final ConstraintLayout off = root.findViewById(R.id.switchconstraint);

        @SuppressLint("UseSwitchCompatOrMaterialCode") final Switch daily = root.findViewById(R.id.dailylog);

        daily.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                off.setVisibility(daily.isChecked() ? View.VISIBLE:View.GONE);
            }
        });

        feed = root.findViewById(R.id.FEEDFLOATING);
        feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()

                        .replace(R.id.fragment_today, new FeedFragment())
                        .addToBackStack(null)
                        .commit();
        }});

        //todayViewModel = ViewModelProviders.of(getActivity()).get(TodayViewModel.class);
        breast = root.findViewById(R.id.BREASLR);
        todayViewModel.getFeedTime().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                breast.setText(s);
            }
        });


        //sleep
        todayViewModel.getSleepDate().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {


                lastSleepTime =s;
            }
        });

        lastSleep = root.findViewById(R.id.AWAKETIME);

        updateTimer.schedule(new TimerTask() {
            @Override
            public void run() {

                try {

                    Date date2 = new Date();
                    String strDateFormat2 = "HH:mm:ss";
                    SimpleDateFormat sdf2 = new SimpleDateFormat(strDateFormat2);
//                    Log.i("current", "curr" + sdf.format(date));
                    String currentTime = sdf2.format(date2);
//                    Log.i("current", "curr22" + lastFeedTime);
                    if (lastSleepTime == null){
                        lastSleepTime = currentTime;
                    }

                    SimpleDateFormat format2 = new SimpleDateFormat("HH:mm:ss");

//                    Date currentTime = Calendar.getInstance().getTime();




                    Date date11 = format2.parse(currentTime);
                    Date date22 = format2.parse(lastSleepTime);
//                    Log.i("diff", "date1:::" + date1);
//                    Log.i("diff", "date2:::" + date2);
                    Long mills2 = date11.getTime() - date22.getTime();

                    int hours2 = (int) (mills2 / (1000 * 60 * 60));
                    int mins2 = (int) (mills2 / (1000 * 60)) % 60;
                    String diff2 = "before:   "  +hours2 + " : " + mins2;
//                    Log.i("diff", "curr22" + diff);
                    lastSleep.setText(diff2);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }, 0, 1000);






        sleep = root.findViewById(R.id.SLEEPFLOTING);
        sleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_today, new SleepFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
        sleepTime = root.findViewById(R.id.TODAYTIME);
        todayViewModel.getSleepTime().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                sleepTime.setText(s);
            }
        });





                //baby information
        viewImage = root.findViewById(R.id.baby_picture);

        Log.i("oncreateview", "oncreateview execute");




            Log.i("oncreateviewimagenull", "imagegggg");
//            viewImage.setImageResource(R.drawable.baby);
        loadImageFromStorage("/data/data/com.example.babycare/app_imageDir");
        b = root.findViewById(R.id.select_image);



        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {





                final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                builder.setTitle("Add Photo!");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (options[item].equals("Take Photo")) {
//                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//
//                            File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
//
//                            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f)+".provider");
//
//                            startActivityForResult(intent, 1);

                            Intent takePic = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                            File photoFile =null;
                            try {
                                photoFile = createPhotoFile();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            if(photoFile!=null) {
                                //pathToFile = photoFile.getAbsolutePath();
                                Uri photoURI = FileProvider.getUriForFile(getActivity(), "com.example.babycare.fileprovider", photoFile);
                                takePic.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);

                                startActivityForResult(takePic, 1);



                            }


                        } else if (options[item].equals("Choose from Gallery")) {
                            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(intent, 2);
                        } else if (options[item].equals("Cancel")) {
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();


            }
        });


        return root;
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);




    }

    private File createPhotoFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String mFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);


        File mFile = File.createTempFile(mFileName, ".jpg", storageDir);
        pathToFile = mFile.getAbsolutePath();
//        File mFile = new File(storageDir, mFileName);
        return mFile;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.i("onDestory", "onDestory execute");
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @SuppressLint("LongLogTag")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {

                File f = new File(pathToFile);
                //viewImage.setImageURI(Uri.fromFile(f));
                Log.i("tag", "Absoulute Url of Image is"+ Uri.fromFile(f));

                Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                Uri contentUri = Uri.fromFile(f);
                mediaScanIntent.setData(contentUri);
                getActivity().sendBroadcast(mediaScanIntent);


//                viewImage.setImageBitmap(bitmap);
//                setRetainInstance(true);
                uploadImageToFirebase(f.getName(), contentUri);

                //save to local
                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),contentUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                saveToInternalStorage(bitmap);


//                Bitmap bitmap = BitmapFactory.decodeFile(pathToFile);
//                viewImage.setImageBitmap(bitmap);



//                Bundle extras = data.getExtras();
//
//                Bitmap imageBitmap = (Bitmap) extras.get("data");
//
//                viewImage.setImageBitmap(imageBitmap);



//                try {
//
//
//                    String path = android.os.Environment
//                            .getExternalStorageDirectory()
//                            + File.separator
//                            + "Phoenix" + File.separator + "default";
//
//                    OutputStream outFile = null;
//                    File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
//                    try {
//                        outFile = new FileOutputStream(file);
//                        bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outFile);
//                        outFile.flush();
//                        outFile.close();
//                    } catch (FileNotFoundException e) {
//                        e.printStackTrace();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
            } else if (requestCode == 2) {
                Uri contentUri = data.getData();
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String imageFileName = "JPEG_" + timeStamp + "." +getFileExt(contentUri);
                //viewImage.setImageURI(contentUri);
                uploadImageToFirebase(imageFileName, contentUri);

                //save to local
                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),contentUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                saveToInternalStorage(bitmap);
//                Uri selectedImage = data.getData();
//                String[] filePath = { MediaStore.Images.Media.DATA };
//
//                Cursor c = getContext().getContentResolver().query(selectedImage,filePath, null, null, null);
//                c.moveToFirst();
//                int columnIndex = c.getColumnIndex(filePath[0]);
//                String picturePath = c.getString(columnIndex);
//                c.close();
//                try {
//                    ParcelFileDescriptor pfd = getContext().getContentResolver().openFileDescriptor(selectedImage, "r");
//                    Bitmap thumbnail = (BitmapFactory.decodeFileDescriptor(pfd.getFileDescriptor()));
//                    viewImage.setImageBitmap(thumbnail);
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }
//
//                Log.w("path of image from gallery......******************.........", picturePath+"");

            }
        }
    }

    private void uploadImageToFirebase(String name, Uri contentUri) {
        final StorageReference image = storageReference.child("images/" + name);
        image.putFile(contentUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                image.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Log.d("tag", "onSuccess: Upload Image URl is" + uri.toString());
                        Picasso.get().load(uri).fit().centerInside().into(viewImage);


      


                    }
                });
                Toast.makeText(getActivity(), "Image Is Uploaded.", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), "Upload Failled.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getFileExt(Uri contentUri) {
        ContentResolver c = getContext().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(c.getType(contentUri));
    }



    private String saveToInternalStorage(Bitmap bitmapImage){
        ContextWrapper cw = new ContextWrapper(getActivity().getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath=new File(directory,"profile.jpg");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }

    private void loadImageFromStorage(String path)
    {

        try {
            File f=new File(path, "profile.jpg");
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
//            ImageView img=(ImageView)findViewById(R.id.imgPicker);
            viewImage.setImageBitmap(b);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

    }

}



