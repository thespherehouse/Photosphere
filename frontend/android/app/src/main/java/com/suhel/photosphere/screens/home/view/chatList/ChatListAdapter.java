package com.suhel.photosphere.screens.home.view.chatList;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.suhel.photosphere.databinding.ItemChatInBinding;
import com.suhel.photosphere.databinding.ItemChatOutBinding;
import com.suhel.photosphere.model.response.Message;
import com.suhel.photosphere.utils.DateUtils;

import java.util.List;

public class ChatListAdapter extends RecyclerView.Adapter {

    private static final int VIEW_TYPE_IN = 1;
    private static final int VIEW_TYPE_OUT = 2;

    private List<Message> data;
    private String userId;

    public ChatListAdapter(String userId) {
        this.userId = userId;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return viewType == VIEW_TYPE_IN ?
                new MessageInViewHolder(ItemChatInBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false)) :
                new MessageOutViewHolder(ItemChatOutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int itemViewType = holder.getItemViewType();
        if (itemViewType == VIEW_TYPE_IN)
            ((MessageInViewHolder) holder).bind(getItem(position));
        else if (itemViewType == VIEW_TYPE_OUT)
            ((MessageOutViewHolder) holder).bind(getItem(position));
    }

    @Override
    public int getItemCount() {
        return (this.data != null) ? this.data.size() : 0;
    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).getUserId().equals(userId) ? VIEW_TYPE_OUT : VIEW_TYPE_IN;
    }

    public void setMessages(List<Message> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void addMessage(Message message) {
        if (this.data == null)
            return;
        this.data.add(0, message);
        notifyItemInserted(this.data.size() - 1);
    }

    public Message getItem(int position) {
        if (this.data == null || this.data.isEmpty())
            return null;

        if (position < 0 || position >= this.data.size())
            return null;

        return this.data != null ? this.data.get(this.data.size() - position - 1) : null;
    }

    class MessageInViewHolder extends RecyclerView.ViewHolder {

        ItemChatInBinding binding;

        public MessageInViewHolder(ItemChatInBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Message message) {
            binding.tvName.setText(message.getUserName());
            binding.tvMessage.setText(message.getMessage());
            binding.tvDateTime.setText(DateUtils.formatSimple(message.getCreatedAt()));
        }

    }

    class MessageOutViewHolder extends RecyclerView.ViewHolder {

        ItemChatOutBinding binding;

        public MessageOutViewHolder(ItemChatOutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Message message) {
            binding.tvMessage.setText(message.getMessage());
            binding.tvDateTime.setText(DateUtils.formatSimple(message.getCreatedAt()));
        }

    }

}
