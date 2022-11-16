package com.example.assignment3miniproj.RVAdapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment3miniproj.Models.Tournament;
import com.example.assignment3miniproj.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TourAddAdapterPL extends FirebaseRecyclerAdapter<Tournament, TourAddAdapterPL.myholder> {

    ArrayList<String> tournKeyList = new ArrayList<>();

    public TourAddAdapterPL(@NonNull FirebaseRecyclerOptions<Tournament> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final myholder holder, final int position, @NonNull Tournament Tournament) {

        holder.name.setText(Tournament.getName());
        holder.Category.setText(Tournament.getCategory());
        holder.Difficulty.setText(Tournament.getDifficulty());
        holder.startDate.setText(Tournament.getStartDate());
        holder.endDate.setText(Tournament.getEndDate());

        DatabaseReference dr = FirebaseDatabase.getInstance().getReference("Tournament");
    }

    @NonNull
    @Override
    public myholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_home_item, parent, false);
        return new TourAddAdapterPL.myholder(view);
    }

    class myholder extends RecyclerView.ViewHolder{
    TextView Category, Difficulty, endDate, startDate, name, Quiz;
    ImageView edit, delete;

    public myholder(@NonNull View itemView) {
        super(itemView);

        Category=(TextView)itemView.findViewById(R.id.tvTournCategoryRcyPL);
        Difficulty=(TextView)itemView.findViewById(R.id.tvTournDifficultyRcyPL);
        endDate=(TextView)itemView.findViewById(R.id.tvTournEndDateRcyPL);
        startDate=(TextView)itemView.findViewById(R.id.tvTournStartDateRcyPL);
        name=(TextView)itemView.findViewById(R.id.tvTournNameRcyPL);

        edit =(ImageView)itemView.findViewById(R.id.editicon);
        delete = (ImageView)itemView.findViewById(R.id.deleteicon);

    }
}

}

//public class TourAddAdapterPL extends FirebaseRecyclerAdapter<Tournament, TourAddAdapterPL.myholder> {
//
//    ArrayList<String> tournKeyList = new ArrayList<>();
//
//    public TourAddAdapterPL(@NonNull FirebaseRecyclerOptions<Tournament> options) {
//        super(options);
//    }
//
//    @Override
//    protected void onBindViewHolder(@NonNull final myholder holder, final int position, @NonNull Tournament Tournament) {
//
//        holder.name.setText(Tournament.getName());
//        holder.Category.setText(Tournament.getCategory());
//        holder.Difficulty.setText(Tournament.getDifficulty());
//        holder.startDate.setText(Tournament.getStartDate());
//        holder.endDate.setText(Tournament.getEndDate());
//
//        DatabaseReference dr = FirebaseDatabase.getInstance().getReference("Tournament");
//    }
//
//    @NonNull
//    @Override
//    public myholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_home_item, parent, false);
//        return new TourAddAdapterPL.myholder(view);
//    }
//
//    class myholder extends RecyclerView.ViewHolder {
//        TextView Category, Difficulty, endDate, startDate, name, Quiz;
//        ImageView edit, delete;
//
//        public myholder(@NonNull View itemView) {
//            super(itemView);
//
//            Category = (TextView) itemView.findViewById(R.id.tvTournCategoryRcyPL);
//            Difficulty = (TextView) itemView.findViewById(R.id.tvTournDifficultyRcyPL);
//            endDate = (TextView) itemView.findViewById(R.id.tvTournEndDateRcyPL);
//            startDate = (TextView) itemView.findViewById(R.id.tvTournStartDateRcyPL);
//            name = (TextView) itemView.findViewById(R.id.tvTournNameRcyPL);
//
//            edit = (ImageView) itemView.findViewById(R.id.editicon);
//            delete = (ImageView) itemView.findViewById(R.id.deleteicon);
//
//        }
//    }
//}
