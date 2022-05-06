package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.namghjk.exchangemoney.R;

import java.util.List;

import Model.History;

public class HistoryAdapter extends ArrayAdapter {

    private int resourceLayout;
    private Context mContext;

    public HistoryAdapter(@NonNull Context context, int resource, @NonNull List objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.resourceLayout = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(resourceLayout, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.txtValueFrom = convertView.findViewById(R.id.txtValueFrom);
            viewHolder.txtCodeFrom = convertView.findViewById(R.id.txtCodeFrom);
            viewHolder.txtValueTo = convertView.findViewById(R.id.txtValueTo);
            viewHolder.txtCodeTo = convertView.findViewById(R.id.txtCodeTo);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        History item = (History) getItem(position);
        viewHolder.txtValueFrom.setText(item.getValueInput());
        viewHolder.txtCodeFrom.setText(item.getCodeFrom());
        viewHolder.txtValueTo.setText(item.getValueOutput());
        viewHolder.txtCodeTo.setText(item.getCodeTo());

        return convertView;
    }

    private static class ViewHolder {
        TextView txtValueFrom;
        TextView txtCodeFrom;
        TextView txtValueTo;
        TextView txtCodeTo;
    }
}

