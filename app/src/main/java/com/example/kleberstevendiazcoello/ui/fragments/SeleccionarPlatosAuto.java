package com.example.kleberstevendiazcoello.ui.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.kleberstevendiazcoello.ui.Database.Database;
import com.example.kleberstevendiazcoello.ui.R;
import com.example.kleberstevendiazcoello.ui.ViewHolder.RAdapterSPlatos;
import com.example.kleberstevendiazcoello.ui.ViewHolder.RadapterAutoPlatos;
import com.example.kleberstevendiazcoello.ui.clases_utilitarias.ConnectionDetector;
import com.example.kleberstevendiazcoello.ui.clases_utilitarias.Detalle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SeleccionarPlatosAuto.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SeleccionarPlatosAuto#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SeleccionarPlatosAuto extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerView;
    ArrayList<Detalle> arrayList = new ArrayList<>();
    ArrayList<Detalle> arrayListdiscoauto = new ArrayList<>();
    RadapterAutoPlatos adapter;
    GridLayoutManager gridLayoutManager;
    RequestQueue requestQueue;
    EditText mSearchField;
    ImageView back;
    ConnectionDetector conn;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public SeleccionarPlatosAuto() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SeleccionarPlatosAuto.
     */
    // TODO: Rename and change types and number of parameters
    public static SeleccionarPlatosAuto newInstance(String param1, String param2) {
        SeleccionarPlatosAuto fragment = new SeleccionarPlatosAuto();
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
        View view =  inflater.inflate(R.layout.fragment_seleccionar_platos_auto, container, false);
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerviewselectauto);
        Log.d("myTag", "Me cree");
        mSearchField = (EditText)view.findViewById(R.id.buscar_food_calculo_auto);
        mSearchField.addTextChangedListener(mQuery);
        back = (ImageView) view.findViewById(R.id.back_calculoauto);
        requestQueue = Volley.newRequestQueue(getActivity());
        conn = new ConnectionDetector(getActivity());
        if(conn.isConnected()){
            try {
                getList();
            } catch (JSONException e) {
                Snackbar.make(getView(),"Error, Baja Conexión",Snackbar.LENGTH_LONG).setAction("Action",null).show();
            }

        }else {
            getListDisconected();
        }
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.support.v4.app.FragmentManager fragmentManager= getFragmentManager();
                android.support.v4.app.FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.content2,new AutomaticCalculo()).addToBackStack("").commit();
            }
        });
        return view;
    }

    public void getListDisconected(){
        arrayListdiscoauto =  new Database(getActivity()).getListaFood();
        adapter = new RadapterAutoPlatos(arrayListdiscoauto,getActivity());
        recyclerView.setAdapter(adapter);
    }
    public void  getList() throws JSONException {
        final ArrayList<Detalle> arrayListe = new ArrayList<>();
        String url = "http://www.flexoviteq.com.ec/InsuvidaFolder/itemscomida.php";
        JSONObject obj = new JSONObject();
        obj.put("id",3);

        JsonArrayRequest jsonArrayRequest =  new JsonArrayRequest(com.android.volley.Request.Method.POST, url,(String) null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        int count = 0;
                        while(count<response.length()){
                            try {
                                JSONObject object = response.getJSONObject(count);
                                Detalle d = new Detalle(object.getInt("id_Comida"),object.getString("Alimento"),object.getString("Medida"),object.getString("CHO"));
                                arrayList.add(d);
                                count ++;
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        adapter = new RadapterAutoPlatos(arrayList,getActivity());

                        recyclerView.setAdapter(adapter);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonArrayRequest);


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
            Toast.makeText(context,"Notificacion select auto de Fragmento",Toast.LENGTH_SHORT);
        }
    }

    private TextWatcher mQuery = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            adapter.filter(editable.toString());
        }
    };
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
