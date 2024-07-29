package mono.com.google.android.gms.tasks;


public class OnCanceledListenerImplementor
	extends java.lang.Object
	implements
		mono.android.IGCUserPeer,
		com.google.android.gms.tasks.OnCanceledListener
{
/** @hide */
	public static final String __md_methods;
	static {
		__md_methods = 
			"n_onCanceled:()V:GetOnCanceledHandler:Android.Gms.Tasks.IOnCanceledListenerInvoker, Xamarin.GooglePlayServices.Tasks\n" +
			"";
		mono.android.Runtime.register ("Android.Gms.Tasks.IOnCanceledListenerImplementor, Xamarin.GooglePlayServices.Tasks", OnCanceledListenerImplementor.class, __md_methods);
	}


	public OnCanceledListenerImplementor ()
	{
		super ();
		if (getClass () == OnCanceledListenerImplementor.class) {
			mono.android.TypeManager.Activate ("Android.Gms.Tasks.IOnCanceledListenerImplementor, Xamarin.GooglePlayServices.Tasks", "", this, new java.lang.Object[] {  });
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
