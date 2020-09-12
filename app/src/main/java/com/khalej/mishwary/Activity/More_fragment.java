package com.khalej.mishwary.Activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.khalej.mishwary.R;
import com.khalej.mishwary.model.Apiclient_home;
import com.khalej.mishwary.model.apiinterface_home;
import com.khalej.mishwary.model.updateImage;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;


public class More_fragment extends Fragment {
TextView logout,terms,whous,callus,language,login,bank;
    private SharedPreferences sharedpref;
    private SharedPreferences.Editor edt;
    CircleImageView image;
    TextView name ,address,phone ;
    private  static final int IMAGEUser = 99;
    Bitmap bitmapUser;
    String mediaPath,mediaPathId;
    String imagePath;
    ProgressDialog progressDialog;
      LinearLayout logut;
    updateImage imageupdate;
    ImageView addorder;
    private apiinterface_home apiinterface;
    private static final int MY_CAMERA_PERMISSION_CODE = 1;
    private static final int CAMERA_REQUEST = 1;
    int x=0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_more_fragment, container, false);
        logout=view.findViewById(R.id.logout);
        terms=view.findViewById(R.id.terms);
        whous=view.findViewById(R.id.whous);
        callus=view.findViewById(R.id.callus);
        language=view.findViewById(R.id.language);
        logut=view.findViewById(R.id.logut);
        image=view.findViewById(R.id.image);
        name=view.findViewById(R.id.username);
        address=view.findViewById(R.id.address);
        phone=view.findViewById(R.id.phone);
        bank=view.findViewById(R.id.bank);

        sharedpref = getActivity().getSharedPreferences("Education", Context.MODE_PRIVATE);
        edt = sharedpref.edit();

        addorder=view.findViewById(R.id.addorder);


        Glide.with(getContext()).load(sharedpref.getString("image","")).thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL).error(R.drawable.addprofile).into(image);
        if (Build.VERSION.SDK_INT >= 23) {
            int permissionCheck = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }
        image.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                if(sharedpref.getString("remember","").equals("yes")){
                    if (getActivity().checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                    {
                        requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                    }
                    else
                    {
                        showPictureDialog();
                    }
                }
                }
        });
        name.setText(sharedpref.getString("name",""));
