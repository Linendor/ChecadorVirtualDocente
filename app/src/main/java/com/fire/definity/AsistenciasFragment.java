package com.fire.definity;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fire.definity.adapter.AsistenciaAdapter;
import com.fire.definity.adapter.DocenteAdapter;
import com.fire.definity.entidades.Docente;
import com.fire.definity.entidades.LisAsis;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AsistenciasFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AsistenciasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AsistenciasFragment extends Fragment implements Response.Listener<JSONObject>,Response.ErrorListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    int id = MainActivity.idus;
Button boton;
CheckBox checar;
TextView im;
    ProgressDialog progres;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    RecyclerView recyclerAs;
    AsistenciaAdapter asistenciaAdapter;
    private OnFragmentInteractionListener mListener;
    ArrayList<LisAsis> ListaAsistencias;
    public AsistenciasFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AsistenciasFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AsistenciasFragment newInstance(String param1, String param2) {
        AsistenciasFragment fragment = new AsistenciasFragment();
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

        View view = inflater.inflate(R.layout.fragment_asistencias, container, false);
        ListaAsistencias = new ArrayList<>();


        recyclerAs = view.findViewById(R.id.idrecyccleAsistencia);
        recyclerAs.setHasFixedSize(true);
        recyclerAs.setLayoutManager(new LinearLayoutManager(this.getContext()));


        request = Volley.newRequestQueue(getContext());

        webService();

        return view;
    }
    public void webService() {

        progres = new ProgressDialog(getContext());
        progres.setMessage("Espera...");
        progres.show();
        String url = "https://chvd.000webhostapp.com/listalocal.php?id="+id;





        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        request.add(jsonObjectRequest);
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
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getContext(),"No hay registros",Toast.LENGTH_SHORT).show();
            progres.hide();
    }

    @Override
    public void onResponse(JSONObject response) {
        LisAsis asis = null;

        JSONArray json=response.optJSONArray("personal");
        try {
            for (int i=0;i < json.length();i++){
                asis = new LisAsis();
                JSONObject jsonObject = null;
                jsonObject = json.getJSONObject(i);
                //asis.setContador(jsonObject.optInt("contador"));
                asis.setNombre(jsonObject.optString("nombre"));
                asis.setContador(jsonObject.optString("contador"));
                asis.setMatricula(jsonObject.optString("asistencia"));


                ListaAsistencias.add(asis);

            }


            AsistenciaAdapter adapter= new AsistenciaAdapter(ListaAsistencias);
            recyclerAs.setAdapter(adapter);
            progres.hide();






        }
        catch (JSONException e) {
            e.printStackTrace();
            progres.hide();
            Toast.makeText(getContext(),"No se pudo consultar"+response,Toast.LENGTH_SHORT).show();
        }


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
