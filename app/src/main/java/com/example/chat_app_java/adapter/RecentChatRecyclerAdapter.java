package com.example.chat_app_java.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chat_app_java.ChatActivity;
import com.example.chat_app_java.R;
import com.example.chat_app_java.model.ChatRoomModel;
import com.example.chat_app_java.model.UserModel;
import com.example.chat_app_java.utils.AndroidUtils;
import com.example.chat_app_java.utils.FirebaseUtil;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class RecentChatRecyclerAdapter extends FirestoreRecyclerAdapter<ChatRoomModel, RecentChatRecyclerAdapter.ChatRoomModelViewHolder> {
    private final Context context;

    public RecentChatRecyclerAdapter(@NonNull FirestoreRecyclerOptions<ChatRoomModel> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull ChatRoomModelViewHolder holder, int position, @NonNull ChatRoomModel model) {
        FirebaseUtil.getOtherUserFromChatroom(model.getUserIds()).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                boolean lastMessageSendByMe = model.getLastMessageSenderId().equals(FirebaseUtil.currentUserId());

                UserModel otherUserModel = task.getResult().toObject(UserModel.class);

                FirebaseUtil.getOtherProfilePicStorageRef(otherUserModel.getUserId()).getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> t) {
                        if (t.isSuccessful()){
                            Uri uri = t.getResult();
                            AndroidUtils.setProfilePic(context, uri, holder.profilePic);
                        }
                    }
                });

                holder.userNameText.setText(otherUserModel.getUsername());
                if (lastMessageSendByMe){
                    holder.lastMessageText.setText("You: "+ model.getLastMessage());
                }else {
                    holder.lastMessageText.setText(model.getLastMessage());
                }

                holder.lastMessageTime.setText(FirebaseUtil.timestampToString(model.getLastMessageTimestamp()));

                holder.itemView.setOnClickListener(v -> {
                    Intent intent = new Intent(context, ChatActivity.class);
                    AndroidUtils.passUserModelAsIntent(intent, otherUserModel);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                });
            }
        });
    }

    @NonNull
    @Override
    public ChatRoomModelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recent_chat_recycler_row, parent, false);
        return new ChatRoomModelViewHolder(view);
    }

    class ChatRoomModelViewHolder extends RecyclerView.ViewHolder {
        TextView userNameText, lastMessageText, lastMessageTime;
        ImageView profilePic;

        public ChatRoomModelViewHolder(@NonNull View itemView) {
            super(itemView);
            userNameText = itemView.findViewById(R.id.user_name_text);
            lastMessageText = itemView.findViewById(R.id.last_message_text);
            lastMessageTime = itemView.findViewById(R.id.last_message_time_text);
            profilePic = itemView.findViewById(R.id.profile_pic_image_view);
        }
    }
}
