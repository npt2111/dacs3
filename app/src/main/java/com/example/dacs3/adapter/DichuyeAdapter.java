package com.example.dacs3.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners;
import com.example.dacs3.R;
import com.example.dacs3.model.Dichuyen;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

public class DichuyeAdapter extends FirebaseRecyclerAdapter<Dichuyen, DichuyeAdapter.dcHolder> {
    public DichuyeAdapter(@NonNull FirebaseRecyclerOptions<Dichuyen> opdc) {
        super(opdc);
    }

    @SuppressLint("RecyclerView")
    @Override
    protected void onBindViewHolder(@NonNull DichuyeAdapter.dcHolder dcHolder, int position, @NonNull Dichuyen dichuyen) {



        dcHolder.tenxe.setText(dichuyen.getTenxe());
        dcHolder.diachi.setText(dichuyen.getDiachi());
        dcHolder.sdt.setText(dichuyen.getSdt());
        Glide.with(dcHolder.pic.getContext())
                .load(dichuyen.getPic())
                .placeholder(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark)
                .error(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark_normal)
                .transform(new CenterCrop(),new GranularRoundedCorners(20,20,20,20))
                .into(dcHolder.pic);


        dcHolder.btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogPlus dialogPlus = DialogPlus.newDialog(dcHolder.tenxe.getContext())

                        .setContentHolder(new ViewHolder(R.layout.update_dc))
                        .setExpanded(true, 750)
                        .create();

                View holderViewUD = dialogPlus.getHolderView();

                EditText tenxe = holderViewUD.findViewById(R.id.DCtitle);
                EditText diachi = holderViewUD.findViewById(R.id.DClocation);
                EditText sdt = holderViewUD.findViewById(R.id.DCphone);
                EditText pic = holderViewUD.findViewById(R.id.DCpic);


                tenxe.setText(dichuyen.getTenxe());
                diachi.setText(dichuyen.getDiachi());
                sdt.setText(dichuyen.getSdt());
                pic.setText(dichuyen.getPic());


                Button btn_update_dialog = holderViewUD.findViewById(R.id.btn_updatedc_dialog);
                dialogPlus.show();
                btn_update_dialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Map<String, Object> map = new HashMap<>();

                        map.put("tenxe",tenxe.getText().toString());
                        map.put("diachi",diachi.getText().toString());
                        map.put("sdt",sdt.getText().toString());
                        map.put("pic",pic.getText().toString());


                        FirebaseDatabase.getInstance().getReference().child("Dichuyen")
                                .child(getRef(dcHolder.getAdapterPosition()).getKey())
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

        dcHolder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(dcHolder.tenxe.getContext());
                builder.setTitle("Xóa?");
                builder.setMessage("Xóa");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseDatabase.getInstance().getReference().child("Dichuyen")
                                .child(getRef(position).getKey()).removeValue();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(dcHolder.tenxe.getContext(), "Cancelled", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });

//        dcHolder.cardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//                Intent intCard = new Intent(dcHolder.tenxe.getContext(), detailamthuc.class );
//                intCard.putExtra("loai",dichuyen.getLoai());
//                intCard.putExtra("pic", dichuyen.getPic());
//                intCard.putExtra("tenxe", dichuyen.getTenxe());
//                intCard.putExtra("diachi", dichuyen.getDiachi());
//                intCard.putExtra("sdt",dichuyen.getSdt());
//
//
//                dcHolder.tenxe.getContext().startActivity(intCard);
//            }
//        });

    }


    @NonNull
    @Override
    public DichuyeAdapter.dcHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dc,parent,false);

        return new DichuyeAdapter.dcHolder(view);
    }

    class dcHolder extends RecyclerView.ViewHolder{
        ImageView pic;
        TextView tenxe,diachi,sdt;
        Button btn_update, btn_delete;
       // CardView cardView;

        public dcHolder(@NonNull View itemView) {
            super(itemView);

            pic= (ImageView) itemView.findViewById(R.id.img_rvdc);
            tenxe=(TextView) itemView.findViewById(R.id.title_rvdc);
            diachi=(TextView) itemView.findViewById(R.id.location_rvdc);
            sdt=(TextView) itemView.findViewById(R.id.sdt_rvdc);
            btn_update=(Button) itemView.findViewById(R.id.btn_updatedc);
            btn_delete=(Button) itemView.findViewById(R.id.btn_deletedc);

         //   cardView = (CardView) itemView.findViewById(R.id.recard);
        }
    }

}
