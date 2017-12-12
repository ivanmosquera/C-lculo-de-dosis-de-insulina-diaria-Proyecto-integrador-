package com.example.kleberstevendiazcoello.ui.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.kleberstevendiazcoello.ui.Database.Database;
import com.example.kleberstevendiazcoello.ui.R;
import com.example.kleberstevendiazcoello.ui.ViewHolder.Apter_carrito_paltos;
import com.example.kleberstevendiazcoello.ui.ViewHolder.RecyclerAdapter;
import com.example.kleberstevendiazcoello.ui.clases_utilitarias.Detalle;
import com.example.kleberstevendiazcoello.ui.clases_utilitarias.Platos;

import org.json.JSONException;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CalcularmanualFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CalcularmanualFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CalcularmanualFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Button selecplatos,calc_dosis;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerView;
    ArrayList<Platos> arrayList = new ArrayList<>();
    Apter_carrito_paltos adapter;
    GridLayoutManager gridLayoutManager;
    RequestQueue requestQueue;
    TextView total_carbo;
    ArrayList<Platos> cart;
    int total;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    FloatingActionButton floatingActionButton;

    private OnFragmentInteractionListener mListener;

    public CalcularmanualFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CalcularmanualFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CalcularmanualFragment newInstance(String param1, String param2) {
        CalcularmanualFragment fragment = new CalcularmanualFragment();
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
        View view = inflater.inflate(R.layout.fragment_calcularmanual, container, false);
         selecplatos = (Button) view.findViewById(R.id.agregarplatos);
         calc_dosis= (Button) view.findViewById(R.id.btn_calcular_dosis);
         total_carbo = (TextView)view.findViewById(R.id.sumatoria_corbogidratos);
         selecplatos.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 android.support.v4.app.FragmentManager fragmentManager= getFragmentManager();
                 android.support.v4.app.FragmentTransaction transaction = fragmentManager.beginTransaction();
                 transaction.replace(R.id.content2,new SeleccionarPlatos()).addToBackStack("").commit();
             }
         });

         calc_dosis.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                  new Database(getActivity()).cleanList();

             }
         });
        recyclerView = (RecyclerView)view.findViewById(R.id.rv_platosseleccionados);
        LoadListFood();
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);


        return view;
    }

    private void LoadListFood() {
        cart = new Database(getActivity()).getListaComida();
        adapter = new Apter_carrito_paltos(cart,getActivity());
        recyclerView.setAdapter(adapter);
        for (Platos platos:cart){
            total += (Integer.parseInt(platos.getCalorias())) * (Integer.parseInt(platos.getCantidad()));

        }
        Locale locale = new Locale("en","US");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
        total_carbo.setText(fmt.format(total));
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
