package com.senzecit.iitiimshaadi.chat;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.senzecit.iitiimshaadi.R;
import com.senzecit.iitiimshaadi.utils.AppController;
import com.senzecit.iitiimshaadi.utils.CONSTANTS;
import com.senzecit.iitiimshaadi.utils.CircleImageView;
import com.senzecit.iitiimshaadi.utils.cache.DownloadImageTask;
import com.senzecit.iitiimshaadi.utils.cache.ImagesCache;
import com.senzecit.iitiimshaadi.utils.preferences.AppPrefs;

import java.util.List;

/**
 * Created by senzec on 22/2/18.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    private List<Message> mMessages;
    private int[] mUsernameColors;
    Context context;
    AppPrefs prefs;

    public MessageAdapter(Context context, List<Message> messages) {
        this.context = context;
        mMessages = messages;
        mUsernameColors = context.getResources().getIntArray(R.array.username_colors);
        prefs = AppController.getInstance().getPrefs();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layout = -1;
        switch (viewType) {
            case Message.TYPE_MESSAGE:
                layout = R.layout.item_message_single;
                break;
            case Message.TYPE_LOG:
                layout = R.layout.item_log;
                break;
            case Message.TYPE_ACTION:
                layout = R.layout.item_action;
                break;
        }
        View v = LayoutInflater
                .from(parent.getContext())
                .inflate(layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.setIsRecyclable(false);
        Message message = mMessages.get(position);
        viewHolder.setProfileUrl(message.getProfileUrl());
        viewHolder.setMessage(message.getUserId(), message.getMessage());
        viewHolder.setUsername(message.getUsername());

    }

    @Override
    public int getItemCount() {
        return mMessages.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mMessages.get(position).getType();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView mProfileIV;
        private TextView mUsernameView;
        private TextView mMessageView;

        public ViewHolder(View itemView) {
            super(itemView);

            mProfileIV = itemView.findViewById(R.id.profile_image);
//            mUsernameView = (TextView) itemView.findViewById(R.id.tv_title);
            mMessageView = itemView.findViewById(R.id.message);
        }

        public void setProfileUrl(String profileURL) {
            if (null == mProfileIV) return;

            loadProfileImage(profileURL);
        }
        public void setUsername(String username) {
            if (null == mUsernameView) return;

            //  mUsernameView.setText(username);
//            mUsernameView.setText(prefs.getString(App.Key.CHATABLE_USER_NAME));
            mUsernameView.setTextColor(getUsernameColor(username));
        }

        public void setMessage(String userid, String message) {
            if (null == mMessageView) return;
            String senderId = prefs.getString(CONSTANTS.LOGGED_USERID);
            String receiverId = prefs.getString(CONSTANTS.OTHER_USERID);

            if(senderId.equalsIgnoreCase(userid)){
                mProfileIV.setVisibility(View.INVISIBLE);
                mMessageView.setText(message);
                mMessageView.setBackgroundResource(R.drawable.chat_bubble_left);
                mMessageView.setPadding(64,32,64,32);
                FrameLayout.LayoutParams layoutParams =
                        new FrameLayout.LayoutParams(mMessageView.getLayoutParams());
                layoutParams.gravity = Gravity.RIGHT;
                mMessageView.setLayoutParams(layoutParams);
            }else if (receiverId.equalsIgnoreCase(userid)) {
                mProfileIV.setVisibility(View.VISIBLE);
                mMessageView.setText(message);
                mMessageView.setBackgroundResource(R.drawable.chat_bubble_right);
                mMessageView.setGravity(Gravity.LEFT);
                mMessageView.setPadding(64,32,64,32);
            }

        }

        private void loadProfileImage(String profileURL){

            if(profileURL != null) {

                //IMAGE CACHE START
                ImagesCache cache = ImagesCache.getInstance();//Singleton instance handled in ImagesCache class.
                cache.initializeCache();
                Bitmap bm = cache.getImageFromWarehouse(profileURL);
                if (bm != null) {
                    Glide.with(context)
                            .load(bm)
                            .error(R.drawable.profile_img1)
                            .into(mProfileIV);
                } else {
                    Glide.with(context)
                            .load(profileURL)
                            .error(R.drawable.profile_img1)
                            .into(mProfileIV);

                    DownloadImageTask imgTask = new DownloadImageTask(cache, mProfileIV, 300, 300);//Since you are using it from `Activity` call second Constructor.
                    imgTask.execute(profileURL);
                }

            }
        }

        private int getUsernameColor(String username) {
            int hash = 7;
            for (int i = 0, len = username.length(); i < len; i++) {
                hash = username.codePointAt(i) + (hash << 5) - hash;
            }
            int index = Math.abs(hash % mUsernameColors.length);
            return mUsernameColors[index];
        }
    }
}
