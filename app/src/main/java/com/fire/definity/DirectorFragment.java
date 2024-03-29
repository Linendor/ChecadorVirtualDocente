package com.fire.definity;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DirectorFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DirectorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DirectorFragment extends Fragment implements Response.Listener<JSONObject>,Response.ErrorListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    EditText nombrecom,nombreusu,pass;
    Button registrar;
    ProgressDialog progres;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    private OnFragmentInteractionListener mListener;

    public DirectorFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DirectorFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DirectorFragment newInstance(String param1, String param2) {
        DirectorFragment fragment = new DirectorFragment();
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

        View view =  inflater.inflate(R.layout.fragment_director, container, false);



        nombrecom = view.findViewById(R.id.NomCompletoDirec);
        nombreusu = view.findViewById(R.id.NombreUsuDirec);
        pass = view.findViewById(R.id.passDirec);
        registrar = view.findViewById(R.id.regDirector);

        request = Volley.newRequestQueue(getContext());

        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviarDirec();
            }
        });


        return view;
    }

    private void enviarDirec() {
        progres = new ProgressDialog(getContext());
        progres.setMessage("Enviando...");
        progres.show();


        String url ="https://chvd.000webhostapp.com/insdirector.php?nombreusu="+nombreusu.getText()+"&pass="+pass.getText()+"&nombrecom="+nombrecom.getText();
        url = url.replace(" ","%20");


        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        request.add(jsonObjectRequest);
    }


    @Override
    public void onErrorResponse(VolleyError error) {
         Toast.makeText(getContext(),"No se envio",Toast.LENGTH_SHORT).show();
            progres.hide();
    }

    @Override
    public void onResponse(JSONObject response) {
        progres.hide();

        nombrecom.setText("");
        nombreusu.setText("");
        pass.setText("");

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
