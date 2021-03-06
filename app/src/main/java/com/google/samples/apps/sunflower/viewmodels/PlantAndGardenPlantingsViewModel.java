package com.google.samples.apps.sunflower.viewmodels;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import androidx.lifecycle.ViewModel;

import com.google.common.base.Preconditions;
import com.google.samples.apps.sunflower.data.GardenPlanting;
import com.google.samples.apps.sunflower.data.Plant;
import com.google.samples.apps.sunflower.data.PlantAndGardenPlantings;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;


/**
 * RecyclerView 的 item 布局文件  需要用到的ViewModel
 */
public class PlantAndGardenPlantingsViewModel extends ViewModel {

    // ObservableField 感应数据变化   ---->  LiveData 感应数据变化
    // private LiveData

    public ObservableField<String> plantId; //植物ID，用于跳转使用
    public ObservableField<String> waterDateString; // 浇水时间 浇水日 信息数据成员
    public ObservableInt wateringInterval;
    public ObservableField<String> imageUrl; // 图片的URL
    public ObservableField<String> plantName; // 花的名称
    public ObservableField<String> plantDateString; // 应该是 种植于 什么 什么 时间 的信息

    public PlantAndGardenPlantingsViewModel(@NonNull PlantAndGardenPlantings plantings) {
        final Plant plant = Preconditions.checkNotNull(plantings.getPlant());
        final GardenPlanting gardenPlanting = plantings.getGardenPlantings().get(0);
        final DateFormat dateFormat = new SimpleDateFormat("MMM d, yyyy", Locale.US);

        this.plantId = new ObservableField<>(plantings.getPlant().getPlantId());
        this.waterDateString = new ObservableField<>(dateFormat.format(gardenPlanting.getLastWateringDate().getTime()));
        this.wateringInterval = new ObservableInt(plant.getWateringInterval());
        this.imageUrl = new ObservableField<>(plant.getImageUrl()); // 给它值
        this.plantName = new ObservableField<>(plant.getName());
        this.plantDateString = new ObservableField<>(dateFormat.format(gardenPlanting.getPlantDate().getTime()));
    }
}
