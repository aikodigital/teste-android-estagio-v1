package mono.com.google.android.gms.common.api.internal;


public class SignInConnectionListenerImplementor
	extends java.lang.Object
	implements
		mono.android.IGCUserPeer,
		com.google.android.gms.common.api.internal.SignInConnectionListener
{
/** @hide */
	public static final String __md_methods;
	static {
		__md_methods = 
			"n_onComplete:()V:GetOnCompleteHandler:Android.Gms.Common.Api.Internal.ISignInConnectionListenerInvoker, Xamarin.GooglePlayServices.Base\n" +
			"";
		mono.android.Runtime.register ("Android.Gms.Common.Api.Internal.ISignInConnectionListenerImplementor, Xamarin.GooglePlayServices.Base", SignInConnectionListenerImplementor.class, __md_methods);
	}


	public SignInConnectionListenerImplementor ()
	{
		super ();
		if (getClass () == SignInConnectionListenerImplementor.class) {
			mono.android.TypeManager.Activate ("Android.Gms.Common.Api.Internal.ISignInConnectionListenerImplementor, Xamarin.GooglePlayServices.Base", "", this, new java.lang.Object[] {  });
		}
	}


	public void onComplete ()
	{
		n_onComplete ();
	}

	private native void n_onComplete ();

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
