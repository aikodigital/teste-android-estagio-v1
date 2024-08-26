package mono.com.google.android.gms.common.api;


public class PendingResult_StatusListenerImplementor
	extends java.lang.Object
	implements
		mono.android.IGCUserPeer,
		com.google.android.gms.common.api.PendingResult.StatusListener
{
/** @hide */
	public static final String __md_methods;
	static {
		__md_methods = 
			"n_onComplete:(Lcom/google/android/gms/common/api/Status;)V:GetOnComplete_Lcom_google_android_gms_common_api_Status_Handler:Android.Gms.Common.Apis.PendingResult/IStatusListenerInvoker, Xamarin.GooglePlayServices.Base\n" +
			"";
		mono.android.Runtime.register ("Android.Gms.Common.Apis.PendingResult+IStatusListenerImplementor, Xamarin.GooglePlayServices.Base", PendingResult_StatusListenerImplementor.class, __md_methods);
	}


	public PendingResult_StatusListenerImplementor ()
	{
		super ();
		if (getClass () == PendingResult_StatusListenerImplementor.class) {
			mono.android.TypeManager.Activate ("Android.Gms.Common.Apis.PendingResult+IStatusListenerImplementor, Xamarin.GooglePlayServices.Base", "", this, new java.lang.Object[] {  });
		}
	}


	public void onComplete (com.google.android.gms.common.api.Status p0)
	{
		n_onComplete (p0);
	}

	private native void n_onComplete (com.google.android.gms.common.api.Status p0);

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
