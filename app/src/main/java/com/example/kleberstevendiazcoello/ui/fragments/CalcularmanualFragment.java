package com.example.kleberstevendiazcoello.ui.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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
import com.example.kleberstevendiazcoello.ui.ViewHolder.Apter_carrito_paltos;
import com.example.kleberstevendiazcoello.ui.ViewHolder.RecyclerAdapter;
import com.example.kleberstevendiazcoello.ui.clases_utilitarias.ConnectionDetector;
import com.example.kleberstevendiazcoello.ui.clases_utilitarias.Detalle;
import com.example.kleberstevendiazcoello.ui.clases_utilitarias.Platos;
import com.example.kleberstevendiazcoello.ui.login.MainActivity;
import com.example.kleberstevendiazcoello.ui.mSwipper.SwipeHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


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
    public static final String Actual = "actual";
    public static final String Objetivo = "objetivo";
    public static final String DDI = "ddi";
    public static final String ICR= "icr";
    Button selecplatos,calc_dosis;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerView;
    ArrayList<Platos> arrayList = new ArrayList<>();
    Apter_carrito_paltos adapter;
    GridLayoutManager gridLayoutManager;
    RequestQueue requestQueue,requestQueue2,requestQueue3;
    TextView total_carbo;
    ArrayList<Platos> cart;
    ArrayList<Platos> cartlistos;
    Spinner spsalud,spinenfermo;
    ArrayList<String> listavida,listaenfermo;
    String[] strvida,senfermo;
    ArrayAdapter comboAdapter,comboadapterenfermo;
    float total = 0;
    public static final String ID_data = "iduser";
    static String id_h ;
    Dialog mydialog;
    TextView txtclose,txttotalinsu;
    EditText glucosoactual, glucosaobjetivo,txticr,txtddi;
    float totalamostar;
    String totalamostars;
    Button guardarhistorial,nuevainsulina;
    float tot = 0;
    int icr = 0;
    int ddi = 0;
    float nivelact = 0;
    float nivelobj = 0;
    float factor = 0;
    ImageView back;
    ConnectionDetector connectionDetector;



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
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calcularmanual, container, false);
        BottomNavigationView navigationView = (BottomNavigationView) getActivity().findViewById(R.id.navigation);
        Menu drawer_menu = navigationView.getMenu();
        MenuItem menuItem;
        menuItem = drawer_menu.findItem(R.id.navigation_camera);
        if(!menuItem.isChecked())
        {
            menuItem.setChecked(true);
        }
        back = (ImageView) view.findViewById(R.id.back_menucalculo);
         selecplatos = (Button) view.findViewById(R.id.agregarplatos);
         requestQueue = Volley.newRequestQueue(getActivity());
         requestQueue2 = Volley.newRequestQueue(getActivity());
         requestQueue3 = Volley.newRequestQueue(getActivity());
         calc_dosis= (Button) view.findViewById(R.id.btn_calcular_dosis);
         total_carbo = (TextView)view.findViewById(R.id.sumatoria_corbogidratos);
         glucosoactual = (EditText)view.findViewById(R.id.mglucosaactual);
         glucosaobjetivo = (EditText)view.findViewById(R.id.mglucosaobjetivo);
         mydialog = new Dialog(getActivity());
         mydialog.setContentView(R.layout.custompopup);
         txtclose = (TextView)mydialog.findViewById(R.id.txtpopclose);
         txttotalinsu = (TextView)mydialog.findViewById(R.id.totalinsulinadministrar);
         txticr = (EditText)view.findViewById(R.id.txticr);
         txtddi = (EditText)view.findViewById(R.id.txtddi);
         selecplatos.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 SharedPreferences sharedPref = getActivity().getSharedPreferences(
                         "datosmedicos", Context.MODE_PRIVATE);
                 SharedPreferences.Editor editor = sharedPref.edit();
                 editor.clear();
                 editor.putString(Objetivo,glucosaobjetivo.getText().toString());
                 editor.putString(Actual,glucosoactual.getText().toString());
                 editor.putString(DDI,txtddi.getText().toString());
                 editor.putString(ICR,txticr.getText().toString());
                 editor.commit();
                 android.support.v4.app.FragmentManager fragmentManager= getFragmentManager();
                 android.support.v4.app.FragmentTransaction transaction = fragmentManager.beginTransaction();
                 transaction.replace(R.id.content2,new SeleccionarPlatos()).addToBackStack("").commit();
             }
         });

         calc_dosis.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {

                 showpopu(view);

                 /*SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                 String currentDate = sdf.format(new Date());
                 SharedPreferences sharedPrefe = getActivity().getSharedPreferences(
                         "userinfodata", Context.MODE_PRIVATE);
                 int iduser = sharedPrefe.getInt(ID_data, 0);
                 String ids = String.valueOf(iduser);
                 SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss", Locale.US);
                 String hour = format.format(new Date());
                 saveHistorial(currentDate,hour,ids);
                 getlastindexHistorial();*/



             }
         });
        recyclerView = (RecyclerView)view.findViewById(R.id.rv_platosseleccionados);
        LoadListFood();
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Database(getActivity()).cleanList();
                android.support.v4.app.FragmentManager fragmentManager= getFragmentManager();
                android.support.v4.app.FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.content2,new CalculosFragment()).addToBackStack("").commit();
            }
        });

        SharedPreferences sharedPrefe2 = getActivity().getSharedPreferences(
                "datosmedicos", Context.MODE_PRIVATE);
        //int edad = sharedPrefe.getInt(Edad_data, 0);
        String objetive = sharedPrefe2.getString(Objetivo, "0");
        String actualiti = sharedPrefe2.getString(Actual, "0");
        String icract = sharedPrefe2.getString(ICR, "15");
        String ddiact = sharedPrefe2.getString(DDI, "0");
        //user.setText(mail);
        glucosaobjetivo.setText(objetive);
        glucosoactual.setText(actualiti);
        txticr.setText(icract);
        txtddi.setText(ddiact);


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
            guardarhistorial = (Button)mydialog.findViewById(R.id.btnguardarhistorial);
            guardarhistorial.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    connectionDetector = new ConnectionDetector(getActivity());
                    if(connectionDetector.isConnected()){
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                        String currentDate = sdf.format(new Date());
                        SharedPreferences sharedPrefe = getActivity().getSharedPreferences(
                                "userinfodata", Context.MODE_PRIVATE);
                        int iduser = sharedPrefe.getInt(ID_data, 0);
                        String ids = String.valueOf(iduser);
                        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss", Locale.US);
                        String hour = format.format(new Date());
                        saveHistorial(currentDate,hour,ids);
                        getlastindexHistorial();
                        Toast.makeText(getActivity(), "Historial Guardado", Toast.LENGTH_LONG).show();

                    }else {
                        Snackbar.make(getView(), "NO TIENE CONEXIÓN A INTERNET, NO SE GUARDARA SU HISTORIAL", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    }
                }
            });

            nuevainsulina = (Button)mydialog.findViewById(R.id.btnnuevocalculo);
            nuevainsulina.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new Database(getActivity()).cleanList();
                    total = 0;
                    android.support.v4.app.FragmentManager fragmentManager= getFragmentManager();
                    android.support.v4.app.FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.replace(R.id.content2,new CalcularmanualFragment()).commit();
                    mydialog.dismiss();
                }
            });

            txttotalinsu.setText(t);
            mydialog.show();








    }

    private String totalcalculo(){
        tot = Float.parseFloat(total_carbo.getText().toString());
        String icrs = txticr.getText().toString().replaceAll(" ", "");
        icr = Integer.parseInt(icrs);//si el no quiere nada es 15 sino cambia.//factor desensibilidad
        ddi = Integer.parseInt(txtddi.getText().toString());
        String act= glucosoactual.getText().toString().replaceAll(" ", "");
        String obj= glucosaobjetivo.getText().toString().replaceAll(" ", "");
        nivelact = Float.parseFloat(act);
        nivelobj = Float.parseFloat(obj);
        factor = (1800/ddi);//variable,//ultrarapida
        totalamostar = ((tot/icr) + ((nivelact-nivelobj)/factor));
        totalamostars = String.format("%.1f", totalamostar);
        NumberFormat nf_out = NumberFormat.getNumberInstance(Locale.UK);
        nf_out.setMaximumFractionDigits(1);
        String output = nf_out.format(totalamostar);
        return output;
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
        cartlistos = new Database(getActivity()).getListaComida();
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
    private void LoadListFood() {
        total = 0;
        cart = new Database(getActivity()).getListaComida();
        adapter = new Apter_carrito_paltos(cart,getActivity());
        recyclerView.setAdapter(adapter);
        ItemTouchHelper.Callback callback = new SwipeHelper(adapter);
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(recyclerView);
        for (Platos platos:cart){
            total += (Float.parseFloat(platos.getCalorias())) * (Integer.parseInt(platos.getCantidad()));
        }
        /*Locale locale = new Locale("en","US");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);*/
        NumberFormat nf_out = NumberFormat.getNumberInstance(Locale.UK);
        nf_out.setMaximumFractionDigits(2);
        String output = nf_out.format(total);
        //total_carbo.setText(String.format("%.2f", total));
        total_carbo.setText(output);
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
