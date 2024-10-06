package com.example.BKPM_E41231300;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterOnePiece extends RecyclerView.Adapter<AdapterOnePiece.CharacterViewHolder> {

    private List<String> characterList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(String name);
    }

    public AdapterOnePiece(List<String> characterList, OnItemClickListener listener) {
        this.characterList = characterList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CharacterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card, parent, false);
        return new CharacterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CharacterViewHolder holder, int position) {
        String characterName = characterList.get(position);
        holder.tvCharacterName.setText(characterName);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(characterName);
            }
        });
    }

    @Override
    public int getItemCount() {
        return characterList.size();
    }

    public static class CharacterViewHolder extends RecyclerView.ViewHolder {
        TextView tvCharacterName;

        public CharacterViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCharacterName = itemView.findViewById(R.id.tvCharacterName);
        }
    }
}
