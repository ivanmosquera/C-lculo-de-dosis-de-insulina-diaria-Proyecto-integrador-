package com.example.kleberstevendiazcoello.ui.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.kleberstevendiazcoello.ui.Database.Database;
import com.example.kleberstevendiazcoello.ui.R;
import com.example.kleberstevendiazcoello.ui.ViewHolder.FilterAdapter;
import com.example.kleberstevendiazcoello.ui.clases_utilitarias.Platos;
import com.example.kleberstevendiazcoello.ui.mSwipper.SwipeHelperFilter;
import com.example.kleberstevendiazcoello.ui.mSwipper.SwipeHelperFilterRigth;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Dimisslabels.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Dimisslabels#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Dimisslabels extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    RecyclerView recyclerView;
    FilterAdapter adapter;
    RecyclerView.LayoutManager layoutManager;
    Button btn_continuar_automatico;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Dimisslabels() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Dimisslabels.
     */
    // TODO: Rename and change types and number of parameters
    public static Dimisslabels newInstance(String param1, String param2) {
        Dimisslabels fragment = new Dimisslabels();
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
        View view = inflater.inflate(R.layout.fragment_dimisslabels, container, false);
        recyclerView = (RecyclerView)view.findViewById(R.id.rvalimentosfiltrados);
        btn_continuar_automatico = (Button)view.findViewById(R.id.continuarautomatico);
        btn_continuar_automatico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.support.v4.app.FragmentManager fragmentManager= getFragmentManager();
                android.support.v4.app.FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.content2,new AutomaticCalculo()).addToBackStack("").commit();
            }
        });

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        ArrayList<Platos> plte = new ArrayList<>();
        Database db = new Database(getActivity());
        plte = db.getListaComidaAuto();
        adapter = new FilterAdapter(plte,getActivity());
        recyclerView.setAdapter(adapter);
        ItemTouchHelper.Callback callback = new SwipeHelperFilter(adapter,getActivity());
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(recyclerView);
        ItemTouchHelper.Callback callback2 = new SwipeHelperFilterRigth(adapter,getActivity());
        ItemTouchHelper helper2 = new ItemTouchHelper(callback2);
        helper2.attachToRecyclerView(recyclerView);

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
            Toast.makeText(context,"Notificacion dismisslables de Fragmento",Toast.LENGTH_SHORT);
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
