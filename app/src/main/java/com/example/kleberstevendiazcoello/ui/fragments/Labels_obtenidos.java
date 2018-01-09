package com.example.kleberstevendiazcoello.ui.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.kleberstevendiazcoello.ui.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Labels_obtenidos.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Labels_obtenidos#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Labels_obtenidos extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ArrayList<String> listaob;
    RequestQueue requestQueue;
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayList<String> filtrada= new ArrayList<>();

    private OnFragmentInteractionListener mListener;

    public Labels_obtenidos() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Labels_obtenidos.
     */
    // TODO: Rename and change types and number of parameters
    public static Labels_obtenidos newInstance(String param1, String param2) {
        Labels_obtenidos fragment = new Labels_obtenidos();
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
        View view = inflater.inflate(R.layout.fragment_labels_obtenidos, container, false);

        listaob = (ArrayList<String>) getArguments().getSerializable("ListaObtenida");
        for (int i = 0; i < listaob.size(); i++) {
            Log.d("Lista en otro fragment",listaob.get(i));
        }
        requestQueue = Volley.newRequestQueue(getActivity());
        try {
            getListLabelsNoNecesarios(listaob);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        for (int j = 0; j < arrayList.size(); j++) {
            Log.d("Lista en otro fragment",arrayList.get(j));
        }
        filtrada = compararlistas(listaob);




        return  view;

    }


    public ArrayList<String> compararlistas(ArrayList<String> listaobtenido) {
        for (int i = 0; i < listaobtenido.size(); i++) {
            for (int j = 0; j < arrayList.size(); j++) {
                Log.d("Comparar", listaobtenido.get(i).toString() + " " + arrayList.get(j).toString());
                if (listaobtenido.get(i).toString().equals(arrayList.get(j).toString())) {
                    listaobtenido.remove(i);
                    Log.d("Removi", listaobtenido.get(i).toString());
                }
            }
        }
        return listaobtenido;
    }



    public void  getListLabelsNoNecesarios(final ArrayList<String> list) throws JSONException {
        String url = "http://www.flexoviteq.com.ec/InsuvidaFolder/labels_no_necesarios.php";
        JsonArrayRequest jsonArrayRequest =  new JsonArrayRequest(com.android.volley.Request.Method.POST, url,(String) null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        int count = 0;
                        while(count<response.length()){
                            try {
                                JSONObject object = response.getJSONObject(count);
                                String label = object.getString("Nombre");
                                //Detalle d = new Detalle(object.getInt("id_Comida"),object.getString("Alimento"),object.getString("Medida"),object.getString("Cantidad"));
                                arrayList.add(label);
                                count ++;
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                        for (int i = 0; i < list.size(); i++) {
                            for (int j = 0; j < arrayList.size(); j++) {
                                //Log.d("Comparar", list.get(i).toString() + " " + arrayList.get(j).toString());
                                if (list.get(i).toString().equals(arrayList.get(j).toString())) {
                                    Log.d("Removi", list.get(i).toString());
                                    list.remove(i);

                                }
                            }
                        }

                        for (int i = 0; i < list.size(); i++) {
                            Log.d("Lista filtrada ",listaob.get(i));
                        }



                        //Log.d("Lista no deseados 2 fragment", arrayList.toString());  //Imprime lista de labels no necesarios por consola
                        //adapter = new RAdapterSPlatos(arrayList,getActivity());

                        //recyclerView.setAdapter(adapter);

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
            Toast.makeText(context,"Notificacion lables de Fragmento",Toast.LENGTH_SHORT);
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
