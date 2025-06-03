package com.example.dacs3.adapter.user;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners;
import com.example.dacs3.R;
import com.example.dacs3.model.Dichuyen;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class DichuyenUserAdapter extends FirebaseRecyclerAdapter<Dichuyen, DichuyenUserAdapter.dcusHolder> {
    public DichuyenUserAdapter(@NonNull FirebaseRecyclerOptions<Dichuyen> opdc) {
        super(opdc);
    }

    @SuppressLint("RecyclerView")
    @Override
    protected void onBindViewHolder(@NonNull DichuyenUserAdapter.dcusHolder dcusHolder, int position, @NonNull Dichuyen dichuyen) {



        dcusHolder.tenxe.setText(dichuyen.getTenxe());
        dcusHolder.diachi.setText(dichuyen.getDiachi());
        dcusHolder.sdt.setText(dichuyen.getSdt());
        Glide.with(dcusHolder.pic.getContext())
                .load(dichuyen.getPic())
                .placeholder(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark)
                .error(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark_normal)
                .transform(new CenterCrop(),new GranularRoundedCorners(20,20,20,20))
                .into(dcusHolder.pic);






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
    public DichuyenUserAdapter.dcusHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dcuser,parent,false);

        return new DichuyenUserAdapter.dcusHolder(view);
    }

    class dcusHolder extends RecyclerView.ViewHolder{
        ImageView pic;
        TextView tenxe,diachi,sdt;

        // CardView cardView;

        public dcusHolder(@NonNull View itemView) {
            super(itemView);

            pic= (ImageView) itemView.findViewById(R.id.img_rvdcUS);
            tenxe=(TextView) itemView.findViewById(R.id.title_rvdcUS);
            diachi=(TextView) itemView.findViewById(R.id.location_rvdcUS);
            sdt=(TextView) itemView.findViewById(R.id.sdt_rvdcUS);


            //   cardView = (CardView) itemView.findViewById(R.id.recard);
        }
    }

}
