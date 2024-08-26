package mono.com.google.android.gms.dynamic;


public class OnDelegateCreatedListenerImplementor
	extends java.lang.Object
	implements
		mono.android.IGCUserPeer,
		com.google.android.gms.dynamic.OnDelegateCreatedListener
{
/** @hide */
	public static final String __md_methods;
	static {
		__md_methods = 
			"n_onDelegateCreated:(Lcom/google/android/gms/dynamic/LifecycleDelegate;)V:GetOnDelegateCreated_Lcom_google_android_gms_dynamic_LifecycleDelegate_Handler:Android.Gms.Dynamic.IOnDelegateCreatedListenerInvoker, Xamarin.GooglePlayServices.Basement\n" +
			"";
		mono.android.Runtime.register ("Android.Gms.Dynamic.IOnDelegateCreatedListenerImplementor, Xamarin.GooglePlayServices.Basement", OnDelegateCreatedListenerImplementor.class, __md_methods);
	}


	public OnDelegateCreatedListenerImplementor ()
	{
		super ();
		if (getClass () == OnDelegateCreatedListenerImplementor.class) {
			mono.android.TypeManager.Activate ("Android.Gms.Dynamic.IOnDelegateCreatedListenerImplementor, Xamarin.GooglePlayServices.Basement", "", this, new java.lang.Object[] {  });
		}
	}


	public void onDelegateCreated (com.google.android.gms.dynamic.LifecycleDelegate p0)
	{
		n_onDelegateCreated (p0);
	}

	private native void n_onDelegateCreated (com.google.android.gms.dynamic.LifecycleDelegate p0);

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
