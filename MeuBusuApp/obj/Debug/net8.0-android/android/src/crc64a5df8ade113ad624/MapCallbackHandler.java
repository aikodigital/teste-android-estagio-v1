package crc64a5df8ade113ad624;


public class MapCallbackHandler
	extends java.lang.Object
	implements
		mono.android.IGCUserPeer,
		com.google.android.gms.maps.GoogleMap.OnCameraMoveListener,
		com.google.android.gms.maps.OnMapReadyCallback
{
/** @hide */
	public static final String __md_methods;
	static {
		__md_methods = 
			"n_onCameraMove:()V:GetOnCameraMoveHandler:Android.Gms.Maps.GoogleMap/IOnCameraMoveListenerInvoker, Xamarin.GooglePlayServices.Maps\n" +
			"n_onMapReady:(Lcom/google/android/gms/maps/GoogleMap;)V:GetOnMapReady_Lcom_google_android_gms_maps_GoogleMap_Handler:Android.Gms.Maps.IOnMapReadyCallbackInvoker, Xamarin.GooglePlayServices.Maps\n" +
			"";
		mono.android.Runtime.register ("Microsoft.Maui.Maps.Handlers.MapCallbackHandler, Microsoft.Maui.Maps", MapCallbackHandler.class, __md_methods);
	}


	public MapCallbackHandler ()
	{
		super ();
		if (getClass () == MapCallbackHandler.class) {
			mono.android.TypeManager.Activate ("Microsoft.Maui.Maps.Handlers.MapCallbackHandler, Microsoft.Maui.Maps", "", this, new java.lang.Object[] {  });
		}
	}


	public void onCameraMove ()
	{
		n_onCameraMove ();
	}

	private native void n_onCameraMove ();


	public void onMapReady (com.google.android.gms.maps.GoogleMap p0)
	{
		n_onMapReady (p0);
	}

	private native void n_onMapReady (com.google.android.gms.maps.GoogleMap p0);

	private java.util.ArrayList refList;
	public void monodroidAddReference (java.lang.Object obj)
	{
		if (refList == null)
			refList = new java.util.ArrayList ();
		refList.add (obj);
	}

	public void monodroidClearReferences ()
	{
		if (refList != null)
			refList.clear ();
	}
}
