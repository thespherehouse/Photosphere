package com.suhel.photosphere.custom.view;

//public abstract class SimpleAdapter<D, V extends ViewDataBinding> extends RecyclerView.Adapter<SimpleAdapter.SimpleViewHolder> {
//
//    private List<D> data;
//
//    public SimpleAdapter() {
//    }
//
//    public SimpleAdapter(List<D> data) {
//        this.data = data;
//    }
//
//    public final List<D> getData() {
//        return data;
//    }
//
//    public final void setData(List<D> data) {
//        this.data = data;
//        notifyDataSetChanged();
//    }
//
//    public final void addItem(D item) {
//        if (this.data == null)
//            this.data = new ArrayList<>();
//        this.data.add(item);
//        notifyItemInserted(this.data.size() - 1);
//    }
//
//    public final void removeItem(D item) {
//        if (this.data != null) {
//            this.data.remove(item);
//            notifyDataSetChanged();
//        }
//    }
//
//    @Override
//    public final SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        return new SimpleViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), getItemLayoutRes(), parent, false));
//    }
//
//    @Override
//    public void onBindViewHolder(SimpleViewHolder holder, int position) {
//        bind(holder.binding, data.get(position));
//    }
//
//    @Override
//    public final int getItemCount() {
//        return data != null ? data.size() : 0;
//    }
//
//    @LayoutRes
//    protected abstract int getItemLayoutRes();
//
//    protected abstract void bind(V binding, D data);
//
//    class SimpleViewHolder extends RecyclerView.ViewHolder {
//
//        V binding;
//
//        SimpleViewHolder(V binding) {
//            super(binding.getRoot());
//            this.binding = binding;
//        }
//
//    }
//
//}
