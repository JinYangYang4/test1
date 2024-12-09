package com.example.bendiyinyue1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class NetMusicAdapter extends RecyclerView.Adapter<NetMusicAdapter.NetMusicViewHolder> {
    private List<NetMusic> netMusicList;
    private OnItemClickListener onItemClickListener;

    public NetMusicAdapter(List<NetMusic> netMusicList) {
        this.netMusicList = netMusicList;
    }

    @NonNull
    @Override
    public NetMusicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.net_music_item, parent, false);
        return new NetMusicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NetMusicViewHolder holder, int position) {
        NetMusic netMusic = netMusicList.get(position);
        holder.titleTextView.setText(netMusic.getAlbumId());
        holder.artistTextView.setText(netMusic.getFilename());
        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.OnItemClick(holder.itemView, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return netMusicList.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public static class NetMusicViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView artistTextView;

        public NetMusicViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.item_local_music_song);
            artistTextView = itemView.findViewById(R.id.item_local_music_singer);
        }
    }

    public interface OnItemClickListener {
        void OnItemClick(View view, int position);
    }
}