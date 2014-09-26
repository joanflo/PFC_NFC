package com.joanflo.network;

import java.io.InputStream;
import java.net.URL;
import com.joanflo.tagit.R;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

/**
 * AsyncTask to load images
 * @author Joanflo
 */
public class ImageLoader extends AsyncTask<URL, Void, Bitmap> {
	// <input_data, progress_data, output_data>

	
	private ImageView imageView;
	
	public ImageLoader(ImageView imageView) {
		this.imageView = imageView;
	}
	
	
	
	@Override
	protected Bitmap doInBackground(URL... urls) {
		URL url = urls[0];
		Bitmap bmp = null;
		try {
			InputStream in = url.openStream();
			bmp = BitmapFactory.decodeStream(in);
			
		} catch (Exception e) {
			// IOException or MalformedURLException
			e.printStackTrace();
		}
		return bmp;
	}
	
	
	
	@Override
	protected void onPostExecute(Bitmap bmp) {
		if (bmp == null) {
			imageView.setImageResource(R.drawable.default_image);
		} else {
			imageView.setImageBitmap(bmp);
		}
    }
	

}