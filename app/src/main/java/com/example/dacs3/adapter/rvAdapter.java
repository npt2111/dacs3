package com.example.dacs3.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners;
import com.example.dacs3.R;
import com.example.dacs3.activity.detailamthuc;
import com.example.dacs3.model.Amthuc;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;


public class rvAdapter extends FirebaseRecyclerAdapter<Amthuc, rvAdapter.rvHolder> {
    public rvAdapter(@NonNull FirebaseRecyclerOptions<Amthuc> options) {
        super(options);

    }

    @SuppressLint("RecyclerView")
    @Override
    protected void onBindViewHolder(@NonNull rvAdapter.rvHolder rvHolder, int position, @NonNull Amthuc amthuc) {



        rvHolder.title.setText(amthuc.getTitle());
        rvHolder.location.setText(amthuc.getLocation());

        float score = (float) amthuc.getScore();
        DecimalFormat df = new DecimalFormat("#.#");
        String formattedScore = df.format(score);
        rvHolder.score.setText(formattedScore);

        Glide.with(rvHolder.pic.getContext())
                .load(amthuc.getPic())
                .transform(new CenterCrop(),new GranularRoundedCorners(20,20,20,20))
                .placeholder(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark)
                .error(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark_normal)
                .into(rvHolder.pic);

        rvHolder.btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogPlus dialogPlus = DialogPlus.newDialog(rvHolder.title.getContext())

                        .setContentHolder(new ViewHolder(R.layout.update_layout))
                        .setExpanded(true, 750)
                        .create();

                View holderViewUD = dialogPlus.getHolderView();

                EditText title = holderViewUD.findViewById(R.id.UDtitle);
                EditText location = holderViewUD.findViewById(R.id.UDlocation);
                EditText description = holderViewUD.findViewById(R.id.UDdescription);
                EditText pic = holderViewUD.findViewById(R.id.UDpic);
                TextView score = holderViewUD.findViewById(R.id.UDscore);

                title.setText(amthuc.getTitle());
                location.setText(amthuc.getLocation());
                description.setText(amthuc.getDescription());
                pic.setText(amthuc.getPic());
                score.setText(String.valueOf((float) amthuc.getScore()));

                Button btn_update_dialog = holderViewUD.findViewById(R.id.btn_update_dialog);
                dialogPlus.show();
                btn_update_dialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Map<String, Object> map = new HashMap<>();

                        map.put("title",title.getText().toString());
                        map.put("location",location.getText().toString());
                        map.put("description",description.getText().toString());
                        map.put("pic",pic.getText().toString());
                        map.put("score",Float.parseFloat(score.getText().toString()));

                        FirebaseDatabase.getInstance().getReference().child("Amthuc")
                                .child(getRef(rvHolder.getAdapterPosition()).getKey())
                                .updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        dialogPlus.dismiss();
                                    }
                                });
                    }
                });


            }
        });


        rvHolder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(rvHolder.title.getContext());
                builder.setTitle("Xóa?");
                builder.setMessage("Xóa");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseDatabase.getInstance().getReference().child("Amthuc")
                                .child(getRef(position).getKey()).removeValue();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(rvHolder.title.getContext(), "Cancelled", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });

        rvHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intCard = new Intent(rvHolder.title.getContext(), detailamthuc.class );
                intCard.putExtra("pic", amthuc.getPic());
                intCard.putExtra("title", amthuc.getTitle());
                intCard.putExtra("location", amthuc.getLocation());
                intCard.putExtra("description", amthuc.getDescription());
                intCard.putExtra("score",String.valueOf(amthuc.getScore()));


                rvHolder.title.getContext().startActivity(intCard);
            }
        });

    }


    @NonNull
    @Override
    public rvAdapter.rvHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item,parent,false);

        return new rvAdapter.rvHolder(view);
    }

    class rvHolder extends RecyclerView.ViewHolder{
        ImageView pic;
        TextView title,location,description,score;
        Button btn_update, btn_delete;
        CardView cardView;

        public rvHolder(@NonNull View itemView) {
            super(itemView);

            pic= (ImageView) itemView.findViewById(R.id.img_rv);
            title=(TextView) itemView.findViewById(R.id.title_rv);
            location=(TextView) itemView.findViewById(R.id.location_rv);
            score=(TextView) itemView.findViewById(R.id.score_rv);
            btn_update=(Button) itemView.findViewById(R.id.btn_update);
            btn_delete=(Button) itemView.findViewById(R.id.btn_delete);

            cardView = (CardView) itemView.findViewById(R.id.recard);
        }
    }


}

