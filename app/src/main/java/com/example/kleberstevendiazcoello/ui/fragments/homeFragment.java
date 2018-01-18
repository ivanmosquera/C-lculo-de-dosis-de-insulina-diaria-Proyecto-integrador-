package com.example.kleberstevendiazcoello.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.kleberstevendiazcoello.ui.Activitys.botton_menu;
import com.example.kleberstevendiazcoello.ui.R;
import com.example.kleberstevendiazcoello.ui.login.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link homeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link homeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class homeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static final String ID_data = "iduser";
    public static final String Mail_data = "email";
    public static final String User_data = "user";
    public static final String Peso_data = "peso";
    public static final String Altura_data = "altura";
    public static final String Edad_data = "edad";
    public static final String Genero_data = "genero";
    public static final String Ciudad_data = "ciudad";
    TextView user, e, peso,a,nombre,genero,ciudad ;
    RequestQueue requestQueue;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public homeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment homeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static homeFragment newInstance(String param1, String param2) {
        homeFragment fragment = new homeFragment();
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
       View view  =inflater.inflate(R.layout.fragment_home, container, false);
        requestQueue = Volley.newRequestQueue(getActivity());
        user = (TextView)view.findViewById(R.id.txtcorreouser);
        a = (TextView)view.findViewById(R.id.txtshowaltura);
        peso= (TextView)view.findViewById(R.id.txtshowpeso);
        e = (TextView)view.findViewById(R.id.txtshowedad);
        nombre = (TextView)view.findViewById(R.id.txtshowusuario);
        genero = (TextView)view.findViewById(R.id.txtshowgenero);
        ciudad = (TextView)view.findViewById(R.id.txtshowciudad);


        SharedPreferences sharedPref = getActivity().getSharedPreferences(
                "userinfo", Context.MODE_PRIVATE);
        String username = sharedPref.getString(Mail_data, "hola");
        getdatosuser(username);


        /*SharedPreferences sharedPref = getActivity().getSharedPreferences(
                "userinfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(ID_data,object.getInt("id"));
        editor.putString(User_data,object.getString("Usuario"));
        editor.putString(Peso_data,String.valueOf(object.getString("Peso")));
        editor.putString(Altura_data,String.valueOf(object.getString("Altura")));
        editor.putInt(Edad_data,object.getInt("Edad"));
        editor.commit();*/

       return view;
    }


    public void getdatosuser(final String mail){

        String url = "http://www.flexoviteq.com.ec/InsuvidaFolder/datosuser.php";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jObj = new JSONArray(response);
                    JSONObject object = jObj.getJSONObject(0);
                    SharedPreferences sharedPref = getActivity().getSharedPreferences(
                            "userinfodata", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.clear();
                    editor.putInt(ID_data,object.getInt("id"));
                    editor.putString(User_data,object.getString("Nombre"));
                    editor.putString(Peso_data,String.valueOf(object.getString("Peso")));
                    editor.putString(Altura_data,String.valueOf(object.getString("Altura")));
                    editor.putString(Genero_data,String.valueOf(object.getString("Genero")));
                    editor.putString(Ciudad_data,String.valueOf(object.getString("Ciudad")));
                    editor.putInt(Edad_data,object.getInt("Edad"));
                    editor.commit();

                    SharedPreferences sharedPrefe = getActivity().getSharedPreferences(
                            "userinfodata", Context.MODE_PRIVATE);
                    int edad = sharedPrefe.getInt(Edad_data, 0);
                    String name = sharedPrefe.getString(User_data, "hola");
                    String altu = sharedPrefe.getString(Altura_data, "hola");
                    String pe = sharedPrefe.getString(Peso_data, "hola");
                    String ciu = sharedPrefe.getString(Ciudad_data, "hola");
                    String ge = sharedPrefe.getString(Genero_data, "hola");
                    user.setText(mail);
                    e.setText(String.valueOf(edad));
                    a.setText(altu);
                    peso.setText(pe);
                    nombre.setText(name);
                    genero.setText(ge);
                    ciudad.setText(ciu);



                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }
        }){
            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put("correo",mail);
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
            Toast.makeText(context,"Notificacion de Fragmento",Toast.LENGTH_SHORT);
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
