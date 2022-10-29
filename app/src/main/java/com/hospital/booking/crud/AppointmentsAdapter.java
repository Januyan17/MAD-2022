package com.hospital.booking.crud;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;

import com.hospital.booking.R;

public class AppointmentsAdapter extends ArrayAdapter<AppointmentModel> {
    public AppointmentsAdapter(Context context) {
        super(context, R.layout.card_appoinement);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.card_appoinement, parent, false);
            holder = new ViewHolder(convertView);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        AppointmentModel appointmentModel = getItem(position);

        holder.tvDoctorName.setText(appointmentModel.getDoctorName());
        holder.tvDate.setText(appointmentModel.getBookingDate());
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent editIntent = new Intent(getContext(), EditAppointment.class);
                editIntent.putExtra("id", appointmentModel.getId());
                getContext().startActivity(editIntent);
            }
        });

        return convertView;
    }

    static class ViewHolder {
        TextView tvDoctorName;
        TextView tvDate;
        Button edit;

        ViewHolder(View view) {
            tvDoctorName = (TextView) view.findViewById(R.id.appointment_card_name);
            tvDate = (TextView) view.findViewById(R.id.appointment_card_date);
            edit = (Button) view.findViewById(R.id.edit111);
        }
    }
}