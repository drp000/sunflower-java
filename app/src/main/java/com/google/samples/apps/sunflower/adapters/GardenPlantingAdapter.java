package com.google.samples.apps.sunflower.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.google.samples.apps.sunflower.R;
import com.google.samples.apps.sunflower.data.PlantAndGardenPlantings;
import com.google.samples.apps.sunflower.databinding.ListItemGardenPlantingBinding;
import com.google.samples.apps.sunflower.fragments.GardenFragmentDirections;
import com.google.samples.apps.sunflower.viewmodels.PlantAndGardenPlantingsViewModel;

public class GardenPlantingAdapter extends ListAdapter<PlantAndGardenPlantings, GardenPlantingAdapter.ViewHolder> {

    public GardenPlantingAdapter() {
        super(new GardenPlantDiffCallback());
    }

    @NonNull
    @Override
    public GardenPlantingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        /*return new ViewHolder(DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.list_item_garden_planting, parent, false));*/

        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_garden_planting, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull GardenPlantingAdapter.ViewHolder holder, int position) {
        PlantAndGardenPlantings plantings = getItem(position);
        holder.itemView.setTag(plantings);

//        holder.bind(createOnClickListener(plantings.getPlant().getPlantId()), plantings);
        holder.bind(new Listener(), plantings);
    }

    private Listener createOnClickListener(String plantId) {
        /*return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("item", "onClick: 你点击了我的花园列表的Item，plantId:" + plantId);
            }
        };*/

        // 跳转到详情 并且把植物 id 给详情
        /*return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 导航过去了 到 详情
                Navigation.findNavController(v).navigate(
                        GardenFragmentDirections.actionGardenFragmentToPlantDetailFragment(plantId)
                );
            }
        };*/
        return new Listener();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private ListItemGardenPlantingBinding binding; // item 每一项

        public ViewHolder(@NonNull ListItemGardenPlantingBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public ViewHolder(@NonNull View view) {
            super(view);
            this.binding = DataBindingUtil.bind(view);
        }

        void bind(Listener listener, PlantAndGardenPlantings plantings) {
            this.binding.setViewModel(new PlantAndGardenPlantingsViewModel(plantings));
            this.binding.setListener(listener);  // 直接和 布局里面的 click 绑定了 关联起来
            this.binding.executePendingBindings();
        }
    }

    /**
     * Item的点击事件，使用dataBinding进行双向绑定
     */
    public static class Listener {
        public void onViewClicked(View view, String plantId) {
            Navigation.findNavController(view).navigate(
                    GardenFragmentDirections.actionGardenFragmentToPlantDetailFragment(plantId)
            );
        }
    }

    // 同学们：这就是一个比较数据而已，新Item 与 旧Item 的比较...
    static class GardenPlantDiffCallback extends DiffUtil.ItemCallback<PlantAndGardenPlantings> {

        @Override
        public boolean areItemsTheSame(@NonNull PlantAndGardenPlantings oldItem,
                                       @NonNull PlantAndGardenPlantings newItem) {
            return oldItem.getPlant().getPlantId().equals(newItem.getPlant().getPlantId());
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull PlantAndGardenPlantings oldItem,
                                          @NonNull PlantAndGardenPlantings newItem) {
            return oldItem.equals(newItem);
        }
    }
}
