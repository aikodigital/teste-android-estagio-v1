package mono.com.google.android.gms.common.api.internal;


public class BackgroundDetector_BackgroundStateChangeListenerImplementor
	extends java.lang.Object
	implements
		mono.android.IGCUserPeer,
		com.google.android.gms.common.api.internal.BackgroundDetector.BackgroundStateChangeListener
{
/** @hide */
	public static final String __md_methods;
	static {
		__md_methods = 
			"n_onBackgroundStateChanged:(Z)V:GetOnBackgroundStateChanged_ZHandler:Android.Gms.Common.Apis.Internal.BackgroundDetector/IBackgroundStateChangeListenerInvoker, Xamarin.GooglePlayServices.Basement\n" +
			"";
		mono.android.Runtime.register ("Android.Gms.Common.Apis.Internal.BackgroundDetector+IBackgroundStateChangeListenerImplementor, Xamarin.GooglePlayServices.Basement", BackgroundDetector_BackgroundStateChangeListenerImplementor.class, __md_methods);
	}


	public BackgroundDetector_BackgroundStateChangeListenerImplementor ()
	{
		super ();
		if (getClass () == BackgroundDetector_BackgroundStateChangeListenerImplementor.class) {
			mono.android.TypeManager.Activate ("Android.Gms.Common.Apis.Internal.BackgroundDetector+IBackgroundStateChangeListenerImplementor, Xamarin.GooglePlayServices.Basement", "", this, new java.lang.Object[] {  });
		}
	}


	public void onBackgroundStateChanged (boolean p0)
	{
		n_onBackgroundStateChanged (p0);
	}

	private native void n_onBackgroundStateChanged (boolean p0);

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
