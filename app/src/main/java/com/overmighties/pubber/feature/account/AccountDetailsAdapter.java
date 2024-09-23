package com.overmighties.pubber.feature.account;


import static com.overmighties.pubber.app.exception.ErrorSnackbarUI.showSnackbar;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import com.overmighties.pubber.R;
import com.overmighties.pubber.app.designsystem.UIText;

import java.util.Arrays;
import java.util.function.Consumer;

import lombok.Getter;

public class AccountDetailsAdapter extends RecyclerView.Adapter<AccountDetailsAdapter.ViewHolder>{
    private final AccountDetailsUIState accountDetailsUIState;
    private final AccountViewModel accountViewModel;
    @Getter
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView contentInfoImageView;
        private final TextView contentTextView;
        private final TextView contentEditTextView;
        public ViewHolder(View view) {
            super(view);
            contentInfoImageView = view.findViewById(R.id.accountDetailsRVR_image_contentInfo);
            contentTextView = view.findViewById(R.id.accountDetailsRVR_text_content);
            contentEditTextView = view.findViewById(R.id.accountDetailsRVR_text_edit);
        }

    }
    public AccountDetailsAdapter(AccountDetailsUIState accountDetailsUIState, AccountViewModel accountViewModel) {
        this.accountDetailsUIState = accountDetailsUIState;
        this.accountViewModel = accountViewModel;
    }
    @NonNull
    @Override
    public AccountDetailsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return new AccountDetailsAdapter.ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.account_details_recycler_view_row,viewGroup,false));
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String content = "UnKnown";
        Consumer<String> listener_consumer = null;
        Integer content_imageview_id = null;
        String title = null;
        if(position==0){
            content_imageview_id = R.drawable.ic_person_24px;
            if(accountDetailsUIState.getUsername() != null)
                content = accountDetailsUIState.getUsername();
            listener_consumer = (String new_data) ->{
                accountViewModel.updateDisplayName(
                        new_data,
                        (errorType, uiText,logMes) -> showSnackbar(holder.itemView,errorType,(UIText.ResourceString)uiText,logMes),
                        ()->{});
            };
            title = holder.itemView.getContext().getString(R.string.new_username);
        }
        if(position==1){
            content_imageview_id = R.drawable.ic_message_on_surface_variant;
            if(accountDetailsUIState.getEmail() != null)
                content = hideEmail(accountDetailsUIState.getEmail());
            listener_consumer = (String new_data) -> {
                accountViewModel.updateEmail(
                        new_data,
                        (errorType, uiText, logMes) -> showSnackbar(holder.itemView, errorType, (UIText.ResourceString) uiText, logMes));
            };
            title = holder.itemView.getContext().getString(R.string.new_email);
        }
        if(position==2){
            content_imageview_id = R.drawable.ic_gender;
            content = accountDetailsUIState.getSex();
            listener_consumer = (String new_data) -> {
                accountViewModel.getUserData().getValue().setSex(new_data);
            };
            title = holder.itemView.getContext().getString(R.string.sex_title);
        }

        if(content_imageview_id != null)
            holder.contentInfoImageView.setImageResource(content_imageview_id);
        holder.contentTextView.setText(content);

        final String[] new_data = {null};
        Consumer<String> finalListener_consumer = listener_consumer;
        String finalTitle = title;
        holder.contentEditTextView.setOnClickListener(v->{
            AlertDialog.Builder builder = new AlertDialog.Builder(holder.itemView.getContext(), R.style.CustomDialog);
            builder.setTitle(finalTitle);
            View dialogView = null;
            if(position == 2){
                String[] sex_choices = {holder.itemView.getContext().getString(R.string.female), holder.itemView.getContext().getString(R.string.male), holder.itemView.getContext().getString(R.string.other)};
                int checked_pos = Arrays.asList(sex_choices).indexOf(accountDetailsUIState.getSex());
                builder.setSingleChoiceItems(sex_choices, checked_pos, (dialog, which) -> {
                    new_data[0] = sex_choices[which];
                });
            }
            else{
                LayoutInflater inflater = LayoutInflater.from(holder.itemView.getContext());
                dialogView = inflater.inflate(R.layout.account_details_recycler_view_row_dialog_input, null);
            }

            View finalDialogView = dialogView;
            builder.setPositiveButton(holder.itemView.getContext().getString(R.string.accept), ((dialog, which) -> {
                if(position!=2)
                    new_data[0] = ((EditText) finalDialogView.findViewById(R.id.accountDetailsRVRDI_ET_changeContent)).getText().toString();
                if(finalListener_consumer != null)
                    finalListener_consumer.accept(new_data[0]);
            }));
            builder.setNeutralButton(holder.itemView.getContext().getString(R.string.cancel), null);


            if(dialogView!=null) {
                builder.setView(dialogView);
            }
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        });
    }

    private String hideEmail(String email){
        String show = email.substring(0, 3);
        StringBuilder hidden_part = new StringBuilder();
        for(int i = 0; i<email.length()-3;i++){
            hidden_part.append("*");
        }
        return show + hidden_part.toString();
    }

    @Override
    public int getItemCount() {
        return 3;
    }

}
