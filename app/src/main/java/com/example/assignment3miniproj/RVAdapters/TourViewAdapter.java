package com.example.assignment3miniproj.RVAdapters;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.assignment3miniproj.Models.Quiz;
import com.example.assignment3miniproj.Models.Tournament;
import com.example.assignment3miniproj.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

public class TourViewAdapter extends FirebaseRecyclerAdapter<Tournament, TourViewAdapter.myviewholder> {



    public TourViewAdapter(@NonNull FirebaseRecyclerOptions<Tournament> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final myviewholder holder, final int position, @NonNull Tournament Tournament) {

        holder.name.setText(Tournament.getName());
        holder.Category.setText(Tournament.getCategory());
        holder.Difficulty.setText(Tournament.getDifficulty());
        holder.startDate.setText(Tournament.getStartDate());
        holder.endDate.setText(Tournament.getEndDate());


        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogPlus dialogplus = DialogPlus.newDialog(holder.edit.getContext())
                        .setContentHolder(new ViewHolder(R.layout.activity_admin_edit_items))
                        .setExpanded(true,1100)
                        .create();
                View myview = dialogplus.getHolderView();
                EditText name = myview.findViewById(R.id.etTournNameDialog);
                EditText startDate = myview.findViewById(R.id.etTournStartDateDialog);
                EditText endDate = myview.findViewById(R.id.etTournEndDateDialog);



                name.setText(Tournament.getName());

                startDate.setText(Tournament.getStartDate());
                endDate.setText(Tournament.getEndDate());
                Button update = myview.findViewById(R.id.btnTournUpdateDialog);


                dialogplus.show();



                update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Map<String, Object> map=new HashMap<>();
                        map.put("name", name.getText().toString());
                        map.put("startDate", startDate.getText().toString());
                        map.put("endDate", endDate.getText().toString());

                        FirebaseDatabase.getInstance().getReference().child("Tournament")
                                .child(getRef(position).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        dialogplus.dismiss();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        dialogplus.dismiss();
                                    }
                                });
                        ;
                    }
                });


                holder.delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(holder.name.getContext());
                        builder.setTitle("Delete Panel");
                        builder.setMessage("Delete...?");
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {

                                FirebaseDatabase.getInstance().getReference().child("Tournament")
                                        .child(getRef(position).getKey()).removeValue();
                            }
                        });

                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                        builder.show();
                    }
                });
            }
        });


    }




    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_tourn_item, parent, false);
        return new myviewholder(view);
    }

    class myviewholder extends RecyclerView.ViewHolder{
        TextView Category, Difficulty, endDate, startDate, name, Quiz;
        ImageView edit, delete;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            Category=(TextView)itemView.findViewById(R.id.tvTournCategoryRcy);
            Difficulty=(TextView)itemView.findViewById(R.id.tvTournDifficultyRcy);
            endDate=(TextView)itemView.findViewById(R.id.tvTournEndDateRcy);
            startDate=(TextView)itemView.findViewById(R.id.tvTournStartDateRcy);
            name=(TextView)itemView.findViewById(R.id.tvTournNameRcy);

            edit =(ImageView)itemView.findViewById(R.id.editicon);
            delete = (ImageView)itemView.findViewById(R.id.deleteicon);

        }
    }


}


