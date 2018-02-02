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
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.kleberstevendiazcoello.ui.Database.Database;
import com.example.kleberstevendiazcoello.ui.R;
import com.example.kleberstevendiazcoello.ui.ViewHolder.AdapaterAutoHolder;
import com.example.kleberstevendiazcoello.ui.ViewHolder.Apter_carrito_paltos;
import com.example.kleberstevendiazcoello.ui.clases_utilitarias.ConnectionDetector;
import com.example.kleberstevendiazcoello.ui.clases_utilitarias.Platos;
import com.example.kleberstevendiazcoello.ui.mSwipper.SwipeHelper;
import com.example.kleberstevendiazcoello.ui.mSwipper.SwipeHelperAuto;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AutomaticCalculo.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AutomaticCalculo#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AutomaticCalculo extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Button selecplatos,calc_dosis;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerView;
    ArrayList<Platos> arrayList = new ArrayList<>();
    AdapaterAutoHolder adapter;
    GridLayoutManager gridLayoutManager;
    TextView total_carbo;
    ArrayList<Platos> cartlistos;
    ArrayList<Platos> cart;
    float total;
    Dialog mydialog;
    TextView txtclose,txttotalinsu;
    Button guardarhistorial,nuevainsulina;
    public static final String ID_data = "iduser";
    float tot = 0;
    float icr = 0;
    float nivelact = 0;
    float nivelobj = 0;
    float factor = 0;
    EditText glucosoactual, glucosaobjetivo;
    float totalamostar;
    String totalamostars;
    static String id_h ;
    RequestQueue requestQueue,requestQueue2,requestQueue3;
    ImageView back;
    ConnectionDetector connectionDetector;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public AutomaticCalculo() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AutomaticCalculo.
     */
    // TODO: Rename and change types and number of parameters
    public static AutomaticCalculo newInstance(String param1, String param2) {
        AutomaticCalculo fragment = new AutomaticCalculo();
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
        View view = inflater.inflate(R.layout.fragment_automatic_calculo, container, false);
        selecplatos = (Button) view.findViewById(R.id.agregarplatosauto);
        calc_dosis= (Button) view.findViewById(R.id.btn_calcular_dosisauto);
        total_carbo = (TextView)view.findViewById(R.id.sumatoria_corbogidratosauto);
        requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue2 = Volley.newRequestQueue(getActivity());
        requestQueue3 = Volley.newRequestQueue(getActivity());
        back = (ImageView) view.findViewById(R.id.back_tomarfoto);
        mydialog = new Dialog(getActivity());
        mydialog.setContentView(R.layout.custompopupauto);
        txtclose = (TextView)mydialog.findViewById(R.id.txtpopcloseauto);
        txttotalinsu = (TextView)mydialog.findViewById(R.id.totalinsulinadministrarauto);
        glucosoactual = (EditText)view.findViewById(R.id.mglucosaactualauto);
        glucosaobjetivo = (EditText)view.findViewById(R.id.mglucosaobjetivoauto);
        selecplatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.support.v4.app.FragmentManager fragmentManager= getFragmentManager();
                android.support.v4.app.FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.content2,new SeleccionarPlatosAuto()).addToBackStack("").commit();
            }
        });

        calc_dosis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showpopu(view);

            }
        });
        recyclerView = (RecyclerView)view.findViewById(R.id.rv_platosseleccionadosauto);
        LoadListFood();
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Database(getActivity()).cleanListAuto();
                android.support.v4.app.FragmentManager fragmentManager= getFragmentManager();
                android.support.v4.app.FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.content2,new CalculosFragment()).addToBackStack("").commit();
            }
        });

        return view;
    }


    public void showpopu(View v){

        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mydialog.dismiss();
            }
        });
        String t  =  totalcalculo();
        guardarhistorial = (Button)mydialog.findViewById(R.id.btnguardarhistorialauto);
        guardarhistorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                connectionDetector = new ConnectionDetector(getActivity());
                if(connectionDetector.isConnected()) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                    String currentDate = sdf.format(new Date());
                    SharedPreferences sharedPrefe = getActivity().getSharedPreferences(
                            "userinfodata", Context.MODE_PRIVATE);
                    int iduser = sharedPrefe.getInt(ID_data, 0);
                    String ids = String.valueOf(iduser);
                    SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss", Locale.US);
                    String hour = format.format(new Date());
                    saveHistorial(currentDate, hour, ids);
                    getlastindexHistorial();
                    Toast.makeText(getActivity(), "Historial Guardado", Toast.LENGTH_LONG).show();
                }
                else{
                    Snackbar.make(getView(), "NO TIENE CONEXIÓN A INTERNET, NO SE GUARDARA SU HISTORIAL", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                }
            }
        });

        nuevainsulina = (Button)mydialog.findViewById(R.id.btnnuevocalculoauto);
        nuevainsulina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Database(getActivity()).cleanListAuto();
                total = 0;
                android.support.v4.app.FragmentManager fragmentManager= getFragmentManager();
                android.support.v4.app.FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.content2,new AutomaticCalculo()).commit();
                mydialog.dismiss();
            }
        });

        txttotalinsu.setText(t);
        mydialog.show();
    }

    private void saveHistorial(final String dia, final String hora,final String idusuario){
        String url = "http://www.flexoviteq.com.ec/InsuvidaFolder/guardarHistorial.php";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put("fecha",dia);
                map.put("hora",hora);
                map.put("idusuario",idusuario);
                map.put("insulina",txttotalinsu.getText().toString());
                map.put("total",total_carbo.getText().toString());
                map.put("glucosaa",glucosoactual.getText().toString());
                map.put("glucosao",glucosaobjetivo.getText().toString());
                return map;
            }

        };
        requestQueue.add(request);


    }

    private void getlastindexHistorial(){


        String url = "http://www.flexoviteq.com.ec/InsuvidaFolder/ultimohistorial.php";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jObj = new JSONArray(response);
                    JSONObject object = jObj.getJSONObject(0);
                    id_h = object.getString("id_historial");
                    Log.d("Id obtenido",id_h);
                    SharedPreferences sharedPrefe = getActivity().getSharedPreferences(
                            "userinfodata", Context.MODE_PRIVATE);
                    int iduser = sharedPrefe.getInt(ID_data, 0);
                    String ids = String.valueOf(iduser);
                    saveplatos(id_h,ids);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }
        });
        requestQueue3.add(request);
    }

    private void saveplatos(final String idhistorial,final String idusuario){
        cartlistos = new Database(getActivity()).getListaComidaAuto();
        for (Platos platos:cartlistos){
            final String idplato = platos.getFoodID();
            String url = "http://www.flexoviteq.com.ec/InsuvidaFolder/guardarplatos.php";
            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }){
                protected Map<String,String> getParams() throws AuthFailureError {
                    Map<String,String> map = new HashMap<String,String>();
                    map.put("idcomida",idplato);
                    map.put("idhistorial",idhistorial);
                    map.put("idusuario",idusuario);
                    return map;
                }

            };
            requestQueue2.add(request);

        }

    }
    private String totalcalculo(){
        tot = Float.parseFloat(total_carbo.getText().toString());
        icr = 15;
        nivelact = Float.parseFloat(glucosoactual.getText().toString());
        nivelobj = Float.parseFloat(glucosaobjetivo.getText().toString());
        factor = (1500/45);
        totalamostar = ((tot/icr) + ((nivelact-nivelobj)/factor));
        totalamostars = String.format("%.1f", totalamostar);
        NumberFormat nf_out = NumberFormat.getNumberInstance(Locale.UK);
        nf_out.setMaximumFractionDigits(1);
        String output = nf_out.format(totalamostar);
        return output;
    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    private void LoadListFood() {
        total = 0;
        cart = new Database(getActivity()).getListaComidaAuto();
        adapter = new AdapaterAutoHolder(cart,getActivity());
        recyclerView.setAdapter(adapter);
        ItemTouchHelper.Callback callback = new SwipeHelperAuto(adapter);
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(recyclerView);
        for (Platos platos:cart){
            total += (Float.parseFloat(platos.getCalorias())) * (Integer.parseInt(platos.getCantidad()));

        }
        NumberFormat nf_out = NumberFormat.getNumberInstance(Locale.UK);
        nf_out.setMaximumFractionDigits(2);
        String output = nf_out.format(total);
        total_carbo.setText(output);
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
