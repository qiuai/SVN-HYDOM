package com.carinsurance.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.model.BitmapDescriptor;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.PolylineOptions;
import com.carinsurancer.car.R;
//高德地图工具
public class AMapUtils {
	
  
	/**
	 * 镜头移动到
	 * @param aMap
	 * @param latlng
	 */
    public static void CameraMoveTo(AMap aMap,LatLng latlng)
    {
    	aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 12));
    }
  
   
	  /**
     * 在地图上画一个普通的marker，系统默认的为蓝色（定位图标）
     * 
     * LatLng的第一个参数是纬度，第二个参数是经度
     * 	LatLng latlng = new LatLng(Double.parseDouble(m_x[j]), Double.parseDouble(m_y[j]));
     * @param aMap
     * @param title 用marker.getTitle()获取
     * @param Snippet 文字片段   用getSnippet()获取，  返回当前marker 的文字片段
     * @param latlng
     */
	public static void drawMarker(AMap aMap,String title,String Snippet,LatLng latlng)
	{
		aMap.addMarker(new MarkerOptions().anchor(0.5f, 0.5f)
				.position(latlng).title(title)
				.snippet(Snippet).draggable(true));
	}
	
/**
 * 画其他颜色的Marker
 * @param aMap 
 * @param latLng   需要画的经纬度
 * @param title 标签getTitle获取
 * @param bitmapDescriptorFactory  
 * BitmapDescriptorFactory中提供了10中不同颜色的Marker 如：BitmapDescriptorFactory.HUE_AZURE
 * 	HUE_AZURE天蓝色	HUE_BLUE蓝色           HUE_CYAN 青色              HUE_GREEN绿色           HUE_MAGENTA酒红色         HUE_ORANGE橙色     HUE_RED红色
    HUE_ROSE玫瑰红    HUE_VIOLET紫色           HUE_YELLOW黄色
 */
	public static void drawOtherColorMarker(AMap aMap,LatLng latLng,String title,float bitmapDescriptorFactory)
	{
		BitmapDescriptor factory=BitmapDescriptorFactory.defaultMarker(bitmapDescriptorFactory);//蓝色
		
		aMap.addMarker(new MarkerOptions().anchor(0.5f, 0.5f)
				.position(latLng).title(title).icon(factory)
				.draggable(true));

	}
//	/**
//	 * 画其他颜色的Marker
//	 * @param aMap 
//	 * @param latLng   需要画的经纬度
//	 * @param title 标签getTitle获取
//	 * @param 0:market_green 1:market_blue ....5.
//	 * 	    @param pos//第几个
//	 */
//		public static void drawOtherColorMarker(Context ct,AMap aMap,LatLng latLng,String title,int setColor,int pos)
//		{
////			BitmapDescriptor factory=BitmapDescriptorFactory.defaultMarker(bitmapDescriptorFactory);//蓝色
//			LayoutInflater inflater=LayoutInflater.from(ct);
////			Bitmap bmp=BitmapFactory.decodeResource(ct.getResources(), R.drawable.icon);
//			View view=inflater.inflate(R.layout.market, null);
//			ImageView iv=(ImageView) view.findViewById(R.id.market);
//			if(setColor==0)
//			{
//				iv.setImageResource(R.drawable.market_green);
//			}else if(setColor==1)
//			{
//				iv.setImageResource(R.drawable.market_blue);
//			}else if(setColor==2)
//			{
//				iv.setImageResource(R.drawable.market_huang);
//			}else if(setColor==3)
//			{
//				iv.setImageResource(R.drawable.market_red);
//			}else if(setColor==4)
//			{
//				iv.setImageResource(R.drawable.market_tianlan);
//			}else if(setColor==5)
//			{
//				iv.setImageResource(R.drawable.market_zi);
//			}
//			TextView number=(TextView)view.findViewById(R.id.number);
//			number.setText(""+pos);
//			BitmapDescriptor factory=BitmapDescriptorFactory.fromView(view);//蓝色
//			
//			aMap.addMarker(new MarkerOptions().anchor(0.5f, 0.5f)
//					.position(latLng).title(title).icon(factory)
//					.draggable(true));
//
//		}
		
		/**
		 * 画其他颜色的Marker
		 * @param aMap 
		 * @param latLng   需要画的经纬度
		 * @param title 标签getTitle获取
		 */
			public static void drawZidingyiMarker(Context ct,AMap aMap,LatLng latLng,String title)
			{
//				BitmapDescriptor factory=BitmapDescriptorFactory.defaultMarker(bitmapDescriptorFactory);//蓝色
				LayoutInflater inflater=LayoutInflater.from(ct);
//				Bitmap bmp=BitmapFactory.decodeResource(ct.getResources(), R.drawable.icon);
				View view=inflater.inflate(R.layout.market, null);
//				ImageView iv=(ImageView) view.findViewById(R.id.market);
//				if(setColor==0)
//				{
//					iv.setImageResource(R.drawable.market_green);
//				}else if(setColor==1)
//				{
//					iv.setImageResource(R.drawable.market_blue);
//				}else if(setColor==2)
//				{
//					iv.setImageResource(R.drawable.market_huang);
//				}else if(setColor==3)
//				{
//					iv.setImageResource(R.drawable.market_red);
//				}else if(setColor==4)
//				{
//					iv.setImageResource(R.drawable.market_tianlan);
//				}else if(setColor==5)
//				{
//					iv.setImageResource(R.drawable.market_zi);
//				}
//				TextView number=(TextView)view.findViewById(R.id.number);
//				number.setText(""+pos);
				BitmapDescriptor factory=BitmapDescriptorFactory.fromView(view);//蓝色
				
				aMap.addMarker(new MarkerOptions().anchor(0.5f, 0.5f)
						.position(latLng).icon(factory)//title(title)
						.draggable(true));

			}
	  /**
     * 画一条指定颜色的线
     * @param aMap
     * @param FromLaLng 从某个地方的经纬度（LatLng）
     * @param ToLaLng   到某个地方的经纬度（LatLng）
     * @param linecolor 类似Color.RED  
     */
	public static void drawLine(AMap aMap,LatLng FromLaLng,LatLng ToLaLng,int linecolor)
	{
		 aMap.addPolyline((new PolylineOptions()).add(
				 FromLaLng, ToLaLng).color(
					linecolor));
	}
	
	
	/**
	 * 参数:
latitude - 地点的纬度，在-90 与90 之间的double 型数值。
longitude - 地点的经度，在-180 与180 之间的double 型数值。
	 * 纬度和经度是否合法
	 * @param latitude
	 * @param longitude
	 * @return
	 */
	public static boolean LatitudeAndLongItudeIsLegal(Double latitude,Double longitude)
	{
		if(-90<=latitude && latitude<=90)
		{
			if(-180<=longitude && longitude<=180)
			{
				return true;
			}
		}
		return false;
		
	}
    /**
     * 纬度是否合法
     * @param latitude
     * @return
     */
	public static boolean LatitudeIsLegal(Double latitude)
	{
		if(-90<=latitude && latitude<=90)
		{
			return true;
		}
		
		return false;
		
	}
	 /**
     * 经度是否合法
     * @param latitude
     * @return
     */
	public static boolean LongitudeIsLegal(Double longitude)
	{
		if(-180<=longitude && longitude<=180)
		{
			return true;
		}
		
		return false;
		
	}
}
