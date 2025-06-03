package com.example.dacs3.adapter.user;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners;
import com.example.dacs3.R;
import com.example.dacs3.activity.detaildulich;
import com.example.dacs3.model.Amthuc;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.text.DecimalFormat;

public class DulichUserAdapter extends FirebaseRecyclerAdapter<Amthuc, DulichUserAdapter.dlusHolder> {
    public DulichUserAdapter(@NonNull FirebaseRecyclerOptions<Amthuc> options) {
        super(options);

    }

    @SuppressLint("RecyclerView")
    @Override
    protected void onBindViewHolder(@NonNull DulichUserAdapter.dlusHolder dlusHolder, int position, @NonNull Amthuc amthuc) {



        dlusHolder.title.setText(amthuc.getTitle());
        dlusHolder.location.setText(amthuc.getLocation());
        float score = (float) amthuc.getScore();
        DecimalFormat df = new DecimalFormat("#.#");
        String formattedScore = df.format(score);
        dlusHolder.score.setText(formattedScore);
        Glide.with(dlusHolder.pic.getContext())
                .load(amthuc.getPic())
                .transform(new CenterCrop(),new GranularRoundedCorners(20,20,20,20))
                .placeholder(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark)
                .error(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark_normal)
                .into( dlusHolder.pic);



        dlusHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intCard = new Intent( dlusHolder.title.getContext(), detaildulich.class );
                intCard.putExtra("pic", amthuc.getPic());
                intCard.putExtra("title", amthuc.getTitle());
                intCard.putExtra("location", amthuc.getLocation());
                intCard.putExtra("description", amthuc.getDescription());
                intCard.putExtra("score",amthuc.getScore());


                dlusHolder.title.getContext().startActivity(intCard);
            }
        });

    }


    @NonNull
    @Override
    public DulichUserAdapter.dlusHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rvuser,parent,false);

        return new DulichUserAdapter.dlusHolder(view);
    }

    class dlusHolder extends RecyclerView.ViewHolder{
        ImageView pic;
        TextView title,location,description,score;

        CardView cardView;

        public dlusHolder(@NonNull View itemView) {
            super(itemView);

            pic= (ImageView) itemView.findViewById(R.id.img_rvus);
            title=(TextView) itemView.findViewById(R.id.title_rvus);
            location=(TextView) itemView.findViewById(R.id.location_rvus);
            score=(TextView) itemView.findViewById(R.id.score_rvus);


            cardView = (CardView) itemView.findViewById(R.id.recardus);
        }
    }


}

