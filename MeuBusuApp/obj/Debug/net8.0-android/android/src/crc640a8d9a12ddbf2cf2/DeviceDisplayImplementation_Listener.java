package crc640a8d9a12ddbf2cf2;


public class DeviceDisplayImplementation_Listener
	extends android.view.OrientationEventListener
	implements
		mono.android.IGCUserPeer
{
/** @hide */
	public static final String __md_methods;
	static {
		__md_methods = 
			"n_onOrientationChanged:(I)V:GetOnOrientationChanged_IHandler\n" +
			"";
		mono.android.Runtime.register ("Microsoft.Maui.Devices.DeviceDisplayImplementation+Listener, Microsoft.Maui.Essentials", DeviceDisplayImplementation_Listener.class, __md_methods);
	}


	public DeviceDisplayImplementation_Listener (android.content.Context p0, int p1)
	{
		super (p0, p1);
		if (getClass () == DeviceDisplayImplementation_Listener.class) {
			mono.android.TypeManager.Activate ("Microsoft.Maui.Devices.DeviceDisplayImplementation+Listener, Microsoft.Maui.Essentials", "Android.Content.Context, Mono.Android:Android.Hardware.SensorDelay, Mono.Android", this, new java.lang.Object[] { p0, p1 });
		}
	}


	public DeviceDisplayImplementation_Listener (android.content.Context p0)
	{
		super (p0);
		if (getClass () == DeviceDisplayImplementation_Listener.class) {
			mono.android.TypeManager.Activate ("Microsoft.Maui.Devices.DeviceDisplayImplementation+Listener, Microsoft.Maui.Essentials", "Android.Content.Context, Mono.Android", this, new java.lang.Object[] { p0 });
		}
	}


	public void onOrientationChanged (int p0)
	{
		n_onOrientationChanged (p0);
	}

	private native void n_onOrientationChanged (int p0);

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
