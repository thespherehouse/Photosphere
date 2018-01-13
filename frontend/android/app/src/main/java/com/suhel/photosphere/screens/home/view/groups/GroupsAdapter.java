package com.suhel.photosphere.screens.home.view.groups;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.suhel.photosphere.databinding.ItemGroupBinding;
import com.suhel.photosphere.model.response.Group;

import java.util.List;
import java.util.Random;

public class GroupsAdapter extends RecyclerView.Adapter<GroupsAdapter.GroupViewHolder> {

    private Random rnd = new Random();
    private List<Group> data;

    @Override
    public GroupViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new GroupViewHolder(ItemGroupBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(GroupViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    public List<Group> getData() {
        return data;
    }

    public void setData(List<Group> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public Group getItem(int position) {
        return data.get(position);
    }

    @Override
    public int getItemCount() {
        return data != null ? data.size() : 0;
    }

    class GroupViewHolder extends RecyclerView.ViewHolder {

        ItemGroupBinding binding;

        public GroupViewHolder(ItemGroupBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Group group) {
            int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
            binding.tvName.setText(group.getName());
            binding.imgAvatar.setColorFilter(color);
            binding.tvAvatar.setText(group.getName().substring(0, 1).toUpperCase());
        }

    }

}
