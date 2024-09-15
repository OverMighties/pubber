package com.overmighties.pubber.feature.pubdetails;

import android.content.Context;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.TextAppearanceSpan;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.overmighties.pubber.R;
import com.overmighties.pubber.feature.pubdetails.chipsfragments.DetailsDrinkListAdapter;
import com.overmighties.pubber.feature.pubdetails.chipsfragments.stateholders.DetailsDrinkCardViewUiState;
import com.overmighties.pubber.feature.pubdetails.chipsfragments.stateholders.DetailsSelectListener;
import com.overmighties.pubber.feature.pubdetails.stateholders.DetailsCommentCardViewUiState;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

public class DetailsCommentsAdapter extends RecyclerView.Adapter<DetailsCommentsAdapter.ViewHolder> {
    public static final String TAG = "DetailsCommentsAdapter";
    private final List<DetailsCommentCardViewUiState> commentsList;
    private boolean expanded = false;
    private  String shownPart;
    private  String hiddenPart;

    @Getter
    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView nickName;
        private final TextView origin;
        private final TextView rating;
        private final TextView priceRange;
        private final TextView content;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nickName = itemView.findViewById(R.id.comTVNickname);
            origin = itemView.findViewById(R.id.comTVOrigin);
            rating = itemView.findViewById(R.id.comTVRating);
            priceRange = itemView.findViewById(R.id.comTVPrice);
            content = itemView.findViewById(R.id.comTVContent);

        }
    }

    public DetailsCommentsAdapter(List<DetailsCommentCardViewUiState> commentsList){
        this.commentsList = commentsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.details_comment_cardview,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull DetailsCommentsAdapter.ViewHolder holder, int position) {
        DetailsCommentCardViewUiState uiState = commentsList.get(position);
        if(uiState.getNickName() != null)
            holder.nickName.setText(uiState.getNickName()+" • ");
        if(uiState.getOrigin() != null) {
            if (uiState.getOrigin().equals(holder.itemView.getContext().getString(R.string.google))) {
                setUpGoogleTextView(holder.origin, holder.itemView.getContext());
            } else if (uiState.getOrigin().equals(holder.itemView.getContext().getString(R.string.app_name))) {
                setUpPubberTextView(holder.origin, holder.itemView.getContext());
            } else if (uiState.getOrigin().equals(holder.itemView.getContext().getString(R.string.tripadvisor))) {
                setUpTripAdvisorTextView(holder.origin, holder.itemView.getContext());
            }
        }
        if(uiState.getRating() != 0.0F)
            holder.rating.setText(String.valueOf(uiState.getRating()));
        if(uiState.getPriceRange() != null)
            holder.priceRange.setText(uiState.getPriceRange());
        if(uiState.getContent() != null){
            if(uiState.getContent().length() <= 200){
                holder.content.setText(uiState.getContent());
            }
            else{
                setUpLongContentTextView(holder.content, uiState.getContent(), holder.itemView.getContext());
            }
        }
    }

    public void setUpTripAdvisorTextView(TextView textView, Context context) {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        SpannableString string = new SpannableString(context.getString(R.string.tripadvisor));
        string.setSpan(new TextAppearanceSpan(context, R.style.TripAdvisor_highlight), 0, string.length(),0);
        spannableStringBuilder.append(string);

        textView.setText(spannableStringBuilder);
    }

    public void setUpPubberTextView(TextView textView, Context context) {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        SpannableString string = new SpannableString(context.getString(R.string.app_name));
        string.setSpan(new TextAppearanceSpan(context, R.style.Pubber_highlight), 0, string.length(),0);
        spannableStringBuilder.append(string);

        textView.setText(spannableStringBuilder);
    }

    public void setUpGoogleTextView(TextView textView, Context context){
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        SpannableString string = new SpannableString("G");
        string.setSpan(new TextAppearanceSpan(context, R.style.Google_highlight_custom_blue),0,1,0);
        spannableStringBuilder.append(string);
        string = new SpannableString("o");
        string.setSpan(new TextAppearanceSpan(context, R.style.Google_highlight_custom_red),0,1,0);
        spannableStringBuilder.append(string);
        string = new SpannableString("o");
        string.setSpan(new TextAppearanceSpan(context, R.style.Google_highlight_custom_yellow),0,1,0);
        spannableStringBuilder.append(string);
        string = new SpannableString("g");
        string.setSpan(new TextAppearanceSpan(context, R.style.Google_highlight_custom_blue),0,1,0);
        spannableStringBuilder.append(string);
        string = new SpannableString("l");
        string.setSpan(new TextAppearanceSpan(context, R.style.Google_highlight_custom_yellow),0,1,0);
        spannableStringBuilder.append(string);
        string = new SpannableString("e");
        string.setSpan(new TextAppearanceSpan(context, R.style.Google_highlight_custom_red),0,1,0);
        spannableStringBuilder.append(string);

        textView.setText(spannableStringBuilder);
    }

    public void setUpLongContentTextView(TextView textView, String content,Context context){
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        shownPart = content.substring(0, 150);
        int n = 149;
        while(!shownPart.endsWith(" ")){
            shownPart = content.substring(0, n);
            n -= 1;
        }
        spannableStringBuilder.append(shownPart);
        SpannableString more = new SpannableString(context.getString(R.string.more));
        more.setSpan(new TextAppearanceSpan(context, R.style.Pubber_highlight), 0, context.getString(R.string.more).length(), 0);
        more.setSpan(new UnderlineSpan(),0, context.getString(R.string.more).length(), 0 );
        spannableStringBuilder.append(more);
        textView.setText(spannableStringBuilder);
        hiddenPart = content.substring(n, content.length()-1);
        textView.setOnClickListener(v->{
            if(expanded){
                SpannableStringBuilder builder = new SpannableStringBuilder(content);
                builder.append(shownPart);
                builder.append(more);
                textView.setText(builder);
                expanded = false;
            }
            else{
                SpannableStringBuilder builder = new SpannableStringBuilder(content);
                builder.append(shownPart);
                builder.append(hiddenPart);
                SpannableString less = new SpannableString(context.getString(R.string.less));
                less.setSpan(new TextAppearanceSpan(context, R.style.Pubber_highlight), 0, context.getString(R.string.less).length(), 0);
                less.setSpan(new UnderlineSpan(),0, context.getString(R.string.less).length(), 0 );
                builder.append(less);
                textView.setText(builder);
                expanded = true;
            }
        });

    }


    @Override
    public int getItemCount() {
        if(commentsList.isEmpty()){return 0;}
        else{
            return  commentsList.size();
        }
    }
}
