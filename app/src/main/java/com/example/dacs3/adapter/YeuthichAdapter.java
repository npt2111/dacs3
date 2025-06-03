package com.example.dacs3.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners;
import com.example.dacs3.R;
import com.example.dacs3.model.YeuThichModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;

public class YeuthichAdapter extends FirebaseRecyclerAdapter<YeuThichModel, YeuthichAdapter.ytHolder> {
public YeuthichAdapter(@NonNull FirebaseRecyclerOptions<YeuThichModel> options) {
        super(options);
        }

@SuppressLint("RecyclerView")
@Override
protected void onBindViewHolder(@NonNull YeuthichAdapter.ytHolder ytHolder, int position, @NonNull YeuThichModel amthuc) {



        ytHolder.title.setText(amthuc.getTitle());
        ytHolder.location.setText(amthuc.getLocation());
        float score = (float) amthuc.getScore();
        DecimalFormat df = new DecimalFormat("#.#");
        String formattedScore = df.format(score);
        ytHolder.score.setText(formattedScore);
        Glide.with(ytHolder.pic.getContext())
        .load(amthuc.getPic())
        .placeholder(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark)
        .error(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark_normal)
                .transform(new CenterCrop(),new GranularRoundedCorners(20,20,20,20))
        .into(ytHolder.pic);



    ytHolder.btn_delete.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(ytHolder.title.getContext());
            builder.setTitle("Xóa?");
            builder.setMessage("Xóa");

            builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    FirebaseDatabase.getInstance().getReference().child("yeuthich")
                            .child(getRef(position).getKey()).removeValue();
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(ytHolder.title.getContext(), "Cancelled", Toast.LENGTH_SHORT).show();
                }
            });
            builder.show();
        }
    });
}


    @NonNull
    @Override
    public YeuthichAdapter.ytHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_admin,parent,false);

        return new YeuthichAdapter.ytHolder(view);
    }

class ytHolder extends RecyclerView.ViewHolder{
    ImageView pic;
    TextView title,location,description,score;
    Button btn_update, btn_delete;
   // CardView cardView;

    public ytHolder(@NonNull View itemView) {
        super(itemView);

        pic= (ImageView) itemView.findViewById(R.id.img_rvyt);
        title=(TextView) itemView.findViewById(R.id.title_rvyt);
        location=(TextView) itemView.findViewById(R.id.location_rvyt);
        score=(TextView) itemView.findViewById(R.id.score_rvyt);

        btn_delete=(Button) itemView.findViewById(R.id.btn_deleteYT);

       // cardView = (CardView) itemView.findViewById(R.id.recard);
    }
}

}