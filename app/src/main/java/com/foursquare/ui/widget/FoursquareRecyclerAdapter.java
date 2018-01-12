package com.foursquare.ui.widget;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import com.foursquare.ui.base.BaseListItemView;
import java.util.List;

public class FoursquareRecyclerAdapter<T, V extends View & BaseListItemView<T>>
    extends RecyclerView.Adapter<FoursquareRecyclerAdapter.CustomViewHolder> {
  private final Function0<V> viewBuilder;
  private List<T> items;

  public FoursquareRecyclerAdapter(List<T> items, Function0<V> viewBuilder) {
    this.items = items;
    this.viewBuilder = viewBuilder;
  }

  @Override public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new CustomViewHolder(viewBuilder.call());
  }

  @Override
  public void onBindViewHolder(FoursquareRecyclerAdapter.CustomViewHolder holder, int position) {
    ((BaseListItemView) holder.getItemView()).bind(items.get(position));
  }

  @Override public int getItemCount() {
    if (items == null) return 0;
    return items.size();
  }

  public T getItem(final int position) {
    return items.get(position);
  }

  public void setItems(List<T> items) {
    this.items = items;
    notifyDataSetChanged();
  }

  public void addItems(List<T> items) {
    this.items.addAll(items);
    notifyDataSetChanged();
  }

  public void insertItems(int position, List<T> items) {
    this.items.addAll(position, items);
    notifyDataSetChanged();
  }

  public List<T> getItems() {
    return items;
  }

  public class CustomViewHolder extends RecyclerView.ViewHolder {
    private V itemView;

    public CustomViewHolder(V itemView) {
      super(itemView);
      this.itemView = itemView;
    }

    public V getItemView() {
      return itemView;
    }
  }

  public interface Function0<V> {
    V call();
  }
}
