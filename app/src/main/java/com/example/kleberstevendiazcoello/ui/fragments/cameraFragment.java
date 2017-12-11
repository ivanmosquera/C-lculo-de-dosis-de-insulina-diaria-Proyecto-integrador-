package com.example.kleberstevendiazcoello.ui.fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kleberstevendiazcoello.ui.R;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.vision.v1.Vision;
import com.google.api.services.vision.v1.VisionRequestInitializer;
import com.google.api.services.vision.v1.model.AnnotateImageRequest;
import com.google.api.services.vision.v1.model.AnnotateImageResponse;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesRequest;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesResponse;
import com.google.api.services.vision.v1.model.ColorInfo;
import com.google.api.services.vision.v1.model.DominantColorsAnnotation;
import com.google.api.services.vision.v1.model.EntityAnnotation;
import com.google.api.services.vision.v1.model.Feature;
import com.google.api.services.vision.v1.model.Image;
import com.google.api.services.vision.v1.model.ImageProperties;
import com.google.api.services.vision.v1.model.SafeSearchAnnotation;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link cameraFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link cameraFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class cameraFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private static final String TAG = "MainActivity";
    private static final int RECORD_REQUEST_CODE = 101;
    private static final int CAMERA_REQUEST_CODE = 102;

    private static final String CLOUD_VISION_API_KEY = "AIzaSyBwnk0A292jZn9TaicCUAcwoSo9hsGK-Q8";
    Button takePicture;
    ImageView imageView;
    ProgressBar imageUploadProgress;
    Spinner spinnerVisionAPI;
    TextView visionAPIData;

    private Feature feature;
    private Bitmap bitmap;
    private String[] visionAPI = new String[]{"LABEL_DETECTION"};

    private String api = visionAPI[0];
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public cameraFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment cameraFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static cameraFragment newInstance(String param1, String param2) {
        cameraFragment fragment = new cameraFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_camera, container, false);
        takePicture = (Button)view.findViewById(R.id.takePicture);
        imageView = (ImageView)view.findViewById(R.id.imageView);
        imageUploadProgress = (ProgressBar)view.findViewById(R.id.imageProgress);
        spinnerVisionAPI = (Spinner) view.findViewById(R.id.spinnerVisionAPI);
        visionAPIData = (TextView) view.findViewById(R.id.visionAPIData);
        ButterKnife.bind(getActivity());

        feature = new Feature();
        feature.setType(visionAPI[0]);
        feature.setMaxResults(10);

        spinnerVisionAPI.setOnItemSelectedListener(this);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, visionAPI);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerVisionAPI.setAdapter(dataAdapter);
        takePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePictureFromCamera();
            }
        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            Toast.makeText(context,"Notificacion de FragmentoCamera",Toast.LENGTH_SHORT);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (checkPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            takePicture.setVisibility(View.VISIBLE);
        } else {
            takePicture.setVisibility(View.INVISIBLE);
            makeRequest(Manifest.permission.CAMERA);
        }
    }

    private int checkPermission(String permission) {
        return ContextCompat.checkSelfPermission(getActivity(), permission);
    }

    private void makeRequest(String permission) {
        ActivityCompat.requestPermissions(getActivity(), new String[]{permission}, RECORD_REQUEST_CODE);
    }

    public void takePictureFromCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
            bitmap = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(bitmap);
            callCloudVision(bitmap, feature);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == RECORD_REQUEST_CODE) {
            if (grantResults.length == 0 && grantResults[0] == PackageManager.PERMISSION_DENIED
                    && grantResults[0] == PackageManager.PERMISSION_DENIED) {

            } else {
                takePicture.setVisibility(View.VISIBLE);
            }
        }
    }

    private void callCloudVision(final Bitmap bitmap, final Feature feature) {
        imageUploadProgress.setVisibility(View.VISIBLE);
        final List<Feature> featureList = new ArrayList<>();
        featureList.add(feature);

        final List<AnnotateImageRequest> annotateImageRequests = new ArrayList<>();

        AnnotateImageRequest annotateImageReq = new AnnotateImageRequest();
        annotateImageReq.setFeatures(featureList);
        annotateImageReq.setImage(getImageEncodeImage(bitmap));
        annotateImageRequests.add(annotateImageReq);


        new AsyncTask<Object, Void, String>() {
            @Override
            protected String doInBackground(Object... params) {
                try {

                    HttpTransport httpTransport = AndroidHttp.newCompatibleTransport();
                    JsonFactory jsonFactory = GsonFactory.getDefaultInstance();

                    VisionRequestInitializer requestInitializer = new VisionRequestInitializer(CLOUD_VISION_API_KEY);

                    Vision.Builder builder = new Vision.Builder(httpTransport, jsonFactory, null);
                    builder.setVisionRequestInitializer(requestInitializer);

                    Vision vision = builder.build();

                    BatchAnnotateImagesRequest batchAnnotateImagesRequest = new BatchAnnotateImagesRequest();
                    batchAnnotateImagesRequest.setRequests(annotateImageRequests);

                    Vision.Images.Annotate annotateRequest = vision.images().annotate(batchAnnotateImagesRequest);
                    annotateRequest.setDisableGZipContent(true);
                    BatchAnnotateImagesResponse response = annotateRequest.execute();
                    return convertResponseToString(response);
                } catch (GoogleJsonResponseException e) {
                    Log.d(TAG, "failed to make API request because " + e.getContent());
                } catch (IOException e) {
                    Log.d(TAG, "failed to make API request because of other IOException " + e.getMessage());
                }
                return "Cloud Vision API request failed. Check logs for details.";
            }

            protected void onPostExecute(String result) {
                visionAPIData.setText(result);
                imageUploadProgress.setVisibility(View.INVISIBLE);
            }
        }.execute();
    }

    @NonNull
    private Image getImageEncodeImage(Bitmap bitmap) {
        Image base64EncodedImage = new Image();
        // Convert the bitmap to a JPEG
        // Just in case it's a format that Android understands but Cloud Vision
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream);
        byte[] imageBytes = byteArrayOutputStream.toByteArray();

        // Base64 encode the JPEG
        base64EncodedImage.encodeContent(imageBytes);
        return base64EncodedImage;
    }

    private String convertResponseToString(BatchAnnotateImagesResponse response) {

        AnnotateImageResponse imageResponses = response.getResponses().get(0);

        List<EntityAnnotation> entityAnnotations;

        String message = "";
        switch (api) {
            case "LANDMARK_DETECTION":
                entityAnnotations = imageResponses.getLandmarkAnnotations();
                message = formatAnnotation(entityAnnotations);
                break;
            case "LOGO_DETECTION":
                entityAnnotations = imageResponses.getLogoAnnotations();
                message = formatAnnotation(entityAnnotations);
                break;
            case "SAFE_SEARCH_DETECTION":
                SafeSearchAnnotation annotation = imageResponses.getSafeSearchAnnotation();
                message = getImageAnnotation(annotation);
                break;
            case "IMAGE_PROPERTIES":
                ImageProperties imageProperties = imageResponses.getImagePropertiesAnnotation();
                message = getImageProperty(imageProperties);
                break;
            case "LABEL_DETECTION":
                entityAnnotations = imageResponses.getLabelAnnotations();
                message = formatAnnotation(entityAnnotations);
                break;
        }
        return message;
    }

    private String getImageAnnotation(SafeSearchAnnotation annotation) {
        return String.format("adult: %s\nmedical: %s\nspoofed: %s\nviolence: %s\n",
                annotation.getAdult(),
                annotation.getMedical(),
                annotation.getSpoof(),
                annotation.getViolence());
    }

    private String getImageProperty(ImageProperties imageProperties) {
        String message = "";
        DominantColorsAnnotation colors = imageProperties.getDominantColors();
        for (ColorInfo color : colors.getColors()) {
            message = message + "" + color.getPixelFraction() + " - " + color.getColor().getRed() + " - " + color.getColor().getGreen() + " - " + color.getColor().getBlue();
            message = message + "\n";
        }
        return message;
    }

    private String formatAnnotation(List<EntityAnnotation> entityAnnotation) {
        String message = "";

        if (entityAnnotation != null) {
            for (EntityAnnotation entity : entityAnnotation) {
                message = message + "    " + entity.getDescription() + " " + entity.getScore();
                message += "\n";
            }
        } else {
            message = "Nothing Found";
        }
        return message;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        api = (String) adapterView.getItemAtPosition(i);
        feature.setType(api);
        if (bitmap != null)
            callCloudVision(bitmap, feature);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}

