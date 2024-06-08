package com.overmighties.pubber.feature.account;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.overmighties.pubber.R;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import com.overmighties.pubber.R;

import lombok.Getter;

public class AccountDetailsAdapter extends RecyclerView.Adapter<AccountDetailsAdapter.ViewHolder>{
    private final AccountDetailsUIState accountDetailsUIState;
    @Getter
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView detailTitleView;
        private final TextView detailContentView;
        public ViewHolder(View view) {
            super(view);
            detailTitleView = (TextView) view.findViewById(R.id.left_title_view);
            detailContentView = (TextView) view.findViewById(R.id.right_content_view);
        }

    }
    public AccountDetailsAdapter(AccountDetailsUIState accountDetailsUIState) {
        this.accountDetailsUIState = accountDetailsUIState;
    }
    @NonNull
    @Override
    public AccountDetailsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return new AccountDetailsAdapter.ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.account_details_recycler_view_row,viewGroup,false));
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(position==0){
            holder.detailTitleView.setText(holder.itemView.getContext().getString(R.string.username_title));
            holder.detailContentView.setText(accountDetailsUIState.getUsername());
        }
        if(position==1){
            holder.detailTitleView.setText(holder.itemView.getContext().getString(R.string.email_title));
            holder.detailContentView.setText(accountDetailsUIState.getEmail());
        }
        if(position==2){
            holder.detailTitleView.setText(holder.itemView.getContext().getString(R.string.sex_title));
            holder.detailContentView.setText(accountDetailsUIState.getSex());
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }

}
