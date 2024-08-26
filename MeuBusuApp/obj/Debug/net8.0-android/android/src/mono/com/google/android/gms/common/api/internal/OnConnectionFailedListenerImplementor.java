package mono.com.google.android.gms.common.api.internal;


public class OnConnectionFailedListenerImplementor
	extends java.lang.Object
	implements
		mono.android.IGCUserPeer,
		com.google.android.gms.common.api.internal.OnConnectionFailedListener
{
/** @hide */
	public static final String __md_methods;
	static {
		__md_methods = 
			"n_onConnectionFailed:(Lcom/google/android/gms/common/ConnectionResult;)V:GetOnConnectionFailed_Lcom_google_android_gms_common_ConnectionResult_Handler:Android.Gms.Common.Api.Internal.IOnConnectionFailedListenerInvoker, Xamarin.GooglePlayServices.Base\n" +
			"";
		mono.android.Runtime.register ("Android.Gms.Common.Api.Internal.IOnConnectionFailedListenerImplementor, Xamarin.GooglePlayServices.Base", OnConnectionFailedListenerImplementor.class, __md_methods);
	}


	public OnConnectionFailedListenerImplementor ()
	{
		super ();
		if (getClass () == OnConnectionFailedListenerImplementor.class) {
			mono.android.TypeManager.Activate ("Android.Gms.Common.Api.Internal.IOnConnectionFailedListenerImplementor, Xamarin.GooglePlayServices.Base", "", this, new java.lang.Object[] {  });
		}
	}


	public void onConnectionFailed (com.google.android.gms.common.ConnectionResult p0)
	{
		n_onConnectionFailed (p0);
	}

	private native void n_onConnectionFailed (com.google.android.gms.common.ConnectionResult p0);

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
