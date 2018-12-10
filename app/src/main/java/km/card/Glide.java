package km.card;

import android.content.Context;
import android.support.annotation.NonNull;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.module.AppGlideModule;

/**
 * @author: ${bruce}
 * @project: CreditCard
 * @package: km.card
 * @description:
 * @date: 2018/12/5 0005  
 * @time: 下午 2:07
 */
@GlideModule
public class Glide extends AppGlideModule
{
	@Override
	public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
		builder.setDiskCache(new DiskLruCacheFactory("/sdcard/imagepicker/", 1024*1024*100));
		super.applyOptions(context, builder);
	}
}
