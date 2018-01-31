package com.example.kleberstevendiazcoello.ui.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
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
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.kleberstevendiazcoello.ui.Otros.MyAdapater;
import com.example.kleberstevendiazcoello.ui.R;
import com.example.kleberstevendiazcoello.ui.ViewHolder.HistorialAdpater;
import com.example.kleberstevendiazcoello.ui.ViewHolder.RecyclerAdapter;
import com.example.kleberstevendiazcoello.ui.clases_utilitarias.ConnectionDetector;
import com.example.kleberstevendiazcoello.ui.clases_utilitarias.Detalle;
import com.example.kleberstevendiazcoello.ui.clases_utilitarias.Historial;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HistorialFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HistorialFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistorialFragment extends Fragment {
    RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerView;
    ArrayList<Historial> arrayList = new ArrayList<>();
    GridLayoutManager gridLayoutManager;
    RequestQueue requestQueue;
    HistorialAdpater adapter;
    android.support.v7.widget.Toolbar toolbar;
    EditText mSearchField;
    Dialog waitdia;
    private int tiempo = 30;
    int pStatus = 0;
    public static final String ID_data = "iduser";
    ConnectionDetector connectionDetector;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public HistorialFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HistorialFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HistorialFragment newInstance(String param1, String param2) {
        HistorialFragment fragment = new HistorialFragment();
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
        View view =  inflater.inflate(R.layout.fragment_historial, container, false);
        mSearchField = (EditText)view.findViewById(R.id.buscar_historial);
        mSearchField.addTextChangedListener(mQuery);
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerviewhistorial);
        Log.d("myTag", "Me cree");
        requestQueue = Volley.newRequestQueue(getActivity());
        connectionDetector = new ConnectionDetector(getActivity());
        if(connectionDetector.isConnected()){
            waitdia = new Dialog(getActivity());
            waitdia.setContentView(R.layout.popupwait);
            waitdia.show();
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

        }else {
            Snackbar.make(getActivity().findViewById(android.R.id.content), "NO TIENE CONEXIÓN A INTERNET", Snackbar.LENGTH_LONG).setAction("Action", null).show();
        }



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
        SharedPreferences sharedPrefe = getActivity().getSharedPreferences(
                "userinfodata", Context.MODE_PRIVATE);
        int iduser = sharedPrefe.getInt(ID_data, 0);
        final String ids = String.valueOf(iduser);

        String url = "http://www.flexoviteq.com.ec/InsuvidaFolder/getHistorial.php";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONArray jObj = new JSONArray(response);

                        int count = 0;
                        while(count<jObj.length()){
                            try {
                                JSONObject object = jObj.getJSONObject(count);
                                Historial h = new Historial(object.getInt("id_historial"),object.getString("InsulinaAdministrada"),object.getString("Fecha"),object.getString("Total_Calorias"),
                                        object.getString("Hora"),object.getString("GlucosaObjetivo"),object.getString("GlucosaActual"));
                                arrayList.add(h);
                                count ++;
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                        adapter = new HistorialAdpater(arrayList,getActivity());
                        recyclerView.setAdapter(adapter);



                } catch (JSONException e) {
                    Snackbar.make(getView(),"NO EXISTEN DATOS",Snackbar.LENGTH_LONG).setAction("Action",null).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }
        }){
            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put("iduser",ids);
                return map;
            }

        };
        requestQueue.add(request);


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
