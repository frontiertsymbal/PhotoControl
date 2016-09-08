package frontier.armyhelper.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;

import frontier.armyhelper.R;

public class ArrayListAdapter extends ArrayAdapter<String> {

    private LayoutInflater mInflater;

    private View.OnClickListener mDeleteListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            remove((String) view.getTag());
        }
    };

    public ArrayListAdapter(Context context, int resource) {
        super(context, resource);
        mInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.param_list_item, parent, false);
        }

        TextView itemText = (TextView) convertView.findViewById(R.id.itemText);
        FrameLayout deleteButton = (FrameLayout) convertView.findViewById(R.id.deleteButton);

        String item = getItem(position);
        itemText.setText(item);
        deleteButton.setTag(item);
        deleteButton.setOnClickListener(mDeleteListener);

        return convertView;
    }

    public ArrayList<String> getParamsList() {
        ArrayList<String> paramsList = new ArrayList<>();
        for (int i = 0; i < getCount(); i++) {
            paramsList.add(getItem(i));
        }
        return paramsList;
    }
}
