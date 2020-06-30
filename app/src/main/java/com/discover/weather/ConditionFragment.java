package com.discover.weather;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.discover.weather.dummy.DummyContent;
import com.discover.weather.dummy.DummyContent.DummyItem;

import java.util.List;
import java.util.concurrent.locks.Condition;

public class ConditionFragment extends DialogFragment
{
    ConditionFragmentListener listener;

    public ConditionFragment() { }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        String conditions[] =
        ArrayAdapter conditions = ArrayAdapter.createFromResource(getContext(), R.array.dummy_conditions, android.R.layout.simple_list_item_1);

        builder.setTitle(R.string.label_conditions).
//                setItems(R.array.dummy_conditions, new DialogInterface.OnClickListener()
//                {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which)
//                    {
//                        listener.onClickCondition(ConditionFragment.this, which);
//                    }
            setAdapter(conditions, new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    listener.onClickCondition(ConditionFragment.this, which);
                }

            });

        return builder.create();
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);

        try {
            listener = (ConditionFragmentListener)context;
        }
        catch (ClassCastException err) {
            throw new ClassCastException(getActivity().toString() +
                    " must implement ConditionFragmentListener");
        }
    }

    public interface ConditionFragmentListener
    {
        public void onClickCondition(DialogFragment dialog, int index);
    }
}
