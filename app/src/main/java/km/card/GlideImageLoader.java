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
public class GlideImageLoader implements ImageLoader
{
	@Override
	public void displayFileImage(ImageView imageView, String path) {
		//GlideApp.with(imageView.getContext())                             //配置上下文
		//		.load(Uri.fromFile(new File(path)))      //设置图片路径(fix #8,文件名包含%符号 无法识别和显示)
		//		.into(imageView);
	}
	
	@Override
	public void displayUserImage(ImageView imageView, String path) {
		//GlideApp.with(imageView.getContext()).load(path).into(imageView);
	}
	
	
	@Override
	public void displayFileVideo(String path) {
		
	}
	
	@Override
	public Class<?> displayFullImageClass() {
		return null;
	}
}
