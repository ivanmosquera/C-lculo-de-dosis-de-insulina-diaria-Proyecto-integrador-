package com.example.kleberstevendiazcoello.ui.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.kleberstevendiazcoello.ui.Database.Database;
import com.example.kleberstevendiazcoello.ui.R;
import com.example.kleberstevendiazcoello.ui.clases_utilitarias.ConnectionDetector;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CalculosFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CalculosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CalculosFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    CardView calculomanual;
    CardView calculoautomatico;
    ConnectionDetector connectionDetector;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public CalculosFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CalculosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CalculosFragment newInstance(String param1, String param2) {
        CalculosFragment fragment = new CalculosFragment();
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

        View view = inflater.inflate(R.layout.fragment_calculos, container, false);
        calculomanual = view.findViewById(R.id.calculo_manual);
        calculoautomatico = view.findViewById(R.id.calculo_automatico);
        Log.d("myTag", "Me cree");

        calculomanual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    SharedPreferences sharedPrefe5 = getActivity().getSharedPreferences(
                            "datosmedicos", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor4 = sharedPrefe5.edit();
                    editor4.clear();
                    editor4.commit();
                    new Database(getActivity()).cleanList();
                    android.support.v4.app.FragmentManager fragmentManager= getFragmentManager();
                    android.support.v4.app.FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.replace(R.id.content2,new CalcularmanualFragment()).commit();




            }
        });

        calculoautomatico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            connectionDetector = new ConnectionDetector(getActivity());
            if(connectionDetector.isConnected()) {
                SharedPreferences sharedPrefe6 = getActivity().getSharedPreferences(
                        "datosmedicos", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor5 = sharedPrefe6.edit();
                editor5.clear();
                editor5.commit();
                new Database(getActivity()).cleanListAuto();
                android.support.v4.app.FragmentManager fragmentManager = getFragmentManager();
                android.support.v4.app.FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.content2, new cameraFragment()).commit();
            }else{
                Snackbar.make(getView(),"NO TIENE CONEXIÓN A INTERNET, PRUEBE CÀLCULO MANUAL",Snackbar.LENGTH_LONG).setAction("Action",null).show();
            }



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
            Toast.makeText(context,"Notificacion Historial de Fragmento",Toast.LENGTH_SHORT);
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
}