//        if(sharedpref.getInt("type",0)==2||sharedpref.getInt("type",0)==1){
//            address.setText( "الرصيد :" +sharedpref.getFloat("wallet",0));
//            address.setVisibility(View.GONE);
//            addorder.setVisibility(View.VISIBLE);
//        }
//        else{
//            address.setVisibility(View.GONE);
//            addorder.setVisibility(View.GONE);
//        }
        address.setText(sharedpref.getString("address",""));
        phone.setText(sharedpref.getString("phone",""));


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edt.putInt("id",0);
                edt.putString("name","");
                edt.putString("image","");
                edt.putString("phone","");
                edt.putString("address","");
                edt.putString("password","");
                edt.putString("createdAt","");
                edt.putInt("type",0);
                edt.putFloat("wallet",0);
                edt.putString("remember","no");
                edt.apply();
                startActivity(new Intent(getActivity(), Login.class));
                getActivity().finish();
            }
        });


        callus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                try{
//                    String url = "https://api.whatsapp.com/send?phone="+"+97333348098";
//                    Intent i = new Intent(Intent.ACTION_VIEW);
//                    i.setData(Uri.parse(url));
//                    startActivity(i);}
//                catch( Exception e){
//                    Toast.makeText(getActivity(), "غير متاحه الأن عاود المحاولة لاحقا " ,Toast.LENGTH_LONG).show();
//                }

                startActivity(new Intent(getActivity(),CallUs.class));
            }
        });
        bank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),copounActivity.class));
            }
        });
        language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sharedpref.getString("language","").trim().equals("ar")){
                    edt.putString("language","en");
                    edt.apply();
                    startActivity(new Intent(getActivity(),MainActivity.class));
                    getActivity().finish();
                }
                else
                {
                    edt.putString("language","ar");
                    edt.apply();
                    startActivity(new Intent(getActivity(),MainActivity.class));
                    getActivity().finish();
                }
            }
        });

        whous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),whous.class));
                }
        });
        terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              startActivity(new Intent(getActivity(),Terms.class));
                 }
        });
        return view;

    }
    private void showPictureDialog(){
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(getContext());
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera" };
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }
    public void choosePhotoFromGallary() {

        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        x=0;
        startActivityForResult(galleryIntent, IMAGEUser);

    }

    private void takePhotoFromCamera() {
        x=1;
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        try {
            File image = File.createTempFile(
                    imageFileName,  /* prefix */
                    ".jpg",  /* suffix */
                    storageDir     /* directory */
            );
            Uri uri = FileProvider.getUriForFile(getContext(), getActivity().getPackageName(), image);
            imagePath=image.getAbsolutePath();//Store this path as globe variable

            Toast.makeText(getContext(), "camera permission granted", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            startActivityForResult(intent, CAMERA_REQUEST);
        } catch (IOException e) {
            e.printStackTrace();
        }



    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK)
        {

            mediaPathId = imagePath;
            String name=random();

            mediaPathId=resizeAndCompressImageBeforeSend(getActivity(),mediaPathId,name);

            image.setImageBitmap(BitmapFactory.decodeFile(mediaPathId));
            fetchInfo();
        }
        if(requestCode == IMAGEUser && resultCode == RESULT_OK && null != data)
        {
            Uri pathImag = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getActivity().getContentResolver().query(pathImag, filePathColumn, null, null, null);
            assert cursor != null;
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            mediaPathId = cursor.getString(columnIndex);
            // Toast.makeText(Registration.this,mediaPath,Toast.LENGTH_LONG).show();
            String namee=random();

            mediaPathId=resizeAndCompressImageBeforeSend(getActivity(),mediaPathId,namee);

            image.setImageBitmap(BitmapFactory.decodeFile(mediaPathId));
            cursor.close();
            fetchInfo();
        }


    }
    public static String random() {
        Random generator = new Random();
        StringBuilder randomStringBuilder = new StringBuilder();
        int randomLength = generator.nextInt(10);
        char tempChar;
        for (int i = 0; i < randomLength; i++){
            tempChar = (char) (generator.nextInt(96) + 32);
            randomStringBuilder.append(tempChar);
        }
        return randomStringBuilder.toString();
    }
    public static String resizeAndCompressImageBeforeSend(Context context, String filePath, String fileName){
        final int MAX_IMAGE_SIZE = 300 * 1024; // max final file size in kilobytes

        // First decode with inJustDecodeBounds=true to check dimensions of image
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath,options);

        // Calculate inSampleSize(First we are going to resize the image to 800x800 image, in order to not have a big but very low quality image.
        //resizing the image will already reduce the file size, but after resizing we will check the file size and start to compress image
        options.inSampleSize = calculateInSampleSize(options, 800, 800);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        options.inPreferredConfig= Bitmap.Config.ARGB_8888;

        Bitmap bmpPic = BitmapFactory.decodeFile(filePath,options);


        int compressQuality = 100; // quality decreasing by 5 every loop.
        int streamLength;
        do{
            ByteArrayOutputStream bmpStream = new ByteArrayOutputStream();
            Log.d("compressBitmap", "Quality: " + compressQuality);
            bmpPic.compress(Bitmap.CompressFormat.JPEG, compressQuality, bmpStream);
            byte[] bmpPicByteArray = bmpStream.toByteArray();
            streamLength = bmpPicByteArray.length;
            compressQuality -= 5;
            Log.d("compressBitmap", "Size: " + streamLength/1024+" kb");
        }while (streamLength >= MAX_IMAGE_SIZE);

        try {
            //save the resized and compressed file to disk cache
            Log.d("compressBitmap","cacheDir: "+context.getCacheDir());
            FileOutputStream bmpFile = new FileOutputStream(context.getCacheDir()+fileName);
            bmpPic.compress(Bitmap.CompressFormat.JPEG, compressQuality, bmpFile);
            bmpFile.flush();
            bmpFile.close();
        } catch (Exception e) {
            Log.e("compressBitmap", "Error on saving file");
        }
        //return the path of resized and compressed file
        return  context.getCacheDir()+fileName;
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        String debugTag = "MemoryInformation";
        // Image nin islenmeden onceki genislik ve yuksekligi
        final int height = options.outHeight;
        final int width = options.outWidth;
        Log.d(debugTag,"image height: "+height+ "---image width: "+ width);
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        Log.d(debugTag,"inSampleSize: "+inSampleSize);
        return inSampleSize;
    }
    public void fetchInfo() {
        if(mediaPathId==null||mediaPathId.equals("")){
            return;
        }
        File file = new File(mediaPathId);

        // Parsing any Media type file
        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("national_image", file.getName(), requestBody);
        RequestBody userid=RequestBody.create(MediaType.parse("text/plain"), String.valueOf(sharedpref.getInt("id",0)));
        progressDialog = ProgressDialog.show(getContext(), "جاري تغيير الصورة الشخصية", "Please wait...", false, false);
        progressDialog.show();

        apiinterface = Apiclient_home.getapiClient().create(apiinterface_home.class);
        Call<updateImage> call = apiinterface.getcontacts_updateProfile(fileToUpload,userid);
        call.enqueue(new Callback<updateImage>() {
            @Override
            public void onResponse(Call<updateImage> call, Response<updateImage> response) {
                progressDialog.dismiss();
                if (response.code() == 422) {
                    JSONObject jObjError = null;
                    try {
                        jObjError = new JSONObject(response.errorBody().string());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    // Toast.makeText(Regester.this,jObjError.toString(),Toast.LENGTH_LONG).show();
                    Toast.makeText(getActivity(),"هناك بيانات مستخدمة من قبل  أو تأكد من انك ادخلت البيانات بشكل صحيح",Toast.LENGTH_LONG).show();
                    Log.d("tag", jObjError.toString());

                    return;
                }
                Toast.makeText(getActivity(),"تم تغيير الصورة الشخصية بنجاح ",Toast.LENGTH_LONG).show();
                imageupdate=response.body();
                edt.putString("image","http://applicationme.com/maishwary/public/storage/uploads/"+imageupdate.getImage());
                edt.apply();
                Glide.with(getContext()).load("http://applicationme.com/maishwary/public/storage/uploads/"+imageupdate.getImage()).thumbnail(0.5f)
                        .crossFade()
                        .diskCacheStrategy(DiskCacheStrategy.ALL).error(R.drawable.addprofile).into(image);


            }
                @Override
                public void onFailure(Call<updateImage> call, Throwable t) {
                    progressDialog.dismiss();

                }
            });

        }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                try {


                    Toast.makeText(getContext(), "camera permission granted", Toast.LENGTH_LONG).show();
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                    cameraIntent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);}
                catch (Exception e){}
            }
            else
            {
                Toast.makeText(getContext(), "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }
}
