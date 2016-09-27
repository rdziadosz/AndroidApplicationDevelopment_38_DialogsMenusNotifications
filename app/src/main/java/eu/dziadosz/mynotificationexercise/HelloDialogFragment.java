package eu.dziadosz.mynotificationexercise;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

/**
 * Created by Radosław on 27.09.2016.
 */

public class HelloDialogFragment extends DialogFragment {
    /* The activity that creates an instance of this dialog fragment must
  * implement this interface in order to receive event callbacks.
  * Each method passes the DialogFragment in case the host needs to query it. */
    public interface HelloDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog, String teamName);
        public void onDialogNegativeClick(DialogFragment dialog);
    }

    // Use this instance of the interface to deliver action events
    HelloDialogListener mListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (HelloDialogListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement HelloDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // new AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        final View dialogView = inflater.inflate(R.layout.name_dialog, null);
        builder.setView(dialogView)

                .setTitle("Hello!")
                .setPositiveButton("Send", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // find a team name
                        EditText editText = (EditText) dialogView.findViewById(R.id.editText);
                        String name = editText.getText().toString();
                        mListener.onDialogPositiveClick(HelloDialogFragment.this,name);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mListener.onDialogNegativeClick(HelloDialogFragment.this);
                    }
                });
        return builder.create();
    }
}
