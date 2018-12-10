package km.card;

import android.widget.ImageView;

/**
 * @author: ${bruce}
 * @project: CreditCard
 * @package: km.card
 * @description:
 * @date: 2018/12/5 0005  
 * @time: 下午 2:11
 */
public interface ImageLoader
{
	void displayFileImage(ImageView imageView, String path);
	
	void displayUserImage(ImageView imageView, String path);
	
	void displayFileVideo(String path);
	
	
	Class<?> displayFullImageClass();
}
