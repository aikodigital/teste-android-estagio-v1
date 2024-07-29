package mono.com.google.android.gms.tasks;


public class OnTokenCanceledListenerImplementor
	extends java.lang.Object
	implements
		mono.android.IGCUserPeer,
		com.google.android.gms.tasks.OnTokenCanceledListener
{
/** @hide */
	public static final String __md_methods;
	static {
		__md_methods = 
			"n_onCanceled:()V:GetOnCanceledHandler:Android.Gms.Tasks.IOnTokenCanceledListenerInvoker, Xamarin.GooglePlayServices.Tasks\n" +
			"";
		mono.android.Runtime.register ("Android.Gms.Tasks.IOnTokenCanceledListenerImplementor, Xamarin.GooglePlayServices.Tasks", OnTokenCanceledListenerImplementor.class, __md_methods);
	}


	public OnTokenCanceledListenerImplementor ()
	{
		super ();
		if (getClass () == OnTokenCanceledListenerImplementor.class) {
			mono.android.TypeManager.Activate ("Android.Gms.Tasks.IOnTokenCanceledListenerImplementor, Xamarin.GooglePlayServices.Tasks", "", this, new java.lang.Object[] {  });
		}
	}


	public void onCanceled ()
	{
		n_onCanceled ();
	}

	private native void n_onCanceled ();

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
