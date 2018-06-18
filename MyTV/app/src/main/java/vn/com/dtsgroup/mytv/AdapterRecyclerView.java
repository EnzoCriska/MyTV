package vn.com.dtsgroup.mytv;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;


public class AdapterRecyclerView extends RecyclerView.Adapter<AdapterRecyclerView.ViewHolder> {
    private List list;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageButton imageButton;
        TextView textView;
        private ItemClickInterface itemClickListener;

        public ViewHolder(View itemView) {
            super(itemView);

            imageButton = itemView.findViewById(R.id.imgbtn);
            textView = itemView.findViewById(R.id.chanelName);
            itemView.setOnClickListener((View.OnClickListener) this);
        }

        public void setItemClickListener (ItemClickInterface itemClickListener){
            this.itemClickListener = itemClickListener;
        }


        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v, getAdapterPosition());
        }
    }

    public AdapterRecyclerView(List list){
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View chanelView = inflater.inflate(R.layout.chanelitem, parent, false);

        ViewHolder viewHolder = new ViewHolder(chanelView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Chanel chanel = (Chanel) list.get(position);
        TextView textView = (TextView) holder.itemView.findViewById(R.id.chanelName);
        textView.setText(chanel.getName());
//        ImageButton imageButton = holder.itemView.findViewById(R.id.imgbtn);
 //        String path = chanel.getUrlLogo();
//        try {
//            InputStream img = new FileInputStream(path);
//            Drawable icon = new BitmapDrawable(img);
//            imageButton.setImageDrawable(icon);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
        holder.setItemClickListener(new ItemClickInterface() {
            @Override
            public void onClick(View view, int postision) {
                MainActivity.streamVideo(chanel.getUrl());
            }
        });

    }


    @Override
    public int getItemCount() {
        return list.size();
    }
    }
