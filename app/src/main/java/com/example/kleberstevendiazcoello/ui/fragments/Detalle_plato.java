package com.example.kleberstevendiazcoello.ui.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.kleberstevendiazcoello.ui.Database.Database;
import com.example.kleberstevendiazcoello.ui.R;
import com.example.kleberstevendiazcoello.ui.clases_utilitarias.Platos;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Detalle_plato.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Detalle_plato#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Detalle_plato extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    TextView txtnombrecomida;
    TextView txtcarbohidratos;
    TextView txttotal;
    ElegantNumberButton elegantNumberButton;
    Button btn_agregar;
    CollapsingToolbarLayout collapsingToolbarLayout;
    String calorias,nombre,cantidad;
    float calculo_total;
    int id;
    String sid;
    ImageView back;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Detalle_plato() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Detalle_plato.
     */
    // TODO: Rename and change types and number of parameters
    public static Detalle_plato newInstance(String param1, String param2) {
        Detalle_plato fragment = new Detalle_plato();
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
        View view = inflater.inflate(R.layout.fragment_detalle_plato, container, false);
        txtnombrecomida = view.findViewById(R.id.txtNombreComidas);
        txtcarbohidratos =  view.findViewById(R.id.txtcalorias_dess);
        txttotal = view.findViewById(R.id.food_total_carbos);
        back = (ImageView) view.findViewById(R.id.back_platosselec);
        elegantNumberButton = (ElegantNumberButton) view.findViewById(R.id.numbre_buttons);
        //cantidad = elegantNumberButton.getNumber();
        btn_agregar = (Button)view.findViewById(R.id.btn_agregar_lista);
        txtnombrecomida.setText(getArguments().getString("Nombre"));
        txtcarbohidratos.setText(getArguments().getString("Caloria"));
        nombre = getArguments().getString("Nombre");
        calorias = getArguments().getString("Caloria");
        id = getArguments().getInt("id_Comida", 0);

        sid = String.valueOf(id);

        elegantNumberButton.setOnClickListener(new ElegantNumberButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("myTag", "Cantidad Ingresada : "+elegantNumberButton.getNumber());
                cantidad = elegantNumberButton.getNumber();
                calculo_total = Float.parseFloat(calorias)*Integer.parseInt(cantidad);
                txttotal.setText(String.valueOf(calculo_total));


            }
        });

        btn_agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Database(getActivity()).addPlatos(new Platos(sid,nombre,calorias,cantidad));
                Toast.makeText(getActivity(), "Plato Agregado", Toast.LENGTH_LONG).show();
                android.support.v4.app.FragmentManager fragmentManager= getFragmentManager();
                android.support.v4.app.FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.content2,new CalcularmanualFragment()).addToBackStack("").commit();
            }

        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.support.v4.app.FragmentManager fragmentManager= getFragmentManager();
                android.support.v4.app.FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.content2,new SeleccionarPlatos()).addToBackStack("").commit();
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
            Toast.makeText(context,"Detalle plato Comida",Toast.LENGTH_SHORT);
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
