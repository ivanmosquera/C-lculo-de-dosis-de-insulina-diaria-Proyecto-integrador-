package com.example.kleberstevendiazcoello.ui.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.kleberstevendiazcoello.ui.Database.Database;
import com.example.kleberstevendiazcoello.ui.R;
import com.example.kleberstevendiazcoello.ui.ViewHolder.FilterAdapter;
import com.example.kleberstevendiazcoello.ui.ViewHolder.HistorialAdpater;
import com.example.kleberstevendiazcoello.ui.ViewHolder.RecyclerAdapter;
import com.example.kleberstevendiazcoello.ui.clases_utilitarias.Detalle;
import com.example.kleberstevendiazcoello.ui.clases_utilitarias.Historial;
import com.example.kleberstevendiazcoello.ui.clases_utilitarias.Platos;
import com.example.kleberstevendiazcoello.ui.mSwipper.SwipeHelper;
import com.example.kleberstevendiazcoello.ui.mSwipper.SwipeHelperFilter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
    RequestQueue requestQueue,requestQueue2;
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayList<String> filtrada= new ArrayList<>();
    ArrayList<Detalle> listtraduc= new ArrayList<>();
    private int tiempo = 30;
    int pStatus = 0;

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
        requestQueue2 = Volley.newRequestQueue(getActivity());
        try {
            getListLabelsNoNecesarios(listaob);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        for (int j = 0; j < arrayList.size(); j++) {
            Log.d("Lista en otro fragment",arrayList.get(j));
        }

        filtrada = compararlistas(listaob);
        Thread thread = new Thread() {
            public void run() {
                while (pStatus < 100) {
                    pStatus += 1;
                    try {
                        sleep(tiempo);
                    } catch (Exception e) {

                    }
                }

                android.support.v4.app.FragmentManager fragmentManager= getFragmentManager();
                android.support.v4.app.FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.content2,new Dimisslabels()).addToBackStack("").commit();


            }
        };
        thread.start();




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
                                //Log.d("Index", "i:" + i +"de " + list.size() + " " + "j:" + j + "de " + arrayList.size());
                               // Log.d("Comparar", list.get(i).toString() + " " + arrayList.get(j).toString());
                                if (list.get(i).toString().equals(arrayList.get(j).toString())) {
                                    Log.d("Removi", list.get(i).toString());
                                    list.remove(i);
                                    i--;
                                    break;
                                    //i = i+1;
                                    //j = -1;
                                }
                            }
                        }



                        for (int i = 0; i < list.size(); i++) {
                            Log.d("Palabra a traducir", list.get(i));
                            String name = list.get(i);
                            try {
                                getListtraduct(name);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            //new Database(getActivity()).addPlatosAuto(new Platos("A1",name,"12","1"));
                        }

                       /* adapter = new FilterAdapter(list,getActivity());
                        recyclerView.setAdapter(adapter);
                        ItemTouchHelper.Callback callback = new SwipeHelperFilter(adapter);
                        ItemTouchHelper helper = new ItemTouchHelper(callback);
                        helper.attachToRecyclerView(recyclerView);*/
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


    public void  getListtraduct(final String name) throws JSONException {
        String url = "http://www.flexoviteq.com.ec/InsuvidaFolder/traductor.php";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jObj = new JSONArray(response);
                    int count = 0;
                    while(count<jObj.length()){
                        try {
                            JSONObject object = jObj.getJSONObject(count);
                            //Detalle d = new Detalle(object.getInt("id_Comida"),object.getString("Alimento"),object.getString("Medida"),object.getString("CHO"));
                            new Database(getActivity()).addPlatosAuto(new Platos(String.valueOf(object.getInt("id_Comida")),object.getString("Alimento"),object.getString("CHO"),"1"));
                            //listtraduc.add(d);
                            Log.d("Guardado", object.getString("Alimento"));

                            count ++;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                } catch (JSONException e) {
                    //Detalle d = new Detalle(12,name,"1","1");
                    new Database(getActivity()).addPlatosAuto(new Platos("A1",name,"1","1"));
                    //listtraduc.add(d);
                    Log.d("Guardado", name);
                }





            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }
        }){
            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put("palabra",name);
                return map;
            }

        };
        requestQueue2.add(request);


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
