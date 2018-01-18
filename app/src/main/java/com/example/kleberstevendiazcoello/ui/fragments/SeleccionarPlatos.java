package com.example.kleberstevendiazcoello.ui.fragments;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
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
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.kleberstevendiazcoello.ui.R;
import com.example.kleberstevendiazcoello.ui.ViewHolder.RAdapterSPlatos;
import com.example.kleberstevendiazcoello.ui.clases_utilitarias.Detalle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SeleccionarPlatos.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SeleccionarPlatos#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SeleccionarPlatos extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerView;
    ArrayList<Detalle> arrayList = new ArrayList<>();
    RAdapterSPlatos adapter;
    GridLayoutManager gridLayoutManager;
    RequestQueue requestQueue;
    EditText mSearchField;
    Dialog waitdia;
    private int tiempo = 30;
    int pStatus = 0;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public SeleccionarPlatos() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SeleccionarPlatos.
     */
    // TODO: Rename and change types and number of parameters
    public static SeleccionarPlatos newInstance(String param1, String param2) {
        SeleccionarPlatos fragment = new SeleccionarPlatos();
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
        View view =  inflater.inflate(R.layout.fragment_seleccionar_platos, container, false);
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerview);
        Log.d("myTag", "Me cree");
        mSearchField = (EditText)view.findViewById(R.id.buscar_food_calculo_manual);
        mSearchField.addTextChangedListener(mQuery);
        waitdia = new Dialog(getActivity());
        waitdia.setContentView(R.layout.popupwait);
        waitdia.show();
        requestQueue = Volley.newRequestQueue(getActivity());
        try {
            getList();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        Thread thread = new Thread() {
            public void run() {
                while (pStatus < 100) {
                    pStatus += 1;
                    try {
                        sleep(tiempo);
                    } catch (Exception e) {

                    }
                }

                waitdia.dismiss();
            }
        };
        thread.start();

        return view;
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

                        adapter = new RAdapterSPlatos(arrayList,getActivity());

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
            Toast.makeText(context,"Seleecionar Platos",Toast.LENGTH_SHORT);
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
